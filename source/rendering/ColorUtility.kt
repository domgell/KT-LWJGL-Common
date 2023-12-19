package rendering

import org.joml.Vector3f
import java.awt.Color

fun Color.toNormalizedColor(): Vector3f {
    return Vector3f(red / 255f, green / 255f, blue / 255f)
}