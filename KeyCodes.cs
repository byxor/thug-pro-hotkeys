using System.Collections.Generic;


namespace ThugPro {
    class KeyCodes {
        private static readonly Dictionary<string, int> map = new Dictionary<string, int> {
            {"F5",    116},
            {"F6",    117},
            {"F7",    118},
            {"F8",    119},
            {"Enter", 13},
        };

        public static int Get(string representation) {
            return map[representation];
        }
    }
}
