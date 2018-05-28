namespace ThugPro {
    class Commands {
        public static readonly string SET_RESTART = Command("set");
        public static readonly string GOTO_RESTART = Command("goto");
        public static readonly string OBSERVE = Command("obs");
        public static readonly string WARP = Command("warp");
        private static string Command(string name) { return "/" + name; }
    }
}
