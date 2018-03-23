using System;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Threading;
using System.Diagnostics;

namespace tprosetgoto {
    class Program {
        static int hwnd = 0;
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

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr SetWindowsHookEx(int idHook, LowLevelKeyboardProc lpfn, IntPtr hMod, uint dwThreadId);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool UnhookWindowsHookEx(IntPtr hhk);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode,IntPtr wParam, IntPtr lParam);

        [DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string lpModuleName);

        //---------------------------------------------

        [DllImport("user32.dll", SetLastError = true)]
        static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        public static extern bool PostMessage(IntPtr hWnd, uint Msg, IntPtr wParam, uint lParam);

        const string progname = "THUG Pro";
        const int WM_KEYUP = 0x101;
        const int WM_CHAR = 0x0102;
        const int VK_ENTER = 0x0D;

        public static void pressEnter(int hwnd) {
            PostMessage((IntPtr)hwnd, WM_KEYDOWN, (IntPtr)VK_ENTER, 0);
        }

        public static void pressChar(int hwnd, char c) {
            Thread.Sleep(10);
            PostMessage((IntPtr)hwnd, WM_CHAR, new IntPtr((Int32)c), 0);
        }

        public static void postCommand(int hwnd, string msg) {
            pressEnter(hwnd);
            Thread.Sleep(5);
            foreach (char c in msg)
                pressChar(hwnd, c);
            
            pressEnter(hwnd);
        }

        private static IntPtr HookCallback(int nCode, IntPtr wParam, IntPtr lParam) {
            if (nCode >= 0 && wParam == (IntPtr)WM_KEYDOWN) {
                int vkCode = Marshal.ReadInt32(lParam);
                if (hwnd != 0) {
                    if (vkCode == 116) //F5
                        postCommand(hwnd, "/set");
                    else if (vkCode == 117) //F6
                        postCommand(hwnd, "/goto");
                    else if (vkCode == 118) //F7
                        postCommand(hwnd, "/obs");
                    else if (vkCode == 119) //F8
                        postCommand(hwnd, "/warp");
                }
            }
            return CallNextHookEx(_hookID, nCode, wParam, lParam);
        }

        static void Main(string[] args) {
            hwnd = (int)FindWindow(null, progname);
            if (hwnd == 0) {
                Console.WriteLine("THUGPro is not running");
                return;
            }

            _hookID = SetHook(_proc);
            Application.Run();
            UnhookWindowsHookEx(_hookID);
        }
    }
}
