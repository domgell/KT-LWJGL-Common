package opengl.samples.cube

import application.App
import opengl.*
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL45.*
import rendering.camera.CameraController
import rendering.toNormalizedColor
import java.awt.Color

const val RESOURCES_SHADER_PATH = "/home/dom/dev/projects/git/LWJGL/KT-LWJGL-Common/source/opengl/samples/cube/shaders/"

class AppGL : App() {
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

        // Floor grid
        val grid = createGridMesh(Color.gray)

        fun drawMesh(mesh: Mesh) {
            mesh.use()

            setUniform(mesh.transform, getUniform("world", meshShader))
            setUniform(cam.viewProjection, getUniform("viewProjection", meshShader))
            setUniform(mesh.color.toNormalizedColor(), getUniform("color", meshShader))

            drawElements(mesh.indices.size, mesh.drawMode)
        }
        
        onDraw {
            glUseProgram(meshShader)
            
            drawMesh(cube)
            drawMesh(grid)
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