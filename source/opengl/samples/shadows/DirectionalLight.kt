package opengl.samples.shadows

import org.joml.Matrix4f
import org.joml.Vector3f

class DirectionalLight {
    var near = 1f
    var far = 12f
    var extent = 7f
    
    val position = Vector3f()
    
    fun getLightSpace(): Matrix4f {
        val lightProjection = Matrix4f().ortho(-extent, extent, -extent, extent, near, far)
        val lightView = Matrix4f().lookAt(position, Vector3f(0f), Vector3f(0f, 1f, 0f))
        val lightSpace = Matrix4f()
        lightProjection.mul(lightView, lightSpace)
        return lightSpace
    }
}