using System;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Diagnostics;

namespace ThugPro {

    class KeyCodeTypes {
        public const int WM_KEYDOWN = 0x0100;
        public const int WM_CHAR = 0x0102;
    }

    class Dlls {
        public const string USER_32 = "user32.dll";
        public const string KERNEL_32 = "kernel32.dll";
    }

    class Program {
        static int windowHandle = 0;
        private const int WH_KEYBOARD_LL = 13;
        private static LowLevelKeyboardProc _proc = HookCallback;
        private static IntPtr _hookId = IntPtr.Zero;

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

            return CallNextHookEx(_hookId, hookCode, keyCodeType, keyCodePointer);
        }

        private static void ProcessKeyCode(int keyCode) {
            if (keyCode == KeyCodes.Get("F5"))
                Command.Post(windowHandle, Commands.SET_RESTART);
            else if (keyCode == KeyCodes.Get("F6"))
                Command.Post(windowHandle, Commands.GOTO_RESTART);
            else if (keyCode == KeyCodes.Get("F7"))
                Command.Post(windowHandle, Commands.OBSERVE);
            else if (keyCode == KeyCodes.Get("F8"))
                Command.Post(windowHandle, Commands.WARP);
        }

        static void Main(string[] args) {
            windowHandle = (int) FindWindow(null, PROGRAM_NAME);

            if (windowHandle == 0) {
                Console.WriteLine(PROGRAM_NAME + " is not running.");
                return;
            }

            _hookId = SetHook(_proc);
            Application.Run();
            UnhookWindowsHookEx(_hookId);
        }
    }
}
