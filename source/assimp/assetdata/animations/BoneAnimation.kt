package assimp.assetdata.animations

import assimp.assetdata.animations.KeyFrame
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc

data class BoneAnimation(
    val boneName: String,
    val positionKeys: Array<KeyFrame<Vector3fc>>,
    val rotationKeys: Array<KeyFrame<Quaternionfc>>,
    val scaleKeys: Array<KeyFrame<Vector3fc>>,
)