package xyz.byxor.thugprohotkeys.hotkeys

import xyz.byxor.thugprohotkeys.commands.CommandBroker
import xyz.byxor.thugprohotkeys.keyboard.Key
import xyz.byxor.thugprohotkeys.keyboard.KeyName
import xyz.byxor.thugprohotkeys.lock.Lock

@Deprecated("Implements a deprecated interface")
class HotkeyBroker(
        private val commandBroker: CommandBroker,
        private val lock: Lock
) {

    fun sendKey(key: Key) {
        when(key.name) {
            KeyName.F5 -> {
                if (lock.isUnlocked())
                    commandBroker.setRestart()
                else
                    return
            }
            KeyName.F6 -> commandBroker.gotoRestart()
            KeyName.F7 -> commandBroker.observe()
            KeyName.F8 -> commandBroker.warpToPlayer()
            KeyName.F9 -> commandBroker.clearChat()
            else -> {}
        }


    }
}