package xyz.byxor.hotkeys.thugpro

import xyz.byxor.hotkeys.core.KeySender
import xyz.byxor.hotkeys.model.Key
import xyz.byxor.hotkeys.model.KeyName
import xyz.byxor.hotkeys.model.KeyPressType

@Deprecated("This class is an untested prototype")
open class ThugProMessageTyper(
        private val keySender: KeySender
) {
    open fun typeMessage(message: String) {
        openChatBox()

        val keys = message.map { character -> character.toKey() }.toTypedArray()
        keySender.send(*keys)

        submitChatBox()
    }

    private fun openChatBox() {
        pressEnter()
        Thread.sleep(20)
    }

    private fun submitChatBox() {
        pressEnter()
    }

    private fun pressEnter() {
        keySender.send(Key(KeyName.ENTER, KeyPressType.DOWN))
    }
}

private fun Char.toKey(): Key {
    try {
        val keyName = when (this) {
            '/' -> KeyName.FORWARD_SLASH
            '\\' -> KeyName.BACKWARDS_SLASH

            'a' -> KeyName.LOWERCASE_A
            'b' -> KeyName.LOWERCASE_B
            'c' -> KeyName.LOWERCASE_C
            'd' -> KeyName.LOWERCASE_D
            'e' -> KeyName.LOWERCASE_E
            'f' -> KeyName.LOWERCASE_F
            'g' -> KeyName.LOWERCASE_G
            'h' -> KeyName.LOWERCASE_H
            'i' -> KeyName.LOWERCASE_I
            'j' -> KeyName.LOWERCASE_J
            'k' -> KeyName.LOWERCASE_K
            'l' -> KeyName.LOWERCASE_L
            'm' -> KeyName.LOWERCASE_M
            'n' -> KeyName.LOWERCASE_N
            'o' -> KeyName.LOWERCASE_O
            'p' -> KeyName.LOWERCASE_P
            'q' -> KeyName.LOWERCASE_Q
            'r' -> KeyName.LOWERCASE_R
            's' -> KeyName.LOWERCASE_S
            't' -> KeyName.LOWERCASE_T
            'u' -> KeyName.LOWERCASE_U
            'v' -> KeyName.LOWERCASE_V
            'w' -> KeyName.LOWERCASE_W
            'x' -> KeyName.LOWERCASE_X
            'y' -> KeyName.LOWERCASE_Y
            'z' -> KeyName.LOWERCASE_Z

            'A' -> KeyName.UPPERCASE_A
            'B' -> KeyName.UPPERCASE_B
            'C' -> KeyName.UPPERCASE_C
            'D' -> KeyName.UPPERCASE_D
            'E' -> KeyName.UPPERCASE_E
            'F' -> KeyName.UPPERCASE_F
            'G' -> KeyName.UPPERCASE_G
            'H' -> KeyName.UPPERCASE_H
            'I' -> KeyName.UPPERCASE_I
            'J' -> KeyName.UPPERCASE_J
            'K' -> KeyName.UPPERCASE_K
            'L' -> KeyName.UPPERCASE_L
            'M' -> KeyName.UPPERCASE_M
            'N' -> KeyName.UPPERCASE_N
            'O' -> KeyName.UPPERCASE_O
            'P' -> KeyName.UPPERCASE_P
            'Q' -> KeyName.UPPERCASE_Q
            'R' -> KeyName.UPPERCASE_R
            'S' -> KeyName.UPPERCASE_S
            'T' -> KeyName.UPPERCASE_T
            'U' -> KeyName.UPPERCASE_U
            'V' -> KeyName.UPPERCASE_V
            'W' -> KeyName.UPPERCASE_W
            'X' -> KeyName.UPPERCASE_X
            'Y' -> KeyName.UPPERCASE_Y
            'Z' -> KeyName.UPPERCASE_Z

            else -> TODO()
        }

        return Key(keyName, KeyPressType.TYPED)
    } catch (exception: NotImplementedError) {
        throw NotImplementedError("No Key object has been defined for the character '$this'")
    }
}