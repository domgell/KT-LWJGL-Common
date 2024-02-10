package opengl.samples.animation.bones

import assetdata.animations.BoneNode
import math.MMath
import org.joml.Matrix4f
import org.joml.Matrix4fc

data class Bone(
    val name: String,
    val parent: String?,
    val children: Array<String>,
    val boneTransform: Matrix4fc,
    val nodeTransform: Matrix4fc,
) {
    val rootOffset: Matrix4f = Matrix4f().identity()
    val modelSpace: Matrix4f = Matrix4f().identity()

    // Const
    val baseFinalTransform = MMath.none
    
    val parentTransform = MMath.none
    val inverseNodeTransform = MMath.none
    
    companion object {
        // TEMP
        fun createHierarchy(boneNodes: Array<BoneNode>, globalInverse: Matrix4fc): Array<Bone> {
            return boneNodes.map { (name, _, parent, children, boneTransform, nodeTransform) ->
                Bone(
                    name, parent?.name, children.map { child -> child.name }.toTypedArray(),
                    boneTransform,
                    nodeTransform
                )
            }.toTypedArray().also {  bones ->
                val boneMap = bones.associateBy { it.name }
                
                fun applyTransform(bone: Bone, parentTransform: Matrix4fc) {
                    bone.modelSpace.set(parentTransform).mul(bone.nodeTransform)
                    bone.baseFinalTransform.set(globalInverse).mul(bone.modelSpace).mul(bone.boneTransform)

                    bone.parentTransform.set(parentTransform).mul(bone.nodeTransform)
                    bone.inverseNodeTransform.set(bone.nodeTransform).invert()
                    
                    bone.children.forEach { childName ->
                        boneMap[childName]?.let { applyTransform(it, bone.modelSpace) }
                    }
                }
                
                applyTransform(bones[0], MMath.none)
            }
        }
    }
}


