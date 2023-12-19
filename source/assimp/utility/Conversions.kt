package assimp.utility

import assetdata.animations.KeyFrame
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.assimp.*

fun AIMatrix4x4.toMatrix4f(): Matrix4f {
    /* *** Transposed ***
    return Matrix4f(
        a1(), a2(), a3(), a4(),
        b1(), b2(), b3(), b4(),
        c1(), c2(), c3(), c4(),
        d1(), d2(), d3(), d4()
    )*/
    
    return Matrix4f(
        a1(), b1(), c1(), d1(), 
        a2(), b2(), c2(), d2(),
        a3(), b3(), c3(), d3(), 
        a4(), b4(), c4(), d4()
    )
}

fun AIVector3D.toVector3f(): Vector3f = Vector3f(x(), y(), z())

fun AIQuaternion.toQuaternionf(): Quaternionf = Quaternionf(x(), y(), z(), w())

fun AIVectorKey.toKeyFrame(): KeyFrame<Vector3f> = KeyFrame(this.mValue().toVector3f(), this.mTime().toFloat())

fun AIQuatKey.toKeyFrame(): KeyFrame<Quaternionf> = KeyFrame(this.mValue().toQuaternionf(), this.mTime().toFloat())