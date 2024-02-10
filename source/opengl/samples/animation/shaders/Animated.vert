#version 460

layout (location = 0) in vec3 position;

layout (location = 1) in ivec4 boneIndices;
layout (location = 2) in vec4 weights;

uniform mat4 world;
uniform mat4 viewProjection;

uniform mat4 boneTransforms[100];

void main() {
    mat4 boneTransform = boneTransforms[boneIndices[0]] * weights[0];
    boneTransform += boneTransforms[boneIndices[1]] * weights[1];
    boneTransform += boneTransforms[boneIndices[2]] * weights[2];
    boneTransform += boneTransforms[boneIndices[3]] * weights[3];
    
    vec4 vertexPosition = boneTransform * vec4(position, 1.0);
    gl_Position = viewProjection * world * vertexPosition;
}