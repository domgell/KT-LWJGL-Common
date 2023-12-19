package rendering.camera

import application.App
import input.MouseButtons
import input.bind
import input.getMovementInput

class CameraController : Camera() {

    private val mouseSensitivity = 0.65f
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
            yaw += mouseDelta.x * -0.001f * mouseSensitivity
            pitch += mouseDelta.y * -0.001f * mouseSensitivity

            rotation.rotationXYZ(pitch, yaw, 0f)
        }

        App.onUpdate {
            // Add movement input rotated by yaw
            //camera.location.add(getMovementInput().mul(movementSpeed).rotateY(-yaw))

            location.add(getMovementInput().mul(movementSpeed * it).rotateX(-pitch).rotateY(-yaw))
        }
    }
}