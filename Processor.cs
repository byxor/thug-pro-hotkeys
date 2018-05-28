namespace ThugPro {
    class Processor {
        public static void Process(int windowHandle, int keyCode) {
            if (keyCode == KeyCodes.Get("F5"))
                Command.Post(windowHandle, Commands.SET_RESTART);
            else if (keyCode == KeyCodes.Get("F6"))
                Command.Post(windowHandle, Commands.GOTO_RESTART);
            else if (keyCode == KeyCodes.Get("F7"))
                Command.Post(windowHandle, Commands.OBSERVE);
            else if (keyCode == KeyCodes.Get("F8"))
                Command.Post(windowHandle, Commands.WARP);
        }
    }
}
