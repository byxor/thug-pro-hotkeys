package xyz.byxor.hotkeys.logs

import xyz.byxor.hotkeys.lock.util.Subscriber
import java.awt.Dimension
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS

class LogView(
        private val controller: LogController
) : Subscriber<LogsUpdatedEvent> {

    private val scrollPane: JScrollPane
    private val textArea: JTextArea

    init {
        textArea = JTextArea()
        textArea.isEditable = false
        textArea.isVisible = true

        scrollPane = JScrollPane(textArea)
        scrollPane.verticalScrollBarPolicy = VERTICAL_SCROLLBAR_ALWAYS
        scrollPane.isVisible = true
        scrollPane.preferredSize = Dimension(500, 400)

        controller.subscribeToUpdates(this)
    }

    override fun notify(event: LogsUpdatedEvent) {
        textArea.text = event.messages.reversed().joinToString("\n")
        textArea.caretPosition = textArea.document.length;
    }

    fun asSwingComponent() = scrollPane
}