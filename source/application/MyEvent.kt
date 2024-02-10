package application

class MyEvent<T> : Disposable {
    private val callbacks = ArrayList<(T) -> Unit>()
    
    // Bind new callback
    operator fun invoke(callback: (T) -> Unit): Disposable {
        callbacks.add(callback)
        
        return object : Disposable {
            val index = callbacks.lastIndex
            
            override fun dispose() {
                callbacks.removeAt(index)
            }
        }
    }

    // Call all callbacks with the given variable
    operator fun invoke(variable: T) {
        callbacks.forEach { it(variable) }
    }

    override fun dispose() = callbacks.clear()
}

// Simplified invoke call for Unit events
operator fun MyEvent<Unit>.invoke() {
    this.invoke(Unit)
}


