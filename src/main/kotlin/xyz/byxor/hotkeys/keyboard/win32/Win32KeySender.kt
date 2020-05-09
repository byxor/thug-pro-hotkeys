package xyz.byxor.hotkeys.keyboard.win32

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import xyz.byxor.hotkeys.keyboard.*
import java.lang.IllegalStateException

class Win32KeySender(
        private val windowTitle: String
) : SystemKeySender() {

    override fun start() {
        connectToWindow(windowTitle)
    }

    private fun connectToWindow(title: String) {
        try {
            window = User32.INSTANCE.FindWindow(null, title)
        } catch(exception: IllegalStateException) {
            throw object: ApplicationNotFound() {
                override fun getDescription() = "Could not find window, '$windowTitle'"
            }
        }
    }

    override fun send(key: Key) {
        User32.INSTANCE.PostMessage(
                window,
                key.pressType.toWindowsMessage(),
                WinDef.WPARAM(key.toWindowsKeyCode()),
                WinDef.LPARAM(0)
        )
    }

    private lateinit var window: WinDef.HWND
}

private fun KeyPressType.toWindowsMessage(): Int = when(this) {
    KeyPressType.UP -> TODO()
    KeyPressType.DOWN -> 0x100
    KeyPressType.TYPED -> 0x102
}

private fun Key.toWindowsKeyCode(): Long {
    try {
        return when (this.name) {
            KeyName.ENTER -> 0x0D
            KeyName.FORWARD_SLASH -> 0x2F
            KeyName.BACKWARDS_SLASH -> TODO()

            KeyName.LOWERCASE_A -> 0x61
            KeyName.LOWERCASE_B -> 0x62
            KeyName.LOWERCASE_C -> 0x63
            KeyName.LOWERCASE_D -> 0x64
            KeyName.LOWERCASE_E -> 0x65
            KeyName.LOWERCASE_F -> 0x66
            KeyName.LOWERCASE_G -> 0x67
            KeyName.LOWERCASE_H -> 0x68
            KeyName.LOWERCASE_I -> 0x69
            KeyName.LOWERCASE_J -> 0x6A
            KeyName.LOWERCASE_K -> 0x6B
            KeyName.LOWERCASE_L -> 0x6C
            KeyName.LOWERCASE_M -> 0x6D
            KeyName.LOWERCASE_N -> 0x6E
            KeyName.LOWERCASE_O -> 0x6F
            KeyName.LOWERCASE_P -> 0x70
            KeyName.LOWERCASE_Q -> 0x71
            KeyName.LOWERCASE_R -> 0x72
            KeyName.LOWERCASE_S -> 0x73
            KeyName.LOWERCASE_T -> 0x74
            KeyName.LOWERCASE_U -> 0x75
            KeyName.LOWERCASE_V -> 0x76
            KeyName.LOWERCASE_W -> 0x77
            KeyName.LOWERCASE_X -> 0x78
            KeyName.LOWERCASE_Y -> 0x79
            KeyName.LOWERCASE_Z -> 0x7A

            KeyName.UPPERCASE_A -> 0x41
            KeyName.UPPERCASE_B -> 0x42
            KeyName.UPPERCASE_C -> 0x43
            KeyName.UPPERCASE_D -> 0x44
            KeyName.UPPERCASE_E -> 0x45
            KeyName.UPPERCASE_F -> 0x46
            KeyName.UPPERCASE_G -> 0x47
            KeyName.UPPERCASE_H -> 0x48
            KeyName.UPPERCASE_I -> 0x49
            KeyName.UPPERCASE_J -> 0x4A
            KeyName.UPPERCASE_K -> 0x4B
            KeyName.UPPERCASE_L -> 0x4C
            KeyName.UPPERCASE_M -> 0x4D
            KeyName.UPPERCASE_N -> 0x4E
            KeyName.UPPERCASE_O -> 0x4F
            KeyName.UPPERCASE_P -> 0x50
            KeyName.UPPERCASE_Q -> 0x51
            KeyName.UPPERCASE_R -> 0x52
            KeyName.UPPERCASE_S -> 0x53
            KeyName.UPPERCASE_T -> 0x54
            KeyName.UPPERCASE_U -> 0x55
            KeyName.UPPERCASE_V -> 0x56
            KeyName.UPPERCASE_W -> 0x57
            KeyName.UPPERCASE_X -> 0x58
            KeyName.UPPERCASE_Y -> 0x59
            KeyName.UPPERCASE_Z -> 0x5A

            KeyName.F1 -> TODO()
            KeyName.F2 -> TODO()
            KeyName.F3 -> TODO()
            KeyName.F4 -> TODO()
            KeyName.F5 -> TODO()
            KeyName.F6 -> TODO()
            KeyName.F7 -> TODO()
            KeyName.F8 -> TODO()
            KeyName.F9 -> TODO()
            KeyName.F10 -> TODO()
            KeyName.F11 -> TODO()
            KeyName.F12 -> TODO()
            KeyName.F13 -> TODO()
            KeyName.F14 -> TODO()
            KeyName.F15 -> TODO()
            KeyName.F16 -> TODO()
            KeyName.F17 -> TODO()
            KeyName.F18 -> TODO()
            KeyName.F19 -> TODO()
            KeyName.F20 -> TODO()
            KeyName.F21 -> TODO()
            KeyName.F22 -> TODO()
            KeyName.F23 -> TODO()
            KeyName.F24 -> TODO()
        }
    } catch (exception: NotImplementedError) {
        throw NotImplementedError("No windows key-code has been defined for '${this.name}'")
    }
}