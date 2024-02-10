package assimp.importing

import assimp.assetdata.AssimpScene
import org.lwjgl.assimp.Assimp

private const val importFlags = Assimp.aiProcess_Triangulate or
        Assimp.aiProcess_LimitBoneWeights or Assimp.aiProcess_JoinIdenticalVertices

object Importer {
    fun fromFile(fileName: String): AssimpScene {
        val aiScene = Assimp.aiImportFile(fileName, importFlags)
            ?: throw Exception("Error while loading '$fileName': ${Assimp.aiGetErrorString()}")
        
        return AssimpScene.create(aiScene)
    }
}
