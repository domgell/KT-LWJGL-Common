#version 460
layout (location = 0) in vec3 position;

uniform mat4 world;
uniform mat4 viewProjection;

void main() {
    gl_Position = viewProjection * world * vec4(position, 1.0);
}