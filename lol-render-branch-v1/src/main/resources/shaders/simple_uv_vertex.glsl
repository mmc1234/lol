#version 150

in vec3 vertexCoord;
in vec2 vertexUV;

out vec2 fragmentUV;

void main() {
    gl_Position = vec4(vertexCoord, 1);
    fragmentUV = vertexUV;
}