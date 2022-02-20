#version 330

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float AlphaDiscard;

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * ColorModulator;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = color;
}
