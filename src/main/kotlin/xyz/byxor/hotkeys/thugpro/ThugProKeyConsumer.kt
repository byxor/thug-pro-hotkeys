package xyz.byxor.hotkeys.thugpro

import xyz.byxor.hotkeys.model.Key
import xyz.byxor.hotkeys.core.KeyConsumer
import xyz.byxor.hotkeys.model.KeyName

class ThugProKeyConsumer(
        private val thugProMessageTyper: ThugProMessageTyper
) : KeyConsumer {

    override fun onKey(key: Key) {
        val message = when(key.name) {
            KeyName.F5 -> "/set"
            KeyName.F6 -> "/goto"
            KeyName.F7 -> "/obs"
            KeyName.F8 -> "/warp"
            KeyName.F9 -> "/clear"
            else -> null
        }

        if (message != null) {
            println("Pressed ${key.name}, typing '${message}'")
            thugProMessageTyper.typeMessage(message)
        }
    }
}