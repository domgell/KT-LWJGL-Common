package assimp.assetdata.animations

import math.MMath
import math.QMath
import math.VMath
import org.joml.*
import kotlin.math.abs

// TODO: REMOVE

data class KeyFrame<T>(val value: T, val time: Float) {
    companion object {
        fun createTransformKeyFrames(
            positionKeys: Array<KeyFrame<Vector3fc>>,
            rotationKeys: Array<KeyFrame<Quaternionfc>>,
            scaleKeys: Array<KeyFrame<Vector3fc>>, timeTolerance: Float = 0.01f
        ): Array<KeyFrame<Matrix4fc>> {
            val transformKeys = ArrayList<KeyFrame<Matrix4fc>>()
            
            rotationKeys.forEach {
                val pos = positionKeys.getValueAtTime(it.time, VMath::getLerped, false)
                val scale = scaleKeys.getValueAtTime(it.time, VMath::getLerped, false)
                transformKeys.add(KeyFrame(MMath.createTransform(pos, it.value, scale, MMath.TransformOrder.TRS), it.time)) 
                //transformKeys.add(KeyFrame(MMath.none.translate(pos).rotate(it.value).scale(scale), it.time))
            }

            positionKeys.forEach {
                val rot = rotationKeys.getValueAtTime(it.time, QMath::getLerped, false)
                val scale = scaleKeys.getValueAtTime(it.time, VMath::getLerped, false)
                transformKeys.add(KeyFrame(MMath.createTransform(it.value, rot, scale, MMath.TransformOrder.TRS), it.time))
                //transformKeys.add(KeyFrame(MMath.none.translate(it.value).rotate(rot).scale(scale), it.time))
            }

            scaleKeys.forEach {
                val pos = positionKeys.getValueAtTime(it.time, VMath::getLerped, false)
                val rot = rotationKeys.getValueAtTime(it.time, QMath::getLerped, false)
                transformKeys.add(KeyFrame(MMath.createTransform(pos, rot, it.value, MMath.TransformOrder.TRS), it.time))
                //transformKeys.add(KeyFrame(MMath.none.translate(pos).rotate(rot).scale(it.value), it.time))
            }
            
            transformKeys.sortBy { it.time }
            
            // Remove keys with the same time
            return transformKeys.filterIndexed { i, _ ->
                if (i == 0)
                    true
                else
                    abs(transformKeys[i - 1].time - transformKeys[i].time) >= timeTolerance
            }.toTypedArray()
        }
    }
}

// TODO REMOVE

fun <T> Array<KeyFrame<T>>.getValueAtTime(time: Float, lerpFunc: (T, T, Float) -> T, shouldLoop: Boolean): T {
    val start = this.getClosestKeyFrameIndex(time)
    val end = this.getNextKeyIndex(start, shouldLoop)

    return this[start].blend(time, this[end], lerpFunc)
}

fun <T> KeyFrame<T>.getBlendAlpha(totalTime: Float, other: KeyFrame<T>): Float {
    val duration = other.time - this.time
    if (duration == 0f) 
        return 0f // No need to blend if both keyframes are at the same time

    return (totalTime - this.time) / duration
}

fun <T> KeyFrame<T>.blend(time: Float, other: KeyFrame<T>, lerpFunc: (T, T, Float) -> T): T =
    lerpFunc(this.value, other.value, this.getBlendAlpha(time, other))

fun <T> Pair<KeyFrame<T>, KeyFrame<T>>.blend(time: Float, lerpFunc: (T, T, Float) -> T): T =
    lerpFunc(this.first.value, this.second.value, this.first.getBlendAlpha(time, this.second))


private fun <T> Array<KeyFrame<T>>.getClosestKeyFrameIndex(time: Float): Int {
    var low = 0
    var high = this.size - 1

    while (low < high) {
        val mid = (low + high) / 2
        val midVal = this[mid].time

        when {
            midVal < time -> low = mid + 1
            midVal > time -> high = mid
            else -> return mid  // exact match
        }
    }

    // Handling the edge case where the closest keyframe could be the last element
    if (low < this.size - 1 &&
        Math.abs(this[low + 1].time - time) < Math.abs(this[low].time - time)) {
        return low + 1
    }

    return low
}

private fun <T> Array<KeyFrame<T>>.getNextKeyIndex(current: Int, shouldLoop: Boolean): Int {
    return if (current >= this.size - 1) {
        if (shouldLoop) 0 else this.size - 1
    } else {
        current + 1
    }
}



