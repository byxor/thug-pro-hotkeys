using System;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Diagnostics;

namespace ThugPro {

    class KeyCodeTypes {
        public const int WM_KEYDOWN = 0x100;
        public const int WM_CHAR = 0x102;
        public const int WH_KEYBOARD_LL = 0xD;
    }

    class Dlls {
        public const string USER_32 = "user32.dll";
        public const string KERNEL_32 = "kernel32.dll";
    }

    class Hooker {
        public static void AttachToWindow(string programName) {
            windowHandle = (int) FindWindow(null, programName);
        }

        public static bool Attached() {
            return windowHandle != 0;
        }

        public static int windowHandle = 0;

        public static IntPtr hookId = IntPtr.Zero;

        public static IntPtr SetHook(LowLevelKeyboardProc proc) {
            using (Process curProcess = Process.GetCurrentProcess())
            using (ProcessModule curModule = curProcess.MainModule) {
                return SetWindowsHookEx(KeyCodeTypes.WH_KEYBOARD_LL, proc, GetModuleHandle(curModule.ModuleName), 0);
            }
        }

        public delegate IntPtr LowLevelKeyboardProc(int nCode, IntPtr wParam, IntPtr lParam);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        public static extern IntPtr SetWindowsHookEx(int idHook, LowLevelKeyboardProc lpfn, IntPtr hMod, uint dwThreadId);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool UnhookWindowsHookEx(IntPtr hhk);

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        public static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

        [DllImport(Dlls.KERNEL_32, CharSet = CharSet.Auto, SetLastError = true)]
        public static extern IntPtr GetModuleHandle(string lpModuleName);

        [DllImport(Dlls.USER_32, SetLastError = true)]
        public static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        public static IntPtr HookCallback(int hookCode, IntPtr keyCodeType, IntPtr keyCodePointer) {
            bool validHook = hookCode >= 0;
            bool validWindow = windowHandle != 0;
            bool keyPressedDown = keyCodeType == (IntPtr) KeyCodeTypes.WM_KEYDOWN; 

            if (validHook && validWindow && keyPressedDown) {
                int keyCode = Marshal.ReadInt32(keyCodePointer);
                Processor.Process(windowHandle, keyCode);
            }

            return CallNextHookEx(hookId, hookCode, keyCodeType, keyCodePointer);
        }


    }

    class Program {
        const string PROGRAM_NAME = "THUG Pro";

        static void Main(string[] args) {
            Hooker.AttachToWindow(PROGRAM_NAME);
            
            if (Hooker.Attached()) {
                Hooker.hookId = Hooker.SetHook(Hooker.HookCallback);
                Application.Run();
                Hooker.UnhookWindowsHookEx(Hooker.hookId);
            } else {
                Console.WriteLine(PROGRAM_NAME + " is not running.");
            }
        }
    }
}
