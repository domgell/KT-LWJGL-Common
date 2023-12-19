package input

import application.App
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW

private val v = Vector3f()
fun getMovementInput(): Vector3f {
    v.set(0f, 0f, 0f)

    if (Keys.W.isDown)
        v.z++
    if (Keys.A.isDown)
        v.x++
    if (Keys.S.isDown)
        v.z--
    if (Keys.D.isDown)
        v.x--

    if (v.length() != 0f)
        v.normalize()

    return v
}

// Based on GLFW_KEY...
enum class Keys(val value: Int) {
    A(65),
    B(66),
    C(67),
    D(68),
    E(69),
    F(70),
    G(71),
    H(72),
    I(73),
    J(74),
    K(75),
    L(76),
    M(77),
    N(78),
    O(79),
    P(80),
    Q(81),
    R(82),
    S(83),
    T(84),
    U(85),
    V(86),
    W(87),
    X(88),
    Y(89),
    Z(90),
    SPACE(32),
    ZERO(48),
    ONE(49),
    TWO(50),
    THREE(51),
    FOUR(52),
    FIVE(53),
    SIX(54),
    SEVEN(55),
    EIGHT(56),
    NINE(57),
    ESC(256),
    F1(290),
    F2(291),
    F3(292),
    F4(293),
    F5(294),
    F6(295),
    F7(296),
    F8(297),
    F9(298),
    F10(299),
    F11(300),
    F12(301),
    F13(302),
    F14(303),
    F15(304),
    F16(305),
    F17(306),
    F18(307),
    F19(308),
    F20(309),
    F21(310),
    F22(311),
    F23(312),
    F24(313),
    F25(314),
    LEFT_SHIFT(340),
    LEFT_CONTROL(341),
    LEFT_ALT(342),
    LEFT_SUPER(343),
    RIGHT_SHIFT(344),
    RIGHT_CONTROL(345),
    RIGHT_ALT(346),
    RIGHT_SUPER(347);
}

val Keys.isDown: Boolean
    get() = GLFW.glfwGetKey(App.window.glfwHandle, value) == GLFW.GLFW_PRESS

