package rendering.camera

import application.App
import math.MMath
import math.QMath
import math.VMath
import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Quaternionf
import org.joml.Vector3f

open class Camera {
    val location = VMath.none
    val rotation = QMath.none
    var fieldOfView = 60f // TODO Vertical fieldOfView
    
    private val projection = MMath.none
    fun getProjection(): Matrix4fc = MMath.projectionMatrix(fieldOfView, App.aspectRatio, projection)

    private val view = MMath.none
    fun getView(): Matrix4fc = MMath.viewMatrix(location, rotation, view)

    private val viewProjection = MMath.none
    fun getViewProjection(): Matrix4fc {
        getProjection().mul(getView(), viewProjection)
        return viewProjection
    }
}