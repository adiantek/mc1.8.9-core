#version 330

layout(location = 0) in vec3 Position;
layout(location = 2) in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

uniform int fogMode;
uniform float fogDensity;
uniform float fogStart;
uniform float fogEnd;
uniform bool fogRadial;

out vec4 vertexColor;
out float vertexFog;

float calculateFog(float vertexDistance) {
    if (fogMode == 9729) {
        // linear
        if (vertexDistance <= fogStart) {
            return 1.0;
        } else if (vertexDistance >= fogEnd) {
            return 0.0;
        }
        return (fogEnd - vertexDistance) / (fogEnd - fogStart);
    } else if (fogMode == 2048) {
        // exp
        return clamp(1.0 / exp(vertexDistance * fogDensity), 0.0, 1.0);
    } else if (fogMode == 2049) {
        // exp2
        return clamp(1.0 / exp(vertexDistance * vertexDistance * fogDensity * fogDensity), 0.0, 1.0);
    } else {
        return 0.5;
    }
}

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color;
    
    vec4 Rh = ModelViewMat * vec4(Position, 1.0);
    vec4 Re = Rh / Rh.w;
    if (fogRadial) {
        // GL_EYE_RADIAL_NV
        vertexFog = calculateFog(length(Re.xyz)); // fancy mode
    } else {
        // GL_EYE_PLANE_ABSOLUTE_NV
        vertexFog = calculateFog(abs(Re.z)); // fast mode
    }
}
