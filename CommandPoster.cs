using System;
using System.Runtime.InteropServices;
using System.Threading;


namespace ThugPro {

    class Command {
        public static void Post(int windowHandle, string message) {
            ToggleChatBox(windowHandle);
            TypeMessage(windowHandle, message);
            ToggleChatBox(windowHandle);
        }

        private static void ToggleChatBox(int windowHandle) {
            PressEnter(windowHandle);
            Thread.Sleep(Timing.CHATBOX_WAIT_MILLISECONDS);
        }

        private static void PressEnter(int windowHandle) {
            PostMessage((IntPtr) windowHandle, KeyCodeTypes.WM_KEYDOWN, (IntPtr) KeyCodes.Get("Enter"), 0);
        }

        private static void TypeMessage(int windowHandle, string message) {
            foreach (char c in message)
                PressChar(windowHandle, c);
        }

        private static void PressChar(int windowHandle, char c) {
            Thread.Sleep(Timing.CHARACTER_WAIT_MILLISECONDS);
            PostMessage((IntPtr) windowHandle, KeyCodeTypes.WM_CHAR, new IntPtr((Int32) c), 0);
        }

        [DllImport(Dlls.USER_32, CharSet = CharSet.Auto, SetLastError = true)]
        private static extern bool PostMessage(IntPtr windowHandle, uint message, IntPtr wParam, uint lParam);
    }

}
