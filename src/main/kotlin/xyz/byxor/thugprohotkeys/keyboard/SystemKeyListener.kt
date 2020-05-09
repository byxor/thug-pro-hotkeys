package xyz.byxor.thugprohotkeys.keyboard

// Starts and stops a mechanism that listens for global key-presses on the user's system
interface SystemKeyListener {
    fun start()
    fun stop()
}