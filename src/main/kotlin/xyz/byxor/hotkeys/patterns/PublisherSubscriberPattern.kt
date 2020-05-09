package xyz.byxor.hotkeys.lock.util

interface Subscriber<EventType> {
    fun notify(event: EventType)
}

class Publisher<EventType> {

    private val subscribers = HashSet<Subscriber<EventType>>()

    fun subscribe(subscriber: Subscriber<EventType>) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: Subscriber<EventType>) {
        subscribers.remove(subscriber)
    }

    fun publish(event: EventType) {
        subscribers.forEach { it.notify(event) }
    }
}