#version 330 core

out vec4 FragColor;

in vec3 Normal;
in vec3 FragPos;
in vec2 TexCoord;

uniform vec3 viewPos;
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightCutOff;
uniform vec3 objectColor;

//texture sampler
uniform sampler2D texture1;
uniform sampler2D texture2;

void main(){

    vec3 dirLuz = normalize(vec3(0.0,0.0,-lightPos.z));
    vec3 dirFoco = normalize(vec3(FragPos-lightPos));

    //*******************LUZ AMBIENTE**************
    float ambient = 0.2;
    vec3 ambient3 = ambient * lightColor;

   
    vec4 t = mix(texture(texture1, TexCoord), texture(texture2,TexCoord), 0.5);
    if(acos(dot(dirFoco,dirLuz)) <= radians(lightCutOff)){

        //***************LUZ DIFUSA******************
        vec3 norm = normalize(Normal);
        vec3 lightDir = normalize(lightPos - FragPos);
        float diff = max(dot(norm, lightDir), 0.0);
        vec3 diffuse3 = diff * lightColor;

        //****************LUZ ESPECULAR**************
        float specularStrength = 1.0;
        vec3 viewDir = normalize(viewPos - FragPos);
        vec3 reflectDir = reflect(-lightDir, norm);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0), 1.0);

        vec3 specular3 = specularStrength * spec * lightColor;
        vec3 result3 = (ambient3 + diffuse3 + specular3) * objectColor;
       
        FragColor = vec4(result3,1.0);
        FragColor = mix(FragColor,t,0.9);
    } else {

        vec3 result3 = (ambient3) * objectColor;
        FragColor = vec4(result3,1.0);
        FragColor = mix(FragColor, t, 0.4);

        }
}