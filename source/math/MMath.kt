package math

import math.FMath.radians
import math.MMath.copy
import math.QMath.copy
import math.VMath.copy
import org.joml.*
import javax.print.attribute.standard.MediaSize.Other

object MMath {
    enum class TransformOrder(val value: Int) {
        SRT(0),
        TRS(1)
    }

    // Constant reference to identity matrix
    val idt: Matrix4fc = MMath.none
    
    // Create a new identity matrix
    val none: Matrix4f
        get() = Matrix4f().identity()
    
    val temp: Matrix4f = none
    
    fun viewMatrix(location: Vector3fc, rotation: Quaternionfc, dest: Matrix4f): Matrix4f {
        dest.identity()
            .rotate(rotation)
            .translate(location.x(), -location.y(), location.z()) // NOTE: location.y is inverted?
        return dest
    }

    fun projectionMatrix(fov: Float, aspectRatio: Float, dest: Matrix4f): Matrix4fc {
        dest.identity()
            .setPerspective(fov.radians, aspectRatio, 0.01f, 1000f)
        return dest
    }
    
    fun createTransform(location: Vector3fc, rotation: Quaternionfc, scale: Vector3fc, order: TransformOrder = TransformOrder.SRT): Matrix4fc {
        return when (order) {
            TransformOrder.SRT -> none.scale(scale).rotate(rotation).translate(location)
            TransformOrder.TRS -> none.translate(location).rotate(rotation).scale(scale)
        }
    }
    
    fun Matrix4fc.getTransposed(): Matrix4fc {
        temp.set(this).transpose()
        return temp.copy()
    }

    fun Matrix4fc.getInverted(): Matrix4fc {
        temp.set(this).invert()
        return temp.copy()
    }
    
    fun Matrix4fc.getLocation(): Vector3fc {
        this.getTranslation(VMath.temp)
        return VMath.temp.copy()
    }

    fun Matrix4fc.getRotation(): Quaternionfc {
        this.getNormalizedRotation(QMath.temp)
        return QMath.temp.copy()
    }

    fun Matrix4fc.getScale(): Vector3fc {
        this.getScale(VMath.temp)
        return VMath.temp.copy()
    }
    
    fun Matrix4f.setMultiply(vararg matrices: Matrix4fc): Matrix4f {
        this.identity()
        matrices.forEach { this.mul(it) }
        return this
    }

    fun Matrix4fc.toFloatArray(): FloatArray {
        val arr = FloatArray(16)
        this.get(arr)
        return arr
    }
    
    fun Matrix4fc.copy(): Matrix4f {
        return Matrix4f(this)
    }
    
    // TEMP
    operator fun Matrix4fc.times(matrix: Matrix4fc): Matrix4f {
        val m = Matrix4f()
        m.set(matrix)
        m.mul(this)
        return m
    }
    
    fun lerp(current: Matrix4fc, target: Matrix4fc, alpha: Float): Matrix4fc {
        val pos = Vector3f()
        current.getLocation().lerp(target.getLocation(), alpha, pos)
        
        val rot = Quaternionf()
        current.getRotation().slerp(target.getRotation(), alpha, rot)
        
        val scale = Vector3f()
        current.getScale().lerp(target.getScale(), alpha, scale)
        
        //return createTransform(pos, rot, scale)
        return createTransform(pos, rot, scale, TransformOrder.TRS)
    }
}


