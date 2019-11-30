package xyz.byxor.hotkeys.win32

import xyz.byxor.hotkeys.thugpro.ThugProKeyConsumer
import xyz.byxor.hotkeys.core.KeyConsumer
import xyz.byxor.hotkeys.core.KeySender
import xyz.byxor.hotkeys.model.LogBuffer
import xyz.byxor.hotkeys.thugpro.ThugProMessageTyper
import xyz.byxor.hotkeys.ui.LogOutput
import xyz.byxor.hotkeys.ui.Window

class Win32Application {

    private val keyListener: Win32KeyListener
    private val keySender: KeySender

    private val logBuffer: LogBuffer

    private val thugProMessageTyper: ThugProMessageTyper
    private val thugProKeyConsumer: KeyConsumer

    private val logOutput: LogOutput
    private val window: Window

    init {
        logOutput = LogOutput()
        window = Window(logOutput)

        logBuffer = LogBuffer(100, logOutput)
        keySender = Win32KeySender("THUG Pro", logBuffer)
        thugProMessageTyper = ThugProMessageTyper(keySender)
        thugProKeyConsumer = ThugProKeyConsumer(thugProMessageTyper, logBuffer)
        keyListener = Win32KeyListener(thugProKeyConsumer)
    }

    fun start() {
        window.display()

        logBuffer.addMessage("""
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

        keySender.start()
        keyListener.start()
    }
}