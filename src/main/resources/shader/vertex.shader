#version 330

// input variables from java code
layout (location=0) in vec3 position;
layout (location=1) in vec2 texCord;

// ouput variables to fragment shader
out vec3 mvPos;
out vec2 outTexCord;
out vec4 color;

// uniform variables for position and color
uniform mat4 projModelMatrix;
uniform vec4 colors[];
uniform int useColorShade;

void main(){
    gl_Position = projModelMatrix * vec4(position, 1.0);
    mvPos = position;
    outTexCord = texCord;

    if(useColorShade==1) {

        // creating color shade
        float red = (((0.5 - position.x) * colors[0].x) + ((0.5 + position.x) * colors[1].x)) + (((0.5 - position.y) * colors[2].x) + ((0.5 +position.y) * colors[3].x));
        float green = (((0.5 - position.x) * colors[0].y) + ((0.5 + position.x) * colors[1].y)) + (((0.5 - position.y) * colors[2].y) + ((0.5 +position.y) * colors[3].y));
        float blue = (((0.5 - position.x) * colors[0].z) + ((0.5 + position.x) * colors[1].z)) + (((0.5 - position.y) * colors[2].z) + ((0.5 +position.y) * colors[3].z));
        float alpha = (((0.5 - position.x) * colors[0].w) + ((0.5 + position.x) * colors[1].w)) + (((0.5 - position.y) * colors[2].w) + ((0.5 +position.y) * colors[3].w));

        color = vec4(red,green,blue,alpha);
    } else {

        // using one color mode
        color = colors[0];
    }
}