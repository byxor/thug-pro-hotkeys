package xyz.byxor.hotkeys.keyboard

import java.lang.Exception

// Sends key-presses to a process on the user's system
abstract class SystemKeySender {

    abstract fun start()

    fun send(vararg keys: Key) {
        keys.forEach{ key -> send(key) }
    }

    abstract fun send(key: Key)
}

abstract class ApplicationNotFound : Exception() {
    abstract fun getDescription(): String
}