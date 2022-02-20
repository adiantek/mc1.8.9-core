#version 330

uniform vec4 ColorModulator;
uniform float AlphaDiscard;

out vec4 fragColor;

void main() {
    vec4 color = ColorModulator;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = ColorModulator;
}
