#version 330

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform vec4 ColorModulator;
uniform float AlphaDiscard;
uniform vec4 fogColor;

in vec2 texCoord0;
in vec4 vertexColor;
in float vertexFog;
in vec4 texLight;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * texLight * vertexColor * ColorModulator;
    if (color.a <= AlphaDiscard) {
        discard;
    }
    fragColor = mix(fogColor, color, vertexFog);
}
