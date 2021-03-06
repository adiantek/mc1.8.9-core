#version 330

in float vertexFog;

uniform vec4 ColorModulator;
uniform float AlphaDiscard;
uniform vec4 fogColor;

out vec4 fragColor;

void main() {
    vec4 color = ColorModulator;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = mix(fogColor, color, vertexFog);
}
