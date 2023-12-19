#version 460
layout (location = 0) in vec3 position;

uniform mat4 world;
uniform mat4 viewProjection;

uniform vec3 color;
out vec3 outColor;

void main() {
    gl_Position = viewProjection * world * vec4(position, 1.0);
    outColor = color;
}