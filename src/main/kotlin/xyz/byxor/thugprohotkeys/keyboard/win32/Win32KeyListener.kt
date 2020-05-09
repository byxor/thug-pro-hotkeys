package xyz.byxor.thugprohotkeys.keyboard.win32

import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import xyz.byxor.thugprohotkeys.keyboard.Key
import xyz.byxor.thugprohotkeys.keyboard.SystemKeyListener
import xyz.byxor.thugprohotkeys.keyboard.KeyName
import xyz.byxor.thugprohotkeys.keyboard.KeyPressType
import xyz.byxor.thugprohotkeys.hotkeys.HotkeyBroker
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

class Win32KeyListener(private val hotkeyBroker: HotkeyBroker) : SystemKeyListener {

    override fun start() {
        disableInternalLogger()
        registerInternalListener()
    }

    override fun stop() {
        unregisterInternalListener()
    }

    private fun disableInternalLogger() {
        LogManager.getLogManager().reset()
        val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
        logger.level = Level.OFF
    }

    private fun registerInternalListener() {
        GlobalScreen.registerNativeHook()

        GlobalScreen.addNativeKeyListener(object: NativeKeyListener {
            override fun nativeKeyPressed(event: NativeKeyEvent) {
                val key = event.toKey()
                hotkeyBroker.sendKey(key)
            }

            override fun nativeKeyTyped(ignored: NativeKeyEvent) {}
            override fun nativeKeyReleased(ignored: NativeKeyEvent) {}
        })
    }

    private fun unregisterInternalListener() {
        GlobalScreen.unregisterNativeHook()
    }
}

private fun NativeKeyEvent.toKey(): Key {
    try {
        val keyName = when(this.keyCode) {
            0x3F -> KeyName.F5
            0x40 -> KeyName.F6
            0x41 -> KeyName.F7
            0x42 -> KeyName.F8
            0x43 -> KeyName.F9
            else -> TODO();
        }

        return Key(keyName, KeyPressType.TYPED)
    } catch(exception: NotImplementedError) {
        val shouldReportError = false

        if (shouldReportError) {
            val keyCodeInHex = java.lang.Integer.toHexString(this.keyCode)
            throw NotImplementedError("No key-name has been defined for NativeKeyEvent key-code '$keyCodeInHex'.")
        } else {
            val placeHolderKey = Key(KeyName.UPPERCASE_O, KeyPressType.TYPED)
            return placeHolderKey
        }
    } finally {

    }
}