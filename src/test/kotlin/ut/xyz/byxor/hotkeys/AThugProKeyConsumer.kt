package ut.xyz.byxor.hotkeys

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import xyz.byxor.hotkeys.logs.LogBuffer
import xyz.byxor.hotkeys.keys.Key
import xyz.byxor.hotkeys.keys.KeyName
import xyz.byxor.hotkeys.keys.KeyPressType
import xyz.byxor.hotkeys.thugpro.ThugProKeyConsumer
import xyz.byxor.hotkeys.thugpro.ThugProMessageTyper

class AThugProKeyConsumer {

    @Test
    fun `Should execute the "set" command when the user presses F5`() {
        whenTheUserPresses(KeyName.F5)
        thenTheMessageIsTyped("/set")
    }

    @Test
    fun `Should execute the "goto" command when the user presses F6`() {
        whenTheUserPresses(KeyName.F6)
        thenTheMessageIsTyped("/goto")
    }

    @Test
    fun `Should execute the "obs" command when the user presses F7`() {
        whenTheUserPresses(KeyName.F7)
        thenTheMessageIsTyped("/obs")
    }

    @Test
    fun `Should execute the "warp" command when the user presses F8`() {
        whenTheUserPresses(KeyName.F8)
        thenTheMessageIsTyped("/warp")
    }

    @Test
    fun `Should execute the "clear" command when the user presses F9`() {
        whenTheUserPresses(KeyName.F9)
        thenTheMessageIsTyped("/clear")
    }

    private fun whenTheUserPresses(keyName: KeyName) {
        thugProKeyConsumer.onKey(Key(keyName, KeyPressType.TYPED))
    }

    private fun thenTheMessageIsTyped(message: String) {
        verify(thugProMessageTyper).typeMessage(message)
    }

    private val thugProMessageTyper = mock<ThugProMessageTyper>()
    private val thugProKeyConsumer = ThugProKeyConsumer(thugProMessageTyper, LogBuffer())
}