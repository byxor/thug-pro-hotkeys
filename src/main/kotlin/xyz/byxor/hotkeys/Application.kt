package xyz.byxor.hotkeys

import xyz.byxor.hotkeys.commands.CommandBroker
import xyz.byxor.hotkeys.keyboard.ApplicationNotFound
import xyz.byxor.hotkeys.thugpro.ThugProKeyConsumer
import xyz.byxor.hotkeys.core.KeyConsumer
import xyz.byxor.hotkeys.keyboard.SystemKeySender
import xyz.byxor.hotkeys.logs.LogBuffer
import xyz.byxor.hotkeys.logs.LogController
import xyz.byxor.hotkeys.commands.GameChat
import xyz.byxor.hotkeys.logs.LogView
import xyz.byxor.hotkeys.keyboard.win32.Win32KeyListener
import xyz.byxor.hotkeys.keyboard.win32.Win32KeySender
import xyz.byxor.hotkeys.lock.Lock
import xyz.byxor.hotkeys.lock.LockController
import xyz.byxor.hotkeys.lock.LockView

class Application {

    private val keyListener: Win32KeyListener
    private val keySender: SystemKeySender

    private val logBuffer: LogBuffer
    private val logController: LogController
    private val logView: LogView

    private val lock: Lock
    private val lockController: LockController
    private val lockView: LockView

    private val gameChat: GameChat
    private val thugProKeyConsumer: KeyConsumer
    private val commandBroker: CommandBroker

    private val applicationView: ApplicationView

    init {
        logBuffer = LogBuffer()
        logController = LogController(logBuffer)
        logView = LogView(logController)

        lock = Lock()
        lockController = LockController(lock)
        lockView = LockView(lockController)

        applicationView = ApplicationView(logView, lockView)

        keySender = Win32KeySender("THUG Pro")
        gameChat = GameChat(keySender)
        commandBroker = CommandBroker()

        thugProKeyConsumer = ThugProKeyConsumer(commandBroker, logBuffer, lock)
        keyListener = Win32KeyListener(thugProKeyConsumer)
    }

    fun start() {
        applicationView.display()

        logBuffer.addMessage("""
            -----------------------------------------------------
            | THUG Pro Hotkeys, from choko & byxor              |
            |                                                   |
            | Commands:                                         |
            |  F5 = /set                                        |
            |  F6 = /goto                                       |
            |  F7 = /obs                                        |
            |  F8 = /warp                                       |
            |  F9 = /clear                                      |
            |                                                   |
            | Source code:                                      |
            |  https://github.com/byxor/thug-pro-hotkeys        |
            |                                                   |
            | Tip:                                              |
            |  Use the lock button to avoid accidentally        |
            |  overwriting your restart point.                  |
            -----------------------------------------------------
            
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