#version 460
layout (location = 0) in vec3 position;

uniform mat4 world;
uniform mat4 viewProjection;

uniform vec3 color;
out vec3 outColor;

uniform mat4 lightSpace;
out vec4 outLightSpacePosition;

void main() {
    gl_Position = viewProjection * world * vec4(position, 1.0);
    
    outColor = color;
    outLightSpacePosition = lightSpace * vec4(vec3(world * vec4(position, 1.0)), 1.0);
}