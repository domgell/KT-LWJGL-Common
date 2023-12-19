package assetdata.animations

import org.joml.Matrix4f
import org.lwjgl.assimp.AIBone
import org.lwjgl.assimp.AINode
import assimp.utility.toMatrix4f

data class BoneNode(
    val name: String, val index: Int, var parent: BoneNode?, val children: ArrayList<BoneNode>,
    val boneTransform: Matrix4f = Matrix4f().identity(),
    val nodeTransform: Matrix4f = Matrix4f().identity()
) {
    companion object {
        // TODO Refactor
        fun createBoneHierarchy(rootNode: AINode, bones: List<AIBone>): List<BoneNode> {
            // Map bones to aiBone.names
            val boneMap = mutableMapOf<String, BoneNode>()
            for ((i, aiBone) in bones.withIndex())
                boneMap[aiBone.name] = BoneNode(aiBone.name, i, null, ArrayList()).apply {
                    boneTransform.set(aiBone.mOffsetMatrix().toMatrix4f())
                }

            val nodes = ArrayList<AINode>()
            rootNode.foreachChild { nodes.add(it) }

            // Set parent, children from corresponding aiNodes, and set nodeTransform from node
            nodes.forEach { node ->
                if (boneMap.containsKey(node.name)) {
                    boneMap[node.name]!!.nodeTransform.set(node.mTransformation().toMatrix4f())

                    if (node.mParent() != null && boneMap.containsKey(node.mParent()!!.name)) {
                        // Add self to children of parent
                        boneMap[node.mParent()!!.name]!!.children.add(boneMap[node.name]!!)
                        boneMap[node.name]!!.parent = boneMap[node.mParent()!!.name]
                    }
                }
            }

            return boneMap.values.toList()
        }
    }
}





