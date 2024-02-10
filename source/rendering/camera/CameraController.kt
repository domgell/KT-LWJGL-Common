package rendering.camera

import application.App
import input.MouseButtons
import input.bind
import input.getMovementInput
import math.FMath.radians
import org.joml.Vector3f

class CameraController {
    val camera = Camera()
    
    private val mouseSensitivity = 0.065f
    private val movementSpeed = 7.5f

    init {
        var pitch = 0f
        var yaw = 0f

        // Apply look input when right click is held
        MouseButtons.Right.bind({ App.mouse.captureCursor = true }, { App.mouse.captureCursor = false })

        App.mouse.onMove { mouseDelta ->
            if (!App.mouse.captureCursor)
                return@onMove

            // Apply look input
            yaw += mouseDelta.x * mouseSensitivity
            pitch += mouseDelta.y * mouseSensitivity

            camera.rotation.rotationXYZ(-pitch.radians, -yaw.radians, 0f)
        }

        val movement = Vector3f()
        
        App.onUpdate { deltaTime ->
            movement.set(getMovementInput())
                .mul(movementSpeed * deltaTime)
                .rotateX(-pitch.radians) // Remove pitch rotation for non-flying controllers
                .rotateY(yaw.radians)
            
            camera.location.add(movement)
        }
    }
}