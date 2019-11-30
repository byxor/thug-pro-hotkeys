package xyz.byxor.hotkeys.ui

import xyz.byxor.hotkeys.core.ModelListener
import xyz.byxor.hotkeys.model.LogBuffer
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS

class LogOutput : ModelListener<LogBuffer> {

    private val scrollPane: JScrollPane
    private val textArea: JTextArea

    init {
        textArea = JTextArea()
        textArea.isEditable = false

        scrollPane = JScrollPane(textArea)
        scrollPane.verticalScrollBarPolicy = VERTICAL_SCROLLBAR_ALWAYS
    }

    override fun onModelChanged(logBuffer: LogBuffer) {
        textArea.text = logBuffer.getMessages().reversed().joinToString("\n")
        textArea.caretPosition = textArea.document.length;
    }

    fun asSwingComponent() = scrollPane
}