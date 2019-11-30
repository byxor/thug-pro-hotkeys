package xyz.byxor.hotkeys.ui

import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE

class Window(
        private val logOutput: LogOutput
) {

    private val frame: JFrame

    init {
        frame = JFrame("THUG Pro Hotkeys")
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.setSize(400, 300)

        frame.add(logOutput.asSwingComponent())
    }

    fun display() {
        frame.isVisible = true
    }
}