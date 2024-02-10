package opengl.samples.animation

import application.App
import math.MMath
import opengl.samples.animation.bones.Bone
import opengl.samples.animation.bones.BoneKeyFramePlayer
import org.joml.Matrix4fc

// TEMP
fun <T> Map<String, T>.findFuzzy(key: String): T? {
    return this[this.keys.find { it.uppercase().contains(key.uppercase()) }]
}

class AnimationPlayer(
    private val nameToBone: Map<String, Bone>,
    private val rootBone: Bone,
    private val globalInverse: Matrix4fc,
) {
    private var boneKeyFramePlayers = mapOf<String, BoneKeyFramePlayer>()

    var shouldLoop = true

    var time = 0f
        private set

    private var isPlaying = true

    var activeAnimation: Animation? = null
        set(value) {
            field = value
            time = 0f
            boneKeyFramePlayers = value?.boneKeyFrames?.entries?.associate {
                it.key to BoneKeyFramePlayer(it.value)
            } ?: mapOf()
        }

    init {
        App.onUpdate { update(it) }
        applyTransform()
    }

    private fun update(deltaTime: Float) {
        if (!isPlaying || activeAnimation == null)
            return

        time += deltaTime * 500f

        if (time > activeAnimation!!.duration)
            if (shouldLoop) restart() else {
                pause()
                return
            }

        boneKeyFramePlayers.forEach { it.value.updateTotalTime(time) }

        //applyTransform(bones.findFuzzy("spine")!!)
        applyTransform(rootBone)
    }

    // TODO Only update the bones that are animated, else use modelSpace value
    private fun applyTransform(bone: Bone = rootBone) {
        bone.modelSpace.set(nameToBone[bone.parent]?.modelSpace ?: MMath.idt).mul(getAnimationTransform(bone))

        // Final transform passed to vertex shader
        bone.rootOffset.set(globalInverse).mul(bone.modelSpace).mul(bone.boneTransform)

        // call ApplyTransform on children
        bone.children.forEach { name -> nameToBone[name]?.let { applyTransform(it) } }
    }

    private fun getAnimationTransform(bone: Bone): Matrix4fc {
        if (activeAnimation == null)
            return bone.nodeTransform

        return boneKeyFramePlayers[bone.name]?.getCurrentTransform() ?: bone.nodeTransform
    }

    fun play() {
        isPlaying = true
    }

    fun stop() {
        isPlaying = false
        time = 0f
    }

    fun pause() {
        isPlaying = false
    }

    fun restart() {
        time = 0f
        boneKeyFramePlayers.forEach { it.value.setNewTime(0f) }

        play()
    }

    fun playAnimation(animation: Animation, shouldLoop: Boolean) {
        activeAnimation = animation
        play()
    }
}