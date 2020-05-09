package xyz.byxor.hotkeys.thugpro

import xyz.byxor.hotkeys.commands.CommandBroker
import xyz.byxor.hotkeys.keyboard.Key
import xyz.byxor.hotkeys.core.KeyConsumer
import xyz.byxor.hotkeys.keyboard.KeyName
import xyz.byxor.hotkeys.lock.Lock
import xyz.byxor.hotkeys.logs.LogBuffer

@Deprecated("Implements a deprecated interface")
class ThugProKeyConsumer(
        private val commandBroker: CommandBroker,
        private val logBuffer: LogBuffer,
        private val lock: Lock
) : KeyConsumer {

    override fun onKey(key: Key) {
        when(key.name) {
            KeyName.F5 -> {
                if (!lock.isLocked())
                    commandBroker.setRestart()
            }
            KeyName.F6 -> commandBroker.gotoRestart()
            KeyName.F7 -> commandBroker.observe()
            KeyName.F8 -> commandBroker.warp()
            KeyName.F9 -> commandBroker.clear()
            else -> {}
        }
    }
}