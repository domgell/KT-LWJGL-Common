package debug

import math.MMath.getLocation
import math.MMath.getRotation
import math.MMath.getScale
import org.joml.Matrix4fc
import org.joml.Vector3f

fun Any.logThis(message: String? = null) {
    if (message != null)
        println("$message: $this")
    else
        println(this)
}

fun Matrix4fc.toTransformString(): String {
    return "pos:   ${this.getLocation()}\n" + 
            "rot:   ${this.getRotation()}\n" +
            "scale: ${this.getScale()}\n"
}