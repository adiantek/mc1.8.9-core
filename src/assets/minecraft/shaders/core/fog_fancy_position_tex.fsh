#version 330

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float AlphaDiscard;
uniform vec4 fogColor;

in vec2 texCoord0;
in float vertexFog;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * ColorModulator;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = mix(fogColor, color, vertexFog);
}
