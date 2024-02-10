package assimp.assetdata.animations

data class MeshAnimation(val name: String, val duration: Float, val boneKeyFrames: List<BoneAnimation>)