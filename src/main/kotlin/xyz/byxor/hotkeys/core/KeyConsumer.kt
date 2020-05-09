package xyz.byxor.hotkeys.core

import xyz.byxor.hotkeys.keyboard.Key

// Reacts to a key-press
@Deprecated("May be replaced with publish/subscribe pattern soon")
interface KeyConsumer {
    fun onKey(key: Key)
}