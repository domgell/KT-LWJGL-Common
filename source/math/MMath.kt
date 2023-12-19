package math

import math.FMath.radians
import org.joml.*

object MMath {
    val none: Matrix4f
        get() = Matrix4f().identity()
    
    private val m = none

    // NOTE: location is inverted?
    fun createViewMatrix(location: Vector3fc, rotation: Quaternionfc): Matrix4f {
        m.identity()
            .rotate(rotation)
            .translate(location)
        return m.copy()
    }

    fun createProjectionMatrix(fov: Float, aspectRatio: Float): Matrix4f {
        m.identity()
            .setPerspective(fov.radians, aspectRatio, 0.01f, 1000f)
        return m.copy()
    }

    fun Matrix4fc.getTransposed(): Matrix4f {
        m.set(this)
        m.transpose()
        return m.copy()
    }

    fun Matrix4fc.getInverted(): Matrix4f {
        m.set(this)
        m.invert()
        return m.copy()
    }
    
    fun Matrix4fc.getLocation(): Vector3f {
        val v = Vector3f()
        this.getTranslation(v)
        return v
    }

    fun Matrix4fc.getRotation(): Quaternionf {
        val q = Quaternionf()
        this.getNormalizedRotation(q)
        return q
    }
    
    fun Matrix4f.setMultiply(vararg matrices: Matrix4fc) {
        this.identity()
        matrices.forEach { this.mul(it) }
    }

    private val v = Vector3f()
    fun Matrix4f.setScale(scale: Float) {
        this.getScale(v)
        v.x = scale / v.x
        v.y = scale / v.y
        v.z = scale / v.z
        this.scale(v)
    }

    fun Matrix4f.setScale(scale: Vector3fc) {
        this.getScale(v)
        v.x = scale.x() / v.x
        v.y = scale.y() / v.y
        v.z = scale.z() / v.z
        this.scale(v)
    }

    fun Matrix4f.clearScale() = this.setScale(1f)
    
    fun Matrix4fc.toFloatArray(): FloatArray {
        val arr = FloatArray(16)
        this.get(arr)
        return arr
    }
    
    fun Matrix4fc.copy(): Matrix4f {
        return Matrix4f(this)
    }
    
    operator fun Matrix4fc.times(matrix: Matrix4fc): Matrix4f {
        val m = Matrix4f()
        m.set(matrix)
        m.mul(this)
        return m
    }
}