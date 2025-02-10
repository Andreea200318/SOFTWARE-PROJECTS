#version 410 core

in vec3 fNormal;
in vec4 fPosEye;
in vec2 fTexCoords;
in vec4 fragPosLightSpace;

out vec4 fColor;

// Pole light parameters
uniform vec3 poleLightPos;
uniform vec3 poleLightColor;
uniform float poleLightIntensity;

//directional light
uniform vec3 lightDir;
uniform vec3 lightColor;

//texture
uniform sampler2D diffuseTexture;
uniform sampler2D specularTexture;
uniform sampler2D shadowMap;
uniform bool fogEnabled;

vec3 ambient;
float ambientStrength = 0.2f;
vec3 diffuse;
vec3 specular;
float specularStrength = 0.5f;
float shininess = 32.0f;

// Calculate pole light contribution
vec3 computePoleLighting() {
    vec3 normalEye = normalize(fNormal);
    vec3 lightVec = poleLightPos - fPosEye.xyz;
    float distance = length(lightVec);
    vec3 lightDirN = normalize(lightVec);
    
    // Attenuation
    float attenuation = 1.0 / (1.0 + 0.09 * distance + 0.032 * distance * distance);
    
    // Diffuse
    float diff = max(dot(normalEye, lightDirN), 0.0);
    vec3 diffusePole = diff * poleLightColor;
    
    // Specular
    vec3 viewDir = normalize(-fPosEye.xyz);
    vec3 reflectDir = reflect(-lightDirN, normalEye);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
    vec3 specularPole = specularStrength * spec * poleLightColor;
    
    return (diffusePole + specularPole) * attenuation * poleLightIntensity;
}

void computeLightComponents() {
    // Previous lighting calculations...
    vec3 cameraPosEye = vec3(0.0f);
    vec3 normalEye = normalize(fNormal);    
    vec3 lightDirN = normalize(lightDir);
    vec3 viewDirN = normalize(cameraPosEye - fPosEye.xyz);
    
    ambient = ambientStrength * lightColor;
    diffuse = max(dot(normalEye, lightDirN), 0.0f) * lightColor;
    
    vec3 reflection = reflect(-lightDirN, normalEye);
    float specCoeff = pow(max(dot(viewDirN, reflection), 0.0f), shininess);
    specular = specularStrength * specCoeff * lightColor;
}

float computeShadow() {
    // Previous shadow calculation...
    vec3 normalizedCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    normalizedCoords = normalizedCoords * 0.5 + 0.5;
    float closestDepth = texture(shadowMap, normalizedCoords.xy).r;
    float currentDepth = normalizedCoords.z;
    float bias = max(0.05f * (1.0f - dot(fNormal, lightDir)), 0.005f);
    return currentDepth - bias > closestDepth ? 1.0f : 0.0f;
}

float computeFog() {
    float fogDensity = 0.05f;
    float fragmentDistance = length(fPosEye);
    float fogFactor = exp(-pow(fragmentDistance * fogDensity, 2));
    return clamp(fogFactor, 0.0f, 1.0f);
}

void main() {
    computeLightComponents();
    
    vec3 baseColor = texture(diffuseTexture, fTexCoords).rgb;
    ambient *= baseColor;
    diffuse *= baseColor;
    specular *= texture(specularTexture, fTexCoords).rgb;
    
    float shadow = computeShadow();
    vec3 directionalLight = min((ambient + (1.0f - shadow)*diffuse) + (1.0f - shadow)*specular, 1.0f);
    
    // Add pole light contribution
    vec3 poleLight = computePoleLighting() * baseColor;
    vec3 finalColor = directionalLight + poleLight;
    
    float fogFactor = fogEnabled ? computeFog() : 1.0f;
    vec4 fogColor = vec4(0.5f, 0.5f, 0.5f, 1.0f);
    fColor = mix(fogColor, vec4(finalColor, 1.0), fogFactor);
}