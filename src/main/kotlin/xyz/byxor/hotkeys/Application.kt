package xyz.byxor.hotkeys

import xyz.byxor.hotkeys.keys.ApplicationNotFound
import xyz.byxor.hotkeys.thugpro.ThugProKeyConsumer
import xyz.byxor.hotkeys.core.KeyConsumer
import xyz.byxor.hotkeys.keys.SystemKeySender
import xyz.byxor.hotkeys.logs.LogBuffer
import xyz.byxor.hotkeys.logs.LogController
import xyz.byxor.hotkeys.thugpro.ThugProMessageTyper
import xyz.byxor.hotkeys.logs.LogView
import xyz.byxor.hotkeys.keys.win32.Win32KeyListener
import xyz.byxor.hotkeys.keys.win32.Win32KeySender

class Application {

    private val keyListener: Win32KeyListener
    private val keySender: SystemKeySender

    private val logBuffer: LogBuffer
    private val logController: LogController
    private val logView: LogView

    private val thugProMessageTyper: ThugProMessageTyper
    private val thugProKeyConsumer: KeyConsumer

    private val applicationView: ApplicationView

    init {
        logBuffer = LogBuffer()
        logController = LogController(logBuffer)
        logView = LogView(logController)

        applicationView = ApplicationView(logView)

        keySender = Win32KeySender("THUG Pro")
        thugProMessageTyper = ThugProMessageTyper(keySender)
        thugProKeyConsumer = ThugProKeyConsumer(thugProMessageTyper, logBuffer)
        keyListener = Win32KeyListener(thugProKeyConsumer)
    }

    fun start() {
        applicationView.display()

        logBuffer.addMessage("""
            THUG Pro Hotkeys by choko & byxor
            ----------------
            F5 = /set
            F6 = /goto
            F7 = /obs
            F8 = /warp
            F9 = /clear
            
            Enjoy!
            
            Source code available at: https://www.github.com/byxor/thug-pro-hotkeys
            ----------------
            
        """.trimIndent())

        try {
            keySender.start()
            keyListener.start()
        } catch(exception: ApplicationNotFound) {
            logBuffer.addMessage(exception.getDescription())
            logBuffer.addMessage("Please open THUG Pro and restart this program")
        }
    }
}