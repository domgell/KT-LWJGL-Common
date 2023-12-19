package rendering

import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer

fun FloatArray.createBuffer(): FloatBuffer {
    val buffer = MemoryUtil.memAllocFloat(size)
    buffer.put(this).flip()
    return buffer
}

fun Array<Float>.createBuffer(): FloatBuffer {
    return this.toFloatArray().createBuffer()
}

fun IntArray.createBuffer(): IntBuffer {
    val buffer = MemoryUtil.memAllocInt(size)
    buffer.put(this).flip()
    return buffer
}
