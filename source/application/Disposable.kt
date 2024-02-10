package application

interface Disposable {
    fun dispose()
}

fun <T> T.asDisposable(onDispose: (T) -> Unit): Disposable {
    return object : Disposable {
        override fun dispose() = onDispose(this@asDisposable)
    }
}

fun disposeAll(vararg resources: Disposable) = resources.forEach { it.dispose() }