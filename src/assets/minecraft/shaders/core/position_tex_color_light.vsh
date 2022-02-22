#version 330

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 UV0;
layout(location = 2) in vec4 Color;
layout(location = 3) in vec2 UV1;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 TexMat0;
uniform mat4 TexMat1;
uniform sampler2D Sampler1;

out vec2 texCoord0;
out vec4 vertexColor;
out vec4 texLight;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    texCoord0 = vec2(TexMat0 * vec4(UV0, 1.0, 1.0));
    vertexColor = Color;
    texLight = texture(Sampler1, vec2(TexMat1 * vec4(UV1, 1.0, 1.0)));
}
