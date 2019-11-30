package xyz.byxor.hotkeys.core

import xyz.byxor.hotkeys.model.Key

// Reacts to a key-press
interface KeyConsumer {
    fun onKey(key: Key)
}