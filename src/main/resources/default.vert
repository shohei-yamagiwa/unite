#version 460 core

in vec2 attrPosition;
in vec4 attrColor;
in vec2 attrTexCoord;

out vec4 vertexColor;
out vec2 textureCoord;

uniform mat4 uniModel;
uniform mat4 uniView;
uniform mat4 uniProjection;

void main() {
    vertexColor = attrColor;
    textureCoord = attrTexCoord;
    gl_Position = uniModel * uniView * uniProjection * vec4(attrPosition, 0.0, 1.0);
}
