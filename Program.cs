using System;
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

    class Dlls {
        public const string USER_32 = "user32.dll";
        public const string KERNEL_32 = "kernel32.dll";
    }

    class Program {
        static int windowHandle = 0;
        private const int WH_KEYBOARD_LL = 13;
        private const int WM_KEYDOWN = 0x0100;
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
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode,IntPtr wParam, IntPtr lParam);

        [DllImport(Dlls.KERNEL_32, CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string lpModuleName);

        //---------------------------------------------

        [DllImport(Dlls.USER_32, SetLastError = true)]
        static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        public static extern bool PostMessage(IntPtr windowHandle, uint Msg, IntPtr wParam, uint lParam);

        const string PROGRAM_NAME = "THUG Pro";

        const int WM_CHAR = 0x0102;
        const int VK_ENTER = 0x0D;

        public static void PressEnter(int windowHandle) {
            PostMessage((IntPtr)windowHandle, WM_KEYDOWN, (IntPtr)VK_ENTER, 0);
        }

        public static void PressChar(int windowHandle, char c) {
            Thread.Sleep(10);
            PostMessage((IntPtr)windowHandle, WM_CHAR, new IntPtr((Int32)c), 0);
        }

        public static void PostCommand(int windowHandle, string msg) {
            PressEnter(windowHandle);
            Thread.Sleep(5);
            foreach (char c in msg)
                PressChar(windowHandle, c);
            
            PressEnter(windowHandle);
        }

        private static IntPtr HookCallback(int nCode, IntPtr wParam, IntPtr lParam) {
            if (nCode >= 0 && wParam == (IntPtr) WM_KEYDOWN) {
                int keycode = Marshal.ReadInt32(lParam);
                if (windowHandle != 0) {
                    if (keycode == KeyCodes.get("F5"))
                        PostCommand(windowHandle, Commands.SET_RESTART);
                    else if (keycode == KeyCodes.get("F6"))
                        PostCommand(windowHandle, Commands.GOTO_RESTART);
                    else if (keycode == KeyCodes.get("F7"))
                        PostCommand(windowHandle, Commands.OBSERVE);
                    else if (keycode == KeyCodes.get("F8"))
                        PostCommand(windowHandle, Commands.WARP);
                }
            }
            return CallNextHookEx(_hookID, nCode, wParam, lParam);
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
