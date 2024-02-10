package math

import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.random.Random

object FMath {
    val Float.radians: Float
        get() = (this * PI /180).toFloat()

    val Float.degrees: Float
        get() = (this * 180/ PI).toFloat()

    val Float.pow: Float
        get() = this * this
    
    fun Float.clamp(min: Float, max: Float): Float {
        return when {
            this < min -> min
            this > max -> max
            else -> this
        }
    }
    
    fun Float.isNearlyEqual(other: Float, tolerance: Float = 0.0001f): Boolean {
        return (this - other).absoluteValue < tolerance
    }
    
    fun Float.isNearlyZero(tolerance: Float = 0.0001f) = this.isNearlyEqual(0f, tolerance)

    fun lerp(value: Float, target: Float, alpha: Float): Float {
        return value + (target - value) * alpha
    }
    
    fun random(min: Float, max: Float): Float {
        return Random.nextFloat() * (max - min) + min
    }
    
    fun perlinNoise(min: Float, max: Float, deltaTime: Float) {
        TODO()
    }
}