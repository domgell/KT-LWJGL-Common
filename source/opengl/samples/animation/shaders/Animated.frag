#version 460

out vec4 outColor; // TODO: Use default frag color instead

uniform vec3 color;

void main() {
    outColor = vec4(color, 1.0);
}