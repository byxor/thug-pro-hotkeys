package xyz.byxor.hotkeys.core

import xyz.byxor.hotkeys.model.keys.Key

// Reacts to a key-press
interface KeyConsumer {
    fun onKey(key: Key)
}