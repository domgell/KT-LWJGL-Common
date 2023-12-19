package math

import math.FMath.degrees
import math.FMath.radians
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc


object QMath {
    private val v = Vector3f()
    
    fun Quaternionf.setEuler(euler: Vector3fc) {
        this.identity()
        this.rotateXYZ(euler.y().radians, euler.x().radians, euler.z().radians)
    }
    
    fun Quaternionf.setEuler(yaw: Float, pitch: Float, roll: Float) {
        this.identity()
        this.rotateXYZ(pitch.radians, yaw.radians, roll.radians)
    }

    fun Quaternionf.getEuler(): Vector3f {
        this.getEulerAnglesXYZ(v)
        v.x = v.x.degrees; v.y = v.y.degrees; v.z = v.z.degrees
        return Vector3f(v)
    }
    
    fun Quaternionfc.getPitch(): Float {
        this.getEulerAnglesXYZ(v)
        return v.y.degrees
    }
    
    fun Quaternionfc.getYaw(): Float {
        this.getEulerAnglesXYZ(v)
        return v.x.degrees
    }

    fun Quaternionfc.getRoll(): Float {
        this.getEulerAnglesXYZ(v)
        return v.z.degrees
    }
    
    fun lookAt(v1: Vector3fc, v2: Vector3fc): Quaternionf {
        throw NotImplementedError()
    }
}