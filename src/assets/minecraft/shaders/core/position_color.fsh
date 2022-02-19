#version 330

in vec4 vertexColor;

uniform vec4 ColorModulator;
uniform float AlphaDiscard;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = color * ColorModulator;
}
