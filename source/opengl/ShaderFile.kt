package opengl

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL40
import java.io.File

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
        System.err.println("Couldn't link the shader program for '$shaderFileName': " + GL20.glGetProgramInfoLog(shaderProgramHandle))

    // Program error checking
    GL20.glValidateProgram(shaderProgramHandle)
    if (GL20.glGetProgrami(shaderProgramHandle, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
        System.err.println("Couldn't validate the shader program for '$shaderFileName': " + GL20.glGetProgramInfoLog(shaderProgramHandle))
    
    GL20.glDeleteShader(shaderHandle)
}

fun createVertexShader(shaderFileName: String, shaderProgramHandle: Int): Int {
    val handle = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)
    initializeShader(handle, shaderFileName, shaderProgramHandle)
    return handle
}

fun createPixelShader(shaderFileName: String, shaderProgramHandle: Int): Int {
    val handle = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)
    initializeShader(handle, shaderFileName, shaderProgramHandle)
    return handle
}

fun createGeometryShader(shaderFileName: String, shaderProgramHandle: Int): Int {
    val handle = GL40.glCreateShader(GL40.GL_GEOMETRY_SHADER)
    initializeShader(handle, shaderFileName, shaderProgramHandle)
    return handle
}