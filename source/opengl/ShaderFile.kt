package opengl

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL45.*
import java.io.File

object Shader {
    fun fromSource(source: String, shaderType: Int): Int {
        val handle = glCreateShader(shaderType)
        compileShader(source, handle)
        return handle
    }

    fun fromFile(path: String, shaderType: Int): Int {
        val handle = glCreateShader(shaderType)
        val source = File(path).readText()
        try {
            compileShader(source, handle)
        } catch (e: IllegalStateException) {
            println(path)
        }
        return handle
    }

    fun createProgram(vararg shaders: Int): Int {
        val program = glCreateProgram()
        shaders.forEach { linkShader(it, program) }
        return program
    }

    private fun compileShader(source: String, handle: Int) {
        // Compile shader from source
        glShaderSource(handle, source)
        glCompileShader(handle)
        getShaderError(GL_COMPILE_STATUS, handle)?.let { throw IllegalStateException("Error compiling: $it") }
    }

    private fun linkShader(shaderHandle: Int, programHandle: Int) {
        // Attach shader to program
        glAttachShader(programHandle, shaderHandle)
        glLinkProgram(programHandle)
        getProgramError(GL_LINK_STATUS, programHandle)?.let { throw IllegalStateException("Error linking: $it") }

        // Validate program
        glValidateProgram(programHandle)
        getProgramError(GL_VALIDATE_STATUS, programHandle)?.let { throw IllegalStateException("Error validating: $it") }

        // Free shader
        glDeleteShader(shaderHandle)
    }

    private fun getShaderError(errorType: Int, handle: Int): String? {
        if (glGetShaderi(handle, errorType) == GL_FALSE)
            return glGetShaderInfoLog(handle)

        return null
    }

    private fun getProgramError(errorType: Int, handle: Int): String? {
        if (glGetProgrami(handle, errorType) == GL_FALSE)
            return GL20.glGetProgramInfoLog(handle)

        return null
    }
}

fun initializeShader(shaderHandle: Int, shaderFileName: String, shaderProgramHandle: Int) {
    // Compile shader code from file
    val shaderCode = File(shaderFileName).readText()
    GL20.glShaderSource(shaderHandle, shaderCode)
    GL20.glCompileShader(shaderHandle)

    // Compilation error checking
    if (GL20.glGetShaderi(shaderHandle, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
        System.err.println("Couldn't compile '$shaderFileName': " + GL20.glGetShaderInfoLog(shaderHandle))

    // Link shader to shader program
    GL20.glAttachShader(shaderProgramHandle, shaderHandle)

    // Linking error checking
    GL20.glLinkProgram(shaderProgramHandle)
    if (GL20.glGetProgrami(shaderProgramHandle, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
        System.err.println(
            "Couldn't link the shader program for '$shaderFileName': " + GL20.glGetProgramInfoLog(
                shaderProgramHandle
            )
        )

    // Program error checking
    GL20.glValidateProgram(shaderProgramHandle)
    if (GL20.glGetProgrami(shaderProgramHandle, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
        System.err.println(
            "Couldn't validate the shader program for '$shaderFileName': " + GL20.glGetProgramInfoLog(
                shaderProgramHandle
            )
        )

    GL20.glDeleteShader(shaderHandle)
}

fun createVertexShader_OLD(shaderFileName: String, shaderProgramHandle: Int): Int {
    val handle = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)
    initializeShader(handle, shaderFileName, shaderProgramHandle)
    return handle
}

fun createPixelShader_OLD(shaderFileName: String, shaderProgramHandle: Int): Int {
    val handle = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)
    initializeShader(handle, shaderFileName, shaderProgramHandle)
    return handle
}

fun createGeometryShader_OLD(shaderFileName: String, shaderProgramHandle: Int): Int {
    val handle = glCreateShader(GL_GEOMETRY_SHADER)
    initializeShader(handle, shaderFileName, shaderProgramHandle)
    return handle
}

fun createShaderProgram(vertexShader: String, pixelShader: String): Int {
    val program = glCreateProgram()
    createVertexShader_OLD(vertexShader, program)
    createPixelShader_OLD(pixelShader, program)
    return program
}