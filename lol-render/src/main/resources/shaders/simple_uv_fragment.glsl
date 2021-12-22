#version 150

in vec2 fragmentUV;

out vec4 finalFragmentColor;

uniform sampler2D texture_sampler;

void main() {
    finalFragmentColor = texture(texture_sampler, fragmentUV);
}