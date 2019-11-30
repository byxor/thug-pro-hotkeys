package xyz.byxor.hotkeys.core

import xyz.byxor.hotkeys.model.keys.Key

// Sends key-presses to an application
abstract class KeySender {

    abstract fun start()

    fun send(vararg keys: Key) {
        keys.forEach{ key -> send(key) }
    }

    abstract fun send(key: Key)
}
