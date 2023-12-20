package opengl.samples.shadows

import opengl.createTexture
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL45
import org.lwjgl.opengl.GL45.*

class ShadowMap {
    val fbo = glGenFramebuffers()
    val depthMap = glGenTextures()
    
    val height = 2048
    val width = 2048
    
    init {
        // Create depth map texture
        glBindTexture(GL_TEXTURE_2D, depthMap)
        createTexture(GL_TEXTURE_2D, 0, width, height, GL_DEPTH_COMPONENT, GL_FLOAT)

        // Set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER)
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, floatArrayOf(1f, 1f, 1f, 1f))

        // Bind depth map texture to frame buffer
        glBindFramebuffer(GL_FRAMEBUFFER, fbo)
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap, 0)
        glDrawBuffer(GL_NONE)
        glReadBuffer(GL_NONE)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }
}