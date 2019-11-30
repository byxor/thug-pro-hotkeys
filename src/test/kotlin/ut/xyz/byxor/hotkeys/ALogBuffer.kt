package ut.xyz.byxor.hotkeys

import org.junit.Test
import xyz.byxor.hotkeys.model.LogBuffer
import kotlin.math.log

class ALogBuffer {

    @Test
    fun `Should store messages`() {
        val logBuffer = LogBuffer()

        logBuffer.addMessage("first")
        logBuffer.addMessage("second")
        logBuffer.addMessage("third")

        assert(logBuffer.getMessages() == listOf("third", "second", "first"))
    }

    @Test
    fun `Should not exceed the specified capacity`() {
        val logBuffer = LogBuffer(3)

        logBuffer.addMessage("first")
        logBuffer.addMessage("second")
        logBuffer.addMessage("third")
        logBuffer.addMessage("fourth")

        assert(logBuffer.getMessages() == listOf("fourth", "third", "second"))
    }

    @Test
    fun `Should correctly handle a capacity of 1`() {
        val logBuffer = LogBuffer(1)

        logBuffer.addMessage("first")
        assert(logBuffer.getMessages() == listOf("first"))

        logBuffer.addMessage("second")
        assert(logBuffer.getMessages() == listOf("second"))

        logBuffer.addMessage("third")
        assert(logBuffer.getMessages() == listOf("third"))
    }

    @Test
    fun `Should correctly handle a capacity of 2`() {
        val logBuffer = LogBuffer(2)

        logBuffer.addMessage("first")
        assert(logBuffer.getMessages() == listOf("first"))

        logBuffer.addMessage("second")
        assert(logBuffer.getMessages() == listOf("second", "first"))

        logBuffer.addMessage("third")
        assert(logBuffer.getMessages() == listOf("third", "second"))
    }
}