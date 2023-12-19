package math

fun lerp(value: Float, target: Float, alpha: Float): Float {
    return value + (target - value) * alpha
}