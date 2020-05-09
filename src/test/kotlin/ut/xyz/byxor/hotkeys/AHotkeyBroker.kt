package ut.xyz.byxor.hotkeys

import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import xyz.byxor.thugprohotkeys.commands.CommandBroker
import xyz.byxor.thugprohotkeys.logs.LogBuffer
import xyz.byxor.thugprohotkeys.keyboard.Key
import xyz.byxor.thugprohotkeys.keyboard.KeyName
import xyz.byxor.thugprohotkeys.keyboard.KeyPressType
import xyz.byxor.thugprohotkeys.lock.Lock
import xyz.byxor.thugprohotkeys.hotkeys.HotkeyBroker

class AHotkeyBroker {

    @Test
    fun `Should execute the "set" command when the user presses F5`() {
        whenTheUserPresses(KeyName.F5)
        thenTheCommandIsExecuted("/set")
    }

    @Test
    fun `Should not execute the "set" command when the user presses F5 if the command is locked`() {
        whenTheSetCommandIsLocked()
        whenTheUserPresses(KeyName.F5)
        thenNoCommandsAreExecuted()
    }

    @Test
    fun `Should execute the "goto" command when the user presses F6`() {
        whenTheUserPresses(KeyName.F6)
        thenTheCommandIsExecuted("/goto")
    }

    @Test
    fun `Should execute the "obs" command when the user presses F7`() {
        whenTheUserPresses(KeyName.F7)
        thenTheCommandIsExecuted("/obs")
    }

    @Test
    fun `Should execute the "warp" command when the user presses F8`() {
        whenTheUserPresses(KeyName.F8)
        thenTheCommandIsExecuted("/warp")
    }

    @Test
    fun `Should execute the "clear" command when the user presses F9`() {
        whenTheUserPresses(KeyName.F9)
        thenTheCommandIsExecuted("/clear")
    }

    private fun whenTheUserPresses(keyName: KeyName) {
        thugProKeyConsumer.sendKey(Key(keyName, KeyPressType.TYPED))
    }

    private fun whenTheSetCommandIsLocked() {
        lock.lock()
    }

    private fun thenTheCommandIsExecuted(command: String) {
        when (command) {
            "/set" -> verify(commandBroker).setRestart()
            "/goto" -> verify(commandBroker).gotoRestart()
            "/obs" -> verify(commandBroker).observe()
            "/warp" -> verify(commandBroker).warp()
            "/clear" -> verify(commandBroker).clear()
        }
    }

    private fun thenNoCommandsAreExecuted() {
        verifyZeroInteractions(commandBroker)
    }

    private val lock = Lock()
    private val commandBroker = mock<CommandBroker>()
    private val thugProKeyConsumer = HotkeyBroker(commandBroker, LogBuffer(), lock)
}