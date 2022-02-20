#version 330

in vec4 vertexColor;
in float vertexFog;

uniform vec4 ColorModulator;
uniform float AlphaDiscard;
uniform vec4 fogColor;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor * ColorModulator;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = mix(fogColor, color, vertexFog);
}
