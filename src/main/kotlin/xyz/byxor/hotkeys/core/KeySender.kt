package xyz.byxor.hotkeys.core

import xyz.byxor.hotkeys.keys.Key
import java.lang.Exception

// Sends key-presses to an application
abstract class KeySender {

    abstract fun start()

    fun send(vararg keys: Key) {
        keys.forEach{ key -> send(key) }
    }

    abstract fun send(key: Key)
}

abstract class ApplicationNotFound : Exception() {
    abstract fun getDescription(): String
}