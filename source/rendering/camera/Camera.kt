package rendering.camera

import application.App
import math.MMath
import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Quaternionf
import org.joml.Vector3f

open class Camera {
    val location = Vector3f()
    val rotation = Quaternionf().identity()
    var fieldOfView = 60f // TODO Vertical fieldOfView
    
    val view: Matrix4fc
        get() = MMath.createViewMatrix(location, rotation)
    
    val projection: Matrix4fc
        get() = MMath.createProjectionMatrix(fieldOfView, App.aspectRatio)
    
    private val m = Matrix4f().identity()
    val viewProjection: Matrix4fc
        get() {
            m.identity()
            projection.mul(view, m) // NOTE: Order must be projection * view for some reason
            return m
        }
}