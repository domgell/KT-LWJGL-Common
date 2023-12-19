package assetdata.animations

import org.joml.Quaternionf
import org.joml.Vector3f

data class BoneAnimation(
    val boneIndex: Int,
    val positionKeys: List<KeyFrame<Vector3f>>,
    val rotationKeys: List<KeyFrame<Quaternionf>>,
    val scaleKeys: List<KeyFrame<Vector3f>>,
)