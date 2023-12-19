package application

import input.Keys
import input.Mouse
import input.bind

abstract class App {
    init {
        window = Window(width, height)
        mouse = Mouse()

        var currentTime: Long
        var lastTime = System.nanoTime()

        Keys.ESC.bind({ window.shouldClose = true })
        
        this.init()
        
        while (!window.shouldClose) {
            window.pollEvents()
            
            // Calculate deltaTime
            currentTime = System.nanoTime()
            deltaTime = (currentTime - lastTime) / 1_000_000_000f
            lastTime = currentTime

            this.update()
        }
        
        window.close()
        this.close()
    }
    
    protected abstract fun init()
    protected abstract fun update()
    protected abstract fun close()
    
    companion object {
        const val width = 800
        const val height = 600
        
        val onUpdate = MyEvent<Float>()
        val onDraw = MyEvent<Unit>()

        lateinit var window: Window
            private set
        
        lateinit var mouse: Mouse
            private set

        var deltaTime: Float = 0f
            private set
        
        val aspectRatio = width.toFloat() / height.toFloat()
    }
}