package assetdata.animations

import assetdata.meshes.AnimationData
import assimp.assetdata.animations.BoneAnimation
import assimp.assetdata.animations.MeshAnimation
import org.joml.Vector4f
import org.joml.Vector4i
import org.lwjgl.assimp.*
import assimp.utility.toKeyFrame

internal val AINode.name: String
    get() = this.mName().dataString()

internal val AIBone.name: String
    get() = this.mName().dataString()

internal val AINodeAnim.name: String
    get() = this.mNodeName().dataString()

internal fun AINode.foreachChild(action: (AINode) -> Unit) {
    action(this)
    for (i in 0..<this.mNumChildren())
        AINode.create(this.mChildren()!![i]).foreachChild(action)
}

private fun AIMesh.getBones(): List<AIBone> = List(this.mNumBones()) { AIBone.create(this.mBones()!![it]) }

private fun AIScene.getNodeAnimationChannels(animationIndex: Int): Array<AINodeAnim> {
    val animation = AIAnimation.create(this.mAnimations()!![animationIndex])
    return Array(animation.mNumChannels()) { AINodeAnim.create(animation.mChannels()!![it]) }
}

private fun AINodeAnim.getPositionKeys(): Array<AIVectorKey> = Array(mNumPositionKeys()) { this.mPositionKeys()!![it] }
private fun AINodeAnim.getRotationKeys(): Array<AIQuatKey> = Array(mNumRotationKeys()) { this.mRotationKeys()!![it] }
private fun AINodeAnim.getScaleKeys(): Array<AIVectorKey> = Array(mNumScalingKeys()) { this.mScalingKeys()!![it] }

// Extract all animation data for each bone
private fun createBoneAnimations(bones: List<BoneNode>, channels: Array<AINodeAnim>): List<BoneAnimation> {
    val boneAnimations = ArrayList<BoneAnimation>()

    channels.forEach { channel ->
        val bone = bones.find { it.name == channel.name }
        if (bone != null)
            boneAnimations.add(
                BoneAnimation(
                    bone.name, channel.getPositionKeys().map { it.toKeyFrame() }.toTypedArray(),
                    channel.getRotationKeys().map { it.toKeyFrame() }.toTypedArray(),
                    channel.getScaleKeys().map { it.toKeyFrame() }.toTypedArray()
                )
            )
    }

    return boneAnimations
}

private fun createAnimation(aiScene: AIScene, bones: List<BoneNode>, index: Int): MeshAnimation {
    val anim = AIAnimation.create(aiScene.mAnimations()!![index])
    val channels = aiScene.getNodeAnimationChannels(index)
    return MeshAnimation(anim.mName().dataString(), anim.mDuration().toFloat(), createBoneAnimations(bones, channels))
}

internal fun AIMesh.createAnimData(aiScene: AIScene): AnimationData? {
    if (this.mNumBones() <= 0)
        return null

    val aiBones = this.getBones()

    val bones = BoneNode.createBoneHierarchy(aiScene.mRootNode()!!, aiBones)
    val animations = List(aiScene.mNumAnimations()) { createAnimation(aiScene, bones, it) }

    val (weights, boneInfluences) = getBoneWeightData(aiBones, this.mNumVertices())

    return AnimationData(
        bones.toTypedArray(),
        animations.toTypedArray(),
        weights.toTypedArray(),
        boneInfluences.toTypedArray()
    )
}

private fun getBoneWeightData(aiBones: List<AIBone>, numVertices: Int): Pair<List<Vector4f>, List<Vector4i>> {
    val numBoneInfluences = Array(numVertices) { 0 }

    val vertexWeights = List(numVertices) { Vector4f(0f) }
    val boneInfluences = List(numVertices) { Vector4i(-1) }

    aiBones.forEachIndexed { boneIndex, bone ->
        bone.mWeights().forEach { weightData ->
            val weight = weightData.mWeight()
            val vertexID = weightData.mVertexId()

            numBoneInfluences[vertexID]++

            when (numBoneInfluences[vertexID]) {
                1 -> {
                    vertexWeights[vertexID].x = weight
                    boneInfluences[vertexID].x = boneIndex
                }

                2 -> {
                    vertexWeights[vertexID].y = weight
                    boneInfluences[vertexID].y = boneIndex
                }

                3 -> {
                    vertexWeights[vertexID].z = weight
                    boneInfluences[vertexID].z = boneIndex
                }

                4 -> {
                    vertexWeights[vertexID].w = weight
                    boneInfluences[vertexID].w = boneIndex
                }
            }
        }
    }

    return Pair(vertexWeights, boneInfluences)
}