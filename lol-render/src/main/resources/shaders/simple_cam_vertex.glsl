#version 150

in vec3 vertexCoord;
in vec2 vertexUV;

out vec2 fragmentUV;

uniform mat4 projection_matrix;
uniform mat4 model_view_matrix;

void main() {
    gl_Position = projection_matrix*model_view_matrix*vec4(vertexCoord, 1);
    fragmentUV = vertexUV;
}