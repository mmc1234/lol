#version 150

in vec3 vertexCoord;

void main() {
    gl_Position = vec4(vertexCoord, 1);
}