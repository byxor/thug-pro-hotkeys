package ut.xyz.byxor.hotkeys

import org.junit.Test
import xyz.byxor.hotkeys.model.LogBuffer

class ALogBuffer {

    private lateinit var logBuffer: LogBuffer

    @Test
    fun `Should store messages`() {
        logBuffer = LogBuffer()

        logBuffer.addMessage("first")
        logBuffer.addMessage("second")
        logBuffer.addMessage("third")

        assertThatTheMessagesAre("third", "second", "first")
    }

    @Test
    fun `Should not exceed the specified capacity`() {
        logBuffer = LogBuffer(3)

        logBuffer.addMessage("first")
        logBuffer.addMessage("second")
        logBuffer.addMessage("third")
        logBuffer.addMessage("fourth")

        assertThatTheMessagesAre("fourth", "third", "second")
    }

    @Test
    fun `Should correctly handle a capacity of 0`() {
        logBuffer = LogBuffer(0)

        logBuffer.addMessage("first")

        assertThatThereAreNoMessages()
    }

    @Test
    fun `Should correctly handle a capacity of 1`() {
        logBuffer = LogBuffer(1)

        logBuffer.addMessage("first")
        assertThatTheMessagesAre("first")

        logBuffer.addMessage("second")
        assertThatTheMessagesAre("second")

        logBuffer.addMessage("third")
        assertThatTheMessagesAre("third")
    }

    @Test
    fun `Should correctly handle a capacity of 2`() {
        logBuffer = LogBuffer(2)

        logBuffer.addMessage("first")
        assertThatTheMessagesAre("first")

        logBuffer.addMessage("second")
        assertThatTheMessagesAre("second", "first")

        logBuffer.addMessage("third")
        assertThatTheMessagesAre("third", "second")
    }

    @Test
    fun `Should allow the latest message to be fetched`() {
        logBuffer = LogBuffer()

        assert(logBuffer.getLatestMessage() == "")

        logBuffer.addMessage("first")
        assert(logBuffer.getLatestMessage() == "first")

        logBuffer.addMessage("second")
        assert(logBuffer.getLatestMessage() == "second")
    }

    private fun assertThatTheMessagesAre(vararg messages: String) {
        assert(logBuffer.getMessages() == messages.toList())
    }

    private fun assertThatThereAreNoMessages(vararg messages: String) {
        assert(logBuffer.getMessages() == emptyList<String>())
    }
}