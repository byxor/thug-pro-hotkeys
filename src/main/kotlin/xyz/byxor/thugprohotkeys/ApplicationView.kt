package xyz.byxor.thugprohotkeys

import xyz.byxor.thugprohotkeys.lock.LockView
import xyz.byxor.thugprohotkeys.logs.LogView
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.WindowConstants.EXIT_ON_CLOSE

class ApplicationView(
        private val logView: LogView,
        private val lockView: LockView
) {

    private val frame: JFrame

    init {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        frame = JFrame("THUG Pro Hotkeys - 2.2")
        frame.contentPane.layout = BorderLayout(8, 8)
        frame.defaultCloseOperation = EXIT_ON_CLOSE

        frame.add(logView.asSwingComponent(), BorderLayout.CENTER)
        frame.add(lockView.asASwingComponent(), BorderLayout.SOUTH)

        frame.pack()
    }

    fun display() {
        frame.isVisible = true
    }
}