package opengl.samples.animation

import application.App
import assimp.importing.Importer
import math.VMath.toFloatArray
import math.VMath.toIntArray
import opengl.createShaderProgram
import opengl.drawElements
import opengl.getUniform
import opengl.samples.animation.bones.Bone
import opengl.samples.cube.createGridMesh
import opengl.setUniform
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL45.*
import rendering.camera.CameraController
import rendering.toNormalizedColor
import java.awt.Color

fun main() {
    AnimationApp()
}

const val RESOURCES_SHADER_PATH = "/home/dom/dev/projects/git/LWJGL/KT-LWJGL-Common/source/opengl/samples/animation/shaders/"

class AnimationApp : App() {
    override fun init() {
        // Initialize GL
        GL.createCapabilities()
        glClearColor(0.3f, 0.3f, 0.3f, 1f)
        glEnable(GL_DEPTH_TEST)

        // Camera
        val cam = CameraController().camera
        cam.location.set(0f, -1f, -5f)

        val grid = createGridMesh(Color.gray)


        // Shaders
        val animatedShader = createShaderProgram(RESOURCES_SHADER_PATH + "Animated.vert", 
            RESOURCES_SHADER_PATH + "Animated.frag")
        
        val meshShader = createShaderProgram(RESOURCES_SHADER_PATH + "Mesh.vert", 
            RESOURCES_SHADER_PATH + "Animated.frag")
        
        val scene = Importer.fromFile("/home/dom/dev/projects/git/LWJGL/KT-LWJGL-Common/source/opengl/samples/animation/resources/dance.glb")
        val meshData = scene.meshes[0]

        val meshDataHandler = MeshDataHandler(meshData.indices)
        meshDataHandler.addAttribute(0, meshData.vertexData.vPositions.toFloatArray(), 3)

        meshDataHandler.addAttribute(1, meshData.animationData!!.vBoneInfluences.toIntArray(), 4)
        meshDataHandler.addAttribute(2, meshData.animationData.vWeights.toFloatArray(), 4)
        
        val bones = Bone.createHierarchy(meshData.animationData.boneNodes, scene.globalInverse)
        val player = AnimationPlayer(bones.associateBy { it.name }, bones[0], scene.globalInverse)
        
        val animations = meshData.animationData.animations.map { Animation.fromAssimp(it) }
        player.activeAnimation = animations[0]
        
        onUpdate { deltaTime ->
            
        }
        
        onDraw {
            glUseProgram(animatedShader)
            
            meshDataHandler.use()
            
            setUniform(Matrix4f().identity(), getUniform("world", animatedShader))
            setUniform(cam.getViewProjection(), getUniform("viewProjection", animatedShader))
            setUniform(Color.cyan.toNormalizedColor(), getUniform("color", animatedShader))
            
            // Set bone uniforms
            bones.forEachIndexed { i, b -> setUniform(b.rootOffset, getUniform("boneTransforms[$i]", animatedShader)) }

            drawElements(meshData.indices.size, GL_TRIANGLES)
            

            // Draw grid
            glUseProgram(meshShader)
            grid.use()
            setUniform(grid.transform, getUniform("world", meshShader))
            setUniform(cam.getViewProjection(), getUniform("viewProjection", meshShader))
            setUniform(grid.color.toNormalizedColor(), getUniform("color", meshShader))

            drawElements(grid.indices.size, grid.drawMode)
        }
    }

    override fun update() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        onUpdate(deltaTime)
        onDraw(Unit)

        GLFW.glfwSwapBuffers(window.glfwHandle)
    }

    override fun close() = GL.destroy()
}