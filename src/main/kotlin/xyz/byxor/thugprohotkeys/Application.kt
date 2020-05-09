package xyz.byxor.thugprohotkeys

import xyz.byxor.thugprohotkeys.commands.*
import xyz.byxor.thugprohotkeys.keyboard.ApplicationNotFound
import xyz.byxor.thugprohotkeys.hotkeys.HotkeyBroker
import xyz.byxor.thugprohotkeys.keyboard.SystemKeySender
import xyz.byxor.thugprohotkeys.logs.LogBuffer
import xyz.byxor.thugprohotkeys.logs.LogController
import xyz.byxor.thugprohotkeys.keyboard.SystemKeyListener
import xyz.byxor.thugprohotkeys.logs.LogView
import xyz.byxor.thugprohotkeys.keyboard.win32.Win32KeyListener
import xyz.byxor.thugprohotkeys.keyboard.win32.Win32KeySender
import xyz.byxor.thugprohotkeys.lock.Lock
import xyz.byxor.thugprohotkeys.lock.LockController
import xyz.byxor.thugprohotkeys.lock.LockView

class Application {

    private val keyListener: SystemKeyListener
    private val keySender: SystemKeySender

    private val logBuffer: LogBuffer
    private val logController: LogController
    private val logView: LogView

    private val lock: Lock
    private val lockController: LockController
    private val lockView: LockView

    private val hotkeyBroker: HotkeyBroker
    private val commandBroker: CommandBroker
    private val gameChat: GameChat

    private val applicationView: ApplicationView

    init {
        keySender = Win32KeySender("THUG Pro")

        logBuffer = LogBuffer()
        logController = LogController(logBuffer)
        logView = LogView(logController)

        lock = Lock()
        lockController = LockController(lock)
        lockView = LockView(lockController)

        gameChat = GameChat(keySender)
        commandBroker = CommandBroker(gameChat, logBuffer)
        hotkeyBroker = HotkeyBroker(commandBroker, logBuffer, lock)

        keyListener = Win32KeyListener(hotkeyBroker)

        applicationView = ApplicationView(logView, lockView)
    }

    fun start() {
        applicationView.display()

        logBuffer.addMessage("""
            -----------------------------------------------------
            | THUG Pro Hotkeys, from choko & byxor              |
            |                                                   |
            | Commands:                                         |
            |  F5 = $SET_RESTART_COMMAND
            |  F6 = $GOTO_RESTART_COMMAND
            |  F7 = $OBSERVE_COMMAND
            |  F8 = $WARP_COMMAND
            |  F9 = $CLEAR_COMMAND
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