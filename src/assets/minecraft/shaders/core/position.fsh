#version 330

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    fragColor = ColorModulator;
}
