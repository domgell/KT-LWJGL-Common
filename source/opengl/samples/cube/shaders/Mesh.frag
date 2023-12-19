#version 460
in vec3 outColor;
out vec4 outColor1; // TODO: Use default frag color instead

void main() {
    outColor1 = vec4(outColor, 1.0);
}