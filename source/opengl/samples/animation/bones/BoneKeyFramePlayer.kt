package opengl.samples.animation.bones

import assimp.assetdata.animations.KeyFrame
import assimp.assetdata.animations.blend
import debug.logThis
import math.QMath
import math.VMath
import opengl.samples.animation.BoneKeyFrames
import org.joml.Matrix4f
import org.joml.Matrix4fc
import kotlin.math.min

class BoneKeyFramePlayer(boneKeyFrames: BoneKeyFrames) {
    class KeyFrameTraverser<T>(val keys: Array<KeyFrame<T>>) {
        private var current = 0

        fun updateTotalTime(totalTime: Float) {
            if (totalTime > keys[current].time)
                current = min(current + 1, keys.lastIndex)
        }
        
        fun setNewTime(totalTime: Float) {
            if (totalTime >= keys.last().time) {
                current = keys.lastIndex
                return
            }
            if (totalTime <= keys.first().time) {
                current = 0
                return
            }
            
            // TEMP: Use binary search instead
            keys.forEachIndexed { i, key -> 
                if (key.time >= totalTime) {
                    current = i
                    return
                }
            }
            
            throw IllegalStateException()
        }
        
        fun getCurrentPair(): Pair<KeyFrame<T>, KeyFrame<T>> {
            return keys[current] to keys[min(current + 1, keys.lastIndex)]
        }
    }
    
    val position = KeyFrameTraverser(boneKeyFrames.posKeys)
    val rotation = KeyFrameTraverser(boneKeyFrames.rotKeys)
    val scale = KeyFrameTraverser(boneKeyFrames.scaleKeys)
    
    private var currentTime = 0f
    
    fun updateTotalTime(totalTime: Float) {
        position.updateTotalTime(totalTime)
        rotation.updateTotalTime(totalTime)
        scale.updateTotalTime(totalTime)
        currentTime = totalTime
    }
    
    fun setNewTime(totalTime: Float) {
        position.setNewTime(totalTime)
        rotation.setNewTime(totalTime)
        scale.setNewTime(totalTime)
        currentTime = totalTime
    }
    
    private val m = Matrix4f()
    fun getCurrentTransform(): Matrix4fc {
        val pos = position.getCurrentPair().blend(currentTime, VMath::getCubicBezier)
        val rot = rotation.getCurrentPair().blend(currentTime, QMath::getLerped)
        val scale = scale.getCurrentPair().blend(currentTime, VMath::getLerped)
        
        // NOTE: This order must be used
        return m.identity().translate(pos).rotate(rot).scale(scale)
    }
}