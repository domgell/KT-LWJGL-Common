package opengl.samples.animation

import assimp.assetdata.animations.KeyFrame
import assimp.assetdata.animations.MeshAnimation
import org.joml.Quaternionfc
import org.joml.Vector3fc

data class Animation(val name: String, val duration: Float, val boneKeyFrames: Map<String, BoneKeyFrames>) {
    companion object {
        fun fromAssimp(assimpAnimation: MeshAnimation): Animation {
            return Animation(assimpAnimation.name, assimpAnimation.duration, assimpAnimation.boneKeyFrames.associate {
                it.boneName to BoneKeyFrames(it.positionKeys, it.rotationKeys, it.scaleKeys)
            })
        }
    }
}

data class BoneKeyFrames(
    val posKeys: Array<KeyFrame<Vector3fc>>,
    val rotKeys: Array<KeyFrame<Quaternionfc>>,
    val scaleKeys: Array<KeyFrame<Vector3fc>>,
)

