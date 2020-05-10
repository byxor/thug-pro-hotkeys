package xyz.byxor.thugprohotkeys.commands

import xyz.byxor.thugprohotkeys.logs.LogBuffer

const val SET_RESTART_COMMAND = "/set"
const val GOTO_RESTART_COMMAND = "/goto"
const val OBSERVE_COMMAND = "/obs"
const val WARP_COMMAND = "/warp"
const val CLEAR_COMMAND = "/clear"

open class CommandBroker(
        private val gameChat: GameChat,
        private val logBuffer: LogBuffer
) {

    open fun setRestart() = execute(SET_RESTART_COMMAND)
    open fun gotoRestart() = execute(GOTO_RESTART_COMMAND)
    open fun observe() = execute(OBSERVE_COMMAND)
    open fun warpToPlayer() = execute(WARP_COMMAND)
    open fun clearChat() = execute(CLEAR_COMMAND)

    private fun execute(command: String) {
        gameChat.sendMessage(command)
        logBuffer.addMessage("Executed $command")
    }
}
