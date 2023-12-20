package opengl.samples.shadows

import application.App
import opengl.*
import opengl.samples.cube.CubeData
import opengl.samples.cube.Mesh
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL45.*
import rendering.camera.CameraController
import rendering.toNormalizedColor
import java.awt.Color

const val RESOURCES_SHADER_PATH = "/home/dom/dev/projects/git/LWJGL/KT-LWJGL-Common/source/opengl/samples/shadows/shaders/"

class ShadowsApp : App() {
    override fun init() {
        // Initialize GL
        GL.createCapabilities()
        glClearColor(0.3f, 0.3f, 0.3f, 1f)
        glEnable(GL_DEPTH_TEST)

        // Camera
        val cam = CameraController()
        cam.location.set(0f, -1f, -5f)

        // Shaders
        val meshShader = glCreateProgram()
        createVertexShader(RESOURCES_SHADER_PATH + "Mesh.vert", meshShader)
        createPixelShader(RESOURCES_SHADER_PATH + "Mesh.frag", meshShader)
        
        // Cube mesh
        val cube = Mesh(CubeData.indices, CubeData.positions).also { it.color = Color.green }

        // Floor mesh
        val floor = Mesh(CubeData.indices, CubeData.positions).also { it.transform.translate(0f, -1f, 0f).scale(5f, 0.05f, 5f) }
        
        // Light
        val light = DirectionalLight().also { it.position.set(3f) }
        val lightMesh = Mesh(CubeData.indices, CubeData.positions).also { it.color = Color.white; it.transform.translate(light.position).scale(0.1f) }
        
        // Shadow map
        val shadowMap = ShadowMap()
        
        // Depth shader
        val depthShader = createShaderProgram(RESOURCES_SHADER_PATH + "Depth.vert", RESOURCES_SHADER_PATH + "Depth.frag")
        
        // Set shadow map uniform on meshShader
        setUniform(0, getUniform("shadowMap", meshShader))

        fun drawMesh(mesh: Mesh) {
            mesh.use()

            setUniform(mesh.transform, getUniform("world", meshShader))
            setUniform(mesh.color.toNormalizedColor(), getUniform("color", meshShader))
            drawElements(mesh.indices.size, mesh.drawMode)
        }

        fun shadowPass(lightSpace: Matrix4f, depthShader: Int, shadowMap: ShadowMap, vararg mesh: Mesh) {
            setUniform(lightSpace, getUniform("lightSpace", depthShader))

            // Render to shadow map
            glCullFace(GL_FRONT)
            glViewport(0, 0, shadowMap.width, shadowMap.height)
            glBindFramebuffer(GL_FRAMEBUFFER, shadowMap.fbo)
            glClear(GL_DEPTH_BUFFER_BIT)
            glActiveTexture(GL_TEXTURE0)

            mesh.forEach {
                it.use()
                setUniform(it.transform, getUniform("world", depthShader))
                drawElements(it.indices.size, it.drawMode)
            }

            // Stop rendering to shadow map
            glBindFramebuffer(GL_FRAMEBUFFER, 0)
            glCullFace(GL_BACK)
        }
        
        onDraw {
            glUseProgram(depthShader)
            shadowPass(light.getLightSpace(), depthShader, shadowMap, cube, floor)
            
            // Render normally
            glViewport(0, 0, width, height)
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glUseProgram(meshShader)
            glBindTexture(GL_TEXTURE_2D, shadowMap.depthMap)
            setUniform(light.getLightSpace(), getUniform("lightSpace", meshShader))

            setUniform(cam.viewProjection, getUniform("viewProjection", meshShader))
            drawMesh(cube)
            drawMesh(floor)
            drawMesh(lightMesh)
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