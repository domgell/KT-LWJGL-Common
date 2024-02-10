package math

import math.FMath.degrees
import math.FMath.radians
import math.MMath.getRotation
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc


object QMath {
    // Constant reference to identity Quaternion
    val idt: Quaternionfc = none

    // Create a new identity Quaternion
    val none: Quaternionf
        get() = Quaternionf().identity()

    val temp = none

    fun Quaternionfc.copy(): Quaternionf {
        return Quaternionf(x(), y(), z(), w())
    }
    
    fun fromEuler(yaw: Float, pitch: Float, roll: Float): Quaternionfc {
        temp.setEuler(yaw.radians, pitch.radians, roll.radians)
        return temp
    }

    fun fromEuler(euler: Vector3fc): Quaternionfc {
        return fromEuler(euler.x(), euler.y(), euler.z())
    }

    fun Quaternionf.setEuler(yaw: Float, pitch: Float, roll: Float) {
        this.identity()
        this.rotateXYZ(pitch.radians, yaw.radians, roll.radians)
    }

    fun Quaternionf.setEuler(euler: Vector3fc) {
        this.setEuler(euler.x(), euler.y(), euler.z())
    }

    fun Quaternionf.getEuler(): Vector3f {
        val v = Vector3f()
        this.getEulerAnglesXYZ(v)
        v.x = v.x.degrees; v.y = v.y.degrees; v.z = v.z.degrees
        return v
    }

    fun Quaternionfc.getInverted(): Quaternionfc {
        temp.set(this).invert()
        return temp
    }

    fun getLerped(start: Quaternionfc, end: Quaternionfc, alpha: Float): Quaternionf {
        val q = Quaternionf()
        start.slerp(end, alpha, q)
        return q
    }

    fun lookAt(fromPosition: Vector3f, toPosition: Vector3f): Quaternionf {
        val up = Vector3f(0f, -1f, 0f)
        
        val lookAtMat = Matrix4f().lookAt(fromPosition, toPosition, up)
        return lookAtMat.getRotation().copy()
    }
}