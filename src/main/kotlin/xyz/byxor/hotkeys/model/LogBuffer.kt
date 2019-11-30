package xyz.byxor.hotkeys.model

class LogBuffer(
        val capacity: Int = 50
) {

    private var earliestNode: LogNode? = null
    private var latestNode: LogNode? = null
    private var numberOfNodes: Int = 0

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
}

private data class LogNode(
        val message: String,
        var previous: LogNode?,
        var next: LogNode?
)
