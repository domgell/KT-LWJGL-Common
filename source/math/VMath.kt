package math

import org.joml.*

object VMath {
    // *** To Float Array ***
    
    fun <T : Vector2fc>Array<T>.toFloatArray(): FloatArray {
        val result = FloatArray(size * 2)

        forEachIndexed { index, vector ->
            val startIndex = index * 2
            result[startIndex] = vector.x()
            result[startIndex + 1] = vector.y()
        }

        return result
    }
    
    fun <T : Vector3fc>Array<T>.toFloatArray(): FloatArray {
        val result = FloatArray(size * 3)

        forEachIndexed { index, vector ->
            val startIndex = index * 3
            result[startIndex] = vector.x()
            result[startIndex + 1] = vector.y()
            result[startIndex + 2] = vector.z()
        }

        return result
    }

    fun <T : Vector4fc>Array<T>.toFloatArray(): FloatArray {
        val result = FloatArray(size * 4)

        forEachIndexed { index, vector ->
            val startIndex = index * 4
            result[startIndex] = vector.x()
            result[startIndex + 1] = vector.y()
            result[startIndex + 2] = vector.z()
            result[startIndex + 3] = vector.z()
        }

        return result
    }

    fun <T : Vector4ic>Array<T>.toIntArray(): IntArray {
        val result = IntArray(size * 4)

        forEachIndexed { index, vector ->
            val startIndex = index * 4
            result[startIndex] = vector.x()
            result[startIndex + 1] = vector.y()
            result[startIndex + 2] = vector.z()
            result[startIndex + 3] = vector.z()
        }

        return result
    }

    fun <T : Vector3ic>Array<T>.toIntArray(): IntArray {
        val result = IntArray(size * 3)

        forEachIndexed { index, vector ->
            val startIndex = index * 3
            result[startIndex] = vector.x()
            result[startIndex + 1] = vector.y()
            result[startIndex + 2] = vector.z()
        }

        return result
    }

    fun Array<Vector2ic>.toIntArray(): IntArray {
        val result = IntArray(size * 2)

        forEachIndexed { index, vector ->
            val startIndex = index * 3
            result[startIndex] = vector.x()
            result[startIndex + 1] = vector.y()
        }

        return result
    }

    operator fun Vector3fc.times(vector: Vector3fc): Vector3f {
        val v = Vector3f()
        v.set(this)
        v.mul(vector)
        return v
    }

    operator fun Vector3fc.times(scalar: Float): Vector3f {
        val v = Vector3f()
        v.set(this)
        v.mul(scalar)
        return v
    }

    operator fun Vector3fc.plus(vector: Vector3fc): Vector3f {
        val v = Vector3f()
        v.set(this)
        v.add(vector)
        return v
    }

    operator fun Vector3fc.unaryMinus(): Vector3f {
        val v = Vector3f()
        v.set(this)
        v.mul(-1f)
        return v
    }

    operator fun Vector3fc.minus(vector: Vector3fc): Vector3f {
        val v = Vector3f()
        v.set(this)
        v.sub(vector)
        return v
    }
    
}