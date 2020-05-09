package xyz.byxor.hotkeys

import xyz.byxor.hotkeys.logs.LogView
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE

class ApplicationView(
        private val logView: LogView
) {

    private val frame: JFrame

    init {
        frame = JFrame("THUG Pro Hotkeys - 2.2")
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.setSize(500, 400)

        frame.add(logView.asSwingComponent())
    }

    fun display() {
        frame.isVisible = true
    }
}