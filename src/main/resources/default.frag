#version 460 core

in vec4 vertexColor;
in vec2 textureCoord;

out vec4 fragColor;

uniform sampler2D uniTexImage;

void main() {
    vec4 textureColor = texture(uniTexImage, textureCoord);
    fragColor = vertexColor * textureColor;
}
