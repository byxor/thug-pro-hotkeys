package xyz.byxor.hotkeys.win32

import xyz.byxor.hotkeys.thugpro.ThugProKeyConsumer
import xyz.byxor.hotkeys.core.KeyConsumer
import xyz.byxor.hotkeys.core.KeySender
import xyz.byxor.hotkeys.thugpro.ThugProMessageTyper

class Win32Application {
    private val keyListener: Win32KeyListener
    private val keySender: KeySender
    private val thugProMessageTyper: ThugProMessageTyper
    private val thugProKeyConsumer: KeyConsumer

    init {
        keySender = Win32KeySender("THUG Pro")
        thugProMessageTyper = ThugProMessageTyper(keySender)
        thugProKeyConsumer = ThugProKeyConsumer(thugProMessageTyper)
        keyListener = Win32KeyListener(thugProKeyConsumer)
    }

    fun start() {
        println("""
            THUG Pro Hotkeys
            ----------------
            F5 = /set
            F6 = /goto
            F7 = /obs
            F8 = /warp
            F9 = /clear
            
            Enjoy!
            ----------------
            
        """.trimIndent())

        keyListener.start()
    }
}