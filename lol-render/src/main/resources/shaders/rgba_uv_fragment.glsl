#version 150

in vec2 fragmentUV;

out vec4 finalFragmentColor;

uniform sampler2D texture_sampler;

uniform vec4 rgba;

void main() {
    finalFragmentColor = rgba*texture(texture_sampler, fragmentUV);
}