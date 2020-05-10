package ut.xyz.byxor.thugprohotkeys;

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import xyz.byxor.thugprohotkeys.commands.CommandBroker
import xyz.byxor.thugprohotkeys.commands.GameChat
import xyz.byxor.thugprohotkeys.logs.LogBuffer

public class ACommandBroker {

    @Test
    fun `Should type the "set" command in the game chat`() {
        commandBroker.setRestart()
        assertCommandWasTyped("/set")
    }

    @Test
    fun `Should type the "goto" command in the game chat`() {
        commandBroker.gotoRestart()
        assertCommandWasTyped("/goto")
    }

    @Test
    fun `Should type the "obs" command in the game chat`() {
        commandBroker.observe()
        assertCommandWasTyped("/obs")
    }

    @Test
    fun `Should type the "warp" command in the game chat`() {
        commandBroker.warpToPlayer()
        assertCommandWasTyped("/warp")
    }

    @Test
    fun `Should type the "clear" command in the game chat`() {
        commandBroker.clearChat()
        assertCommandWasTyped("/clear")
    }

    private fun assertCommandWasTyped(command: String) = verify(gameChat).sendMessage(command)

    private val gameChat = mock<GameChat>()
    private val commandBroker = CommandBroker(gameChat, LogBuffer())
}
