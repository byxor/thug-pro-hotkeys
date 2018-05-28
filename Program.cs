﻿using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Threading;
using System.Diagnostics;

namespace tprosetgoto {

    class Commands {
        public static readonly string SET_RESTART = Command("set");
        public static readonly string GOTO_RESTART = Command("goto");
        public static readonly string OBSERVE = Command("obs");
        public static readonly string WARP = Command("warp");
        private static string Command(string name) { return "/" + name; }
    }

    class KeyCodes {
        private static readonly Dictionary<string, int> map = new Dictionary<string, int> {
            {"F5", 116},
            {"F6", 117},
            {"F7", 118},
            {"F8", 119},
        };

        public static int get(string representation) {
            return map[representation];
        }
    }

    class KeyCodeTypes {
        public const int WM_KEYDOWN = 0x0100;
    }

    class Dlls {
        public const string USER_32 = "user32.dll";
        public const string KERNEL_32 = "kernel32.dll";
    }

    class Command {
        public static void Post(int windowHandle, string msg) {
            PressEnter(windowHandle);
            Thread.Sleep(5);
            foreach (char c in msg)
                PressChar(windowHandle, c);
            PressEnter(windowHandle);
        }

        private static void PressEnter(int windowHandle) {
            PostMessage((IntPtr) windowHandle, KeyCodeTypes.WM_KEYDOWN, (IntPtr) VK_ENTER, 0);
        }

        private static void PressChar(int windowHandle, char c) {
            Thread.Sleep(10);
            PostMessage((IntPtr) windowHandle, WM_CHAR, new IntPtr((Int32) c), 0);
        }

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        private static extern bool PostMessage(IntPtr windowHandle, uint message, IntPtr wParam, uint lParam);

        private const int WM_CHAR = 0x0102;
        private const int VK_ENTER = 0x0D;
    }

    class Program {
        static int windowHandle = 0;
        private const int WH_KEYBOARD_LL = 13;
        private static LowLevelKeyboardProc _proc = HookCallback;
        private static IntPtr _hookID = IntPtr.Zero;

        private static IntPtr SetHook(LowLevelKeyboardProc proc) {
            using (Process curProcess = Process.GetCurrentProcess())
            using (ProcessModule curModule = curProcess.MainModule) {
                return SetWindowsHookEx(WH_KEYBOARD_LL, proc, GetModuleHandle(curModule.ModuleName), 0);
            }
        }

        private delegate IntPtr LowLevelKeyboardProc(int nCode, IntPtr wParam, IntPtr lParam);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr SetWindowsHookEx(int idHook, LowLevelKeyboardProc lpfn, IntPtr hMod, uint dwThreadId);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool UnhookWindowsHookEx(IntPtr hhk);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

        [DllImport(Dlls.KERNEL_32, CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string lpModuleName);

        [DllImport(Dlls.USER_32, SetLastError = true)]
        static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        const string PROGRAM_NAME = "THUG Pro";

        private static IntPtr HookCallback(int hookCode, IntPtr keyCodeType, IntPtr keyCodePointer) {
            bool validHook = hookCode >= 0;
            bool validWindow = windowHandle != 0;
            bool keyPressedDown = keyCodeType == (IntPtr) KeyCodeTypes.WM_KEYDOWN; 

            if (validHook && validWindow && keyPressedDown) {
                int keyCode = Marshal.ReadInt32(keyCodePointer);
                ProcessKeyCode(keyCode);
            }

            return CallNextHookEx(_hookID, hookCode, keyCodeType, keyCodePointer);
        }

        private static void ProcessKeyCode(int keyCode) {
            if (keyCode == KeyCodes.get("F5"))
                Command.Post(windowHandle, Commands.SET_RESTART);
            else if (keyCode == KeyCodes.get("F6"))
                Command.Post(windowHandle, Commands.GOTO_RESTART);
            else if (keyCode == KeyCodes.get("F7"))
                Command.Post(windowHandle, Commands.OBSERVE);
            else if (keyCode == KeyCodes.get("F8"))
                Command.Post(windowHandle, Commands.WARP);
        }

        static void Main(string[] args) {
            windowHandle = (int) FindWindow(null, PROGRAM_NAME);

            if (windowHandle == 0) {
                Console.WriteLine(PROGRAM_NAME + " is not running.");
                return;
            }

            _hookID = SetHook(_proc);
            Application.Run();
            UnhookWindowsHookEx(_hookID);
        }
    }
}
