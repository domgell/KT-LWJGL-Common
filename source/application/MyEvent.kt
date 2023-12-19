package application

class MyEvent<T> {
    private val callbacks = ArrayList<(T) -> Unit>()

    // Bind new callback
    operator fun invoke(callback: (T) -> Unit): Int {
        callbacks.add(callback)
        return callbacks.lastIndex
    }

    // Call all callbacks with the given variable
    operator fun invoke(variable: T) {
        callbacks.forEach { it(variable) }
    }
    
    fun remove(index: Int) {
        callbacks.removeAt(index)
    }
}

// Simplified invoke call for Unit events
operator fun MyEvent<Unit>.invoke() {
    this.invoke(Unit)
}


