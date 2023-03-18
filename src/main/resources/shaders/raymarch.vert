#version 400

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec3 vertex_color;

void main() {
    gl_Position = vec4(vertex_position, 1.0);
}