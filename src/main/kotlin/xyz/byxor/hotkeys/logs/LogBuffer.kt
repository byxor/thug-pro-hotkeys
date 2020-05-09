package xyz.byxor.hotkeys.logs

import xyz.byxor.hotkeys.lock.util.Publisher
import xyz.byxor.hotkeys.lock.util.Subscriber

class LogBuffer(private val capacity: Int = 50) {

    private val publisher = Publisher<LogsUpdatedEvent>()

    // The log messages are stored in a linked list.
    private var earliestNode: LogNode? = null
    private var latestNode: LogNode? = null
    private var numberOfNodes: Int = 0

    fun getLatestMessage(): String {
        if (latestNode != null) {
            return latestNode!!.message
        } else {
            return ""
        }
    }

    fun getMessages(): List<String> {
        val messages = mutableListOf<String>()

        var currentNode = latestNode
        while (currentNode != null) {
            messages.add(currentNode.message)
            currentNode = currentNode.previous
        }

        return messages.toList()
    }

    fun addMessage(message: String) {
        if (numberOfNodes == 0) {
            insertFirstNode(message)
        } else {
            insertNonFirstNode(message)
        }

        if (numberOfNodes > capacity) {
            removeEarliestNode()
        }

        notifySubscribersOfUpdates()
    }

    private fun notifySubscribersOfUpdates() {
        publisher.publish(LogsUpdatedEvent(getMessages()))
    }

    private fun insertFirstNode(message: String) {
        latestNode = LogNode(message, null, null)
        earliestNode = latestNode
        numberOfNodes++
    }

    private fun insertNonFirstNode(message: String) {
        val secondLatestLogNode = latestNode!!
        latestNode = LogNode(message, secondLatestLogNode, null)
        secondLatestLogNode.next = latestNode
        numberOfNodes++
    }

    private fun removeEarliestNode() {
        if (numberOfNodes == 1) {
            removeOnlyNode()
        } else {
            removeEarliestOfMultipleNodes()
        }
    }

    private fun removeOnlyNode() {
        earliestNode = null
        latestNode = null
        numberOfNodes--
    }

    private fun removeEarliestOfMultipleNodes() {
        val secondEarliestNode = earliestNode!!.next!!
        secondEarliestNode.previous = null
        earliestNode = secondEarliestNode
        numberOfNodes--
    }

    fun subscribeToUpdates(subscriber: Subscriber<LogsUpdatedEvent>) {
        publisher.subscribe(subscriber)
    }
}

private data class LogNode(
        val message: String,
        var previous: LogNode?,
        var next: LogNode?
)
