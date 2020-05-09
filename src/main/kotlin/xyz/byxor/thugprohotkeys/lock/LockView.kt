package xyz.byxor.thugprohotkeys.lock

import xyz.byxor.thugprohotkeys.lock.util.Subscriber
import java.awt.Dimension
import javax.swing.*

class LockView(
        private val controller: LockController
) : Subscriber<LockStateChangedEvent> {

    private val component = LockSwingComponent(controller)

    init {
        controller.subscribeToLockStateChanges(this)
    }

    override fun notify(event: LockStateChangedEvent) {
        when (event.isLocked) {
            true -> component.renderLocked()
            false -> component.renderUnlocked()
        }
    }

    fun asASwingComponent(): JComponent = component
}

class LockSwingComponent(controller: LockController) : JPanel() {

    private val buttonTextWhenLocked = "Unlock"
    private val buttonTextWhenUnlocked = "Lock"
    private val buttonPreferredSize = Dimension(320, 56)

    private val tooltipTextWhenLocked = "/set is temporarily disabled via the lock. Click the lock to enable the command."
    private val tooltipTextWhenUnlocked = "/set is temporarily enabled via the lock. Click the lock to disable the command."

    private val toggleButton: JButton

    init {
        toggleButton = JButton()
        toggleButton.minimumSize = buttonPreferredSize
        toggleButton.preferredSize = buttonPreferredSize
        toggleButton.isFocusable = false
        toggleButton.addActionListener { event -> controller.toggleLock() }
        toggleButton.isVisible = true
        add(toggleButton)

        isVisible = true

        renderUnlocked()
    }

    fun renderLocked() {
        toggleButton.text = buttonTextWhenLocked
        toggleButton.toolTipText = tooltipTextWhenLocked
    }

    fun renderUnlocked() {
        toggleButton.text = buttonTextWhenUnlocked
        toggleButton.toolTipText = tooltipTextWhenUnlocked
    }
}
