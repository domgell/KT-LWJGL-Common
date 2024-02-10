package math

import org.joml.*

object VMath {
    val zero: Vector3fc = Vector3f()
    val one: Vector3fc = Vector3f(1f)
    
    val none: Vector3f
        get() = Vector3f()
    
    val temp = Vector3f()
    
    fun Vector4f.copy(): Vector4fc {
        return Vector4f(this)
    }
    
    fun Vector3f.copy(): Vector3fc {
        return Vector3f(this)
    }

    fun Vector2f.copy(): Vector2fc {
        return Vector2f(this)
    }
    
    fun getLerped(start: Vector3fc, end: Vector3fc, alpha: Float): Vector3f {
        val v = Vector3f()
        start.lerp(end, alpha, v)
        return v
    }

    fun getCubicBezier(start: Vector3fc, end: Vector3fc, t: Float): Vector3f {
        val oneMinusT = 1f - t
        val oneMinusT2 = oneMinusT * oneMinusT
        val t2 = t * t

        // Approximate control points
        val controlPoint1 = Vector3f(start).lerp(end, 1f / 3f)
        val controlPoint2 = Vector3f(start).lerp(end, 2f / 3f)

        // Calculate Bezier point
        return Vector3f(start).mul(oneMinusT2 * oneMinusT) // start * (1-t)^3
            .add(controlPoint1.mul(3f * oneMinusT2 * t))   // 3 * controlPoint1 * (1-t)^2 * t
            .add(controlPoint2.mul(3f * oneMinusT * t2))   // 3 * controlPoint2 * (1-t) * t^2
            .add(Vector3f(end).mul(t2 * t))                // end * t^3
    }
    
    fun <T: Vector2fc>Array<T>.toFloatArray() = this.flatMap { listOf(it.x(), it.y()) }.toFloatArray()
    fun <T: Vector3fc>Array<T>.toFloatArray() = this.flatMap { listOf(it.x(), it.y(), it.z()) }.toFloatArray()
    fun <T: Vector4fc>Array<T>.toFloatArray() = this.flatMap { listOf(it.x(), it.y(), it.z(), it.w()) }.toFloatArray()
    
    fun <T: Vector2ic>Array<T>.toIntArray() = this.flatMap { listOf(it.x(), it.y()) }.toIntArray()
    fun <T: Vector3ic>Array<T>.toIntArray() = this.flatMap { listOf(it.x(), it.y(), it.z()) }.toIntArray()
    fun <T: Vector4ic>Array<T>.toIntArray() = this.flatMap { listOf(it.x(), it.y(), it.z(), it.w()) }.toIntArray()
}

operator fun Vector2fc.component1(): Float = this.x()
operator fun Vector2fc.component2(): Float = this.y()