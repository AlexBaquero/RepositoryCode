#include <windows.h>
#include <glad.h>
#include <glfw3.h>
#include <stdio.h>
#include <math.h>
#include <iostream>

#include "esfera.h"
#define STB_IMAGE_IMPLEMENTATION
#include <stb_image.h>

//Para las transformaciones
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>


void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void processInput(GLFWwindow *window);

// settings
unsigned int SCR_WIDTH = 800;
unsigned int SCR_HEIGHT = 800;

extern GLuint setShaders(const char *nVertx, const char *nFrag);
GLuint shaderProgram;

// unsigned int VBO, VAO, EBO;
unsigned int VAO;
unsigned int VAOCubo;
unsigned int VAOCuadrado;
unsigned int VAOEsfera;

float angulo;
int camara=0;
int G = 35;

typedef struct {
	float px, py, pz; //posicio inicial
	float angulo_trans; //angulo giro x
	float angulo_trans_2; //angulo giro z
	float sx, sy, sz; //escalado en los dos ejes
	unsigned int listarender; //VAO
	float col1,col2,col3;
	boolean articulacion;
	boolean base;
	boolean foco;
	GLuint textura;
} objeto ;


#define ARADIANES 0.0174
#define GL_PI 3.14f
#define RADIO .5


int PiscinaT[16] = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
int PisoP=0;
int NowT=0;

int v = 0.6;

objeto base = {		0,0,0.15,0,0,.3,.2,.2,0, 0.0,0.0,1.0,0,0,0};
objeto baseA1 = {	0,0,0.10,0,0,.07,.07,.07,0, 0.0,1.0,1.0,0,0,0};
objeto base1 = {	0,0,0.10,0,0,.05,.05,.3,0,1.0,1.0,0.0,0,0,0};
objeto baseA2 = {	0,0,0.15,0,0,.05,.05,.05,0,0.0,1.0,1.0,0,0,0};
objeto base2 = {	0,0,0.11,0,0,.05,.05,.3,0,1.0,1.0,0.0,0,0,0};
objeto foco = {		0,0,0.25,0,0,.05,.05,.05,0,0.5,0.8,0.1,0,0,0};

void keyCallBack(GLFWwindow *window, int key, int scancode, int action, int mods);
void framebuffer_size_callback(GLFWwindow* window, int width, int height);


void openGlInit() {
	//Habilito aqui el depth en vez de arriba para los usuarios de linux y mac mejor arriba
	//Incializaciones varias
	glClearDepth(1.0f); //Valor z-buffer
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // valor limpieza buffer color
	glEnable(GL_DEPTH_TEST); // z-buffer
	//glEnable(GL_CULL_FACE); //ocultacion caras back
	glEnable(GL_TEXTURE_2D);
	glCullFace(GL_BACK);
}

glm::vec3 ActualizarPosicion(glm::mat4 transform) {
	return glm::vec3(transform * glm::vec4(0.0f, 0.0f, 0.0f, 1.0f));
}

void dibujaEjes() {
	unsigned int VBO, EBO;
	// set up vertex data (and buffer(s)) and configure vertex attributes
	// ------------------------------------------------------------------
	float vertices[] = {
		//Vertices          //Colores
		0.0f, 0.0f, 0.0f,	 0.0f, .0f, .0f,  // 0
		.5f, 0.0f, 0.0f,	 1.0f, .0f, .0f,  //x
		0.0f, .5f, 0.0f,	.0f, 1.0f, 0.0f, // y
		0.0f, .5f, 0.0f,	 .0f, .0f, 1.0f,  // z  
		.5f , .5f, 0.5f,	0.0f, .0f, .0f // 1,1,1 bueno realmente la mitad
	};
	unsigned int indices[] = {  // empieza desde cero
		0, 1,
		0, 2,
		0, 3,
		0, 4
	};

	glGenVertexArrays(1, &VAO);
	glGenBuffers(1, &VBO);
	glGenBuffers(1, &EBO);
	// bind the Vertex Array Object first, then bind and set vertex buffer(s), and then configure vertex attributes(s).
	glBindVertexArray(VAO);

	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);

	// position attribute
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);

	// position Color
	
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(1);

	
	// note that this is allowed, the call to glVertexAttribPointer registered VBO as the vertex attribute's bound vertex buffer object so afterwards we can safely unbind
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	//glDrawElements(GL_LINES, 8, GL_UNSIGNED_INT, 0);
	// You can unbind the VAO afterwards so other VAO calls won't accidentally modify this VAO, but this rarely happens. Modifying other
	// VAOs requires a call to glBindVertexArray anyways so we generally don't unbind VAOs (nor VBOs) when it's not directly necessary.
	glBindVertexArray(0);
	glDeleteBuffers(1, &VBO);
	glDeleteBuffers(1, &EBO);

}

void dibujaCuadrado() {
	unsigned int VBO, EBO;

	float vertices[] = {
		-0.5f, -0.5f, 0.5f,        1.0f, 1.0f, 1.0f,	1.0f,0.0f,
		 0.5f, -0.5f, 0.5f,        1.0f, 1.0f, 1.0f,	0.0f,0.0f,
		 0.5f,  0.5f, 0.5f,        1.0f, 1.0f, 1.0f,	0.0f,1.0f,
		 0.5f,  0.5f, 0.5f,        1.0f, 1.0f, 1.0f,	0.0f,1.0f,
		-0.5f,  0.5f, 0.5f,        1.0f, 1.0f, 1.0f,	1.0f,1.0f,
		 -0.5f, -0.5f, 0.5f,       1.0f, 1.0f, 1.0f,	1.0f,0.0f,

	};

	glGenVertexArrays(1, &VAOCuadrado);
	glGenBuffers(1, &VBO);
	glGenBuffers(1, &EBO);
	// bind the Vertex Array Object first.
	glBindVertexArray(VAOCuadrado);

	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);


	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);

	//Normales
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(1);

	//texturas
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(6 * sizeof(float)));
	glEnableVertexAttribArray(2);


	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
	glDeleteBuffers(1, &VBO);
	glDeleteBuffers(1, &EBO);

}

void dibujaEsfera() {
	unsigned int VBO;

	glGenVertexArrays(1, &VAOEsfera);
	glGenBuffers(1, &VBO);

	// bind the Vertex Array Object first.
	glBindVertexArray(VAOEsfera);

	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices_esfera), vertices_esfera, GL_STATIC_DRAW);

	//normales
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(1);

	//texture
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(2);

	//vertices
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(5 * sizeof(float)));
	glEnableVertexAttribArray(0);


	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
	glDeleteBuffers(1, &VBO);
	
}

int dibujaCubo() {
	unsigned int VBO, EBO;


	float vertices[] = {

			//CARA DELANTERA
			-0.5f, 0.5f,  -0.5f,	0.0f, 1.0f,0.0f,  1.0f,0.0f,
			 0.5f, 0.5f,  -0.5f,	0.0f, 1.0f,0.0f,  0.0f,0.0f,
			 0.5f,  0.5f,  0.5f,	0.0f, 1.0f,0.0f,  0.0f,1.0f,

			 0.5f,  0.5f,  0.5f,	0.0f, 1.0f,0.0f,  0.0f,1.0f,
			 -0.5f, 0.5f,  0.5f,	0.0f, 1.0f,0.0f,  1.0f,1.0f,
			 -0.5f, 0.5f, -0.5f,	0.0f, 1.0f,0.0f,  1.0f,0.0f,
			 
			//CARA TRASERA
			  0.5f, -0.5f, -0.5f,   0.0f, -1.0f,0.0f,  1.0f,0.0f,
			 -0.5f, -0.5f, -0.5f,   0.0f, -1.0f,0.0f,  0.0f,0.0f,
			  0.5f, -0.5f,  0.5f,   0.0f, -1.0f,0.0f,  1.0f,1.0f,

			 -0.5f, -0.5f,  0.5f,   0.0f, -1.0f,0.0f,  0.0f,1.0f,
			  0.5f, -0.5f,  0.5f,	0.0f, -1.0f,0.0f,  1.0f,1.0f,
			 -0.5f, -0.5f,  -0.5f,  0.0f, -1.0f,0.0f,  0.0f,0.0f,
			 
			//CARA DERECHA
			  0.5f, -0.5f,  -0.5f,  1.0f, 0.0f,0.0f,  0.0f,0.0f,
			  0.5f, -0.5f,  0.5f,   1.0f, 0.0f,0.0f,   0.0f,1.0f,
			  0.5f, 0.5f,  0.5f,    1.0f, 0.0f,0.0f,   1.0f,1.0f,

			   0.5f,  0.5f,  0.5f,   1.0f, 0.0f,0.0f,  1.0f,1.0f,
			   0.5f,  0.5f, -0.5f,	 1.0f, 0.0f,0.0f,  1.0f,0.0f,
			   0.5f, -0.5f,  -0.5f,  1.0f, 0.0f,0.0f,  0.0f,0.0f,
	
			  //CARA IZQUIERDA	
			   -0.5f,  -0.5f,  0.5f,   -1.0f, 0.0f,0.0f, 1.0f,1.0f,
			   -0.5f, -0.5f,  -0.5f,  -1.0f, 0.0f,0.0f, 1.0f,0.0f,
			   -0.5f,  0.5f,  -0.5f,  -1.0f, 0.0f,0.0f, 0.0f,0.0f,

			   - 0.5f,  0.5f,  -0.5f,  -1.0f, 0.0f,0.0f, 0.0f,0.0f,
			   -0.5f,  0.5f, 0.5f,	 -1.0f, 0.0f,0.0f, 0.0f,1.0f,
			   -0.5f, -0.5f,  0.5f,   -1.0f, 0.0f,0.0f, 1.0f,1.0f,

			  //CARA SUPERIOR
			    0.5, -0.5,  0.5f,    0.0f, 0.0f,1.0f,	1.0f,0.0f,
				-0.5f, -0.5f,  0.5f,   0.0f, 0.0f,1.0f, 0.0f,0.0f,
				-0.5f, 0.5f,  0.5f,    0.0f, 0.0f,1.0f, 0.0f,1.0f,

				-0.5f, 0.5f,  0.5f,    0.0f, 0.0f,1.0f,  0.0f,1.0f,
				 0.5f, 0.5f,  0.5f,	   0.0f, 0.0f,1.0f,  1.0f,1.0f,
				 0.5f, -0.5f,  0.5f,   0.0f, 0.0f,1.0f,  1.0f,0.0f,

				//CARA INFERIOR
			   -0.5f, -0.5f,  -0.5f,  0.0f, 0.0f,-1.0f,  1.0f,0.0f,
			   0.5f, -0.5f,  -0.5f,   0.0f, 0.0f,-1.0f,  0.0f,0.0f,
			   0.5f,  0.5f,  -0.5f,   0.0f, 0.0f,-1.0f,  0.0f,1.0f,

			   0.5f,0.5f,	-0.5f,	0.0f, 0.0f,	-1.0f,  0.0f,1.0f,
			   -0.5f,0.5f,	-0.5f,	0.0f, 0.0f,	-1.0f,  1.0f,1.0f,
			   -0.5f,-0.5f,	 -0.5f,	0.0f, 0.0f,	-1.0f,  1.0f,0.0f

	};

	glGenVertexArrays(1, &VAOCubo);
	glGenBuffers(1, &VBO);
	glGenBuffers(1, &EBO);
	// bind the Vertex Array Object first.
	glBindVertexArray(VAOCubo);

	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);


	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);

	// position Color
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8  * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(1);

	//textura
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(6 * sizeof(float)));
	glEnableVertexAttribArray(2);

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
	glDeleteBuffers(1, &VBO);
	glDeleteBuffers(1, &EBO);

	return VAOCubo;
}

//*************************************FUNCIONES DE PERSPECTIVA****************************

void CamaraPrimeraPersona() {
	glViewport(0, 0, SCR_WIDTH, SCR_HEIGHT);
	glm::mat4 view;
	view = glm::mat4();
	view = glm::lookAt(glm::vec3(base.px + 0.4 * cos(ARADIANES * base.angulo_trans), base.py + 0.4 *
		sin(ARADIANES * base.angulo_trans), base.pz + 0.4f), glm::vec3(base.px + 10 * cos(ARADIANES *
			base.angulo_trans), base.py + 10 * sin(ARADIANES * base.angulo_trans), base.pz), glm::vec3(0.0f, 0.0f,
				1.0f));

	unsigned int viewLoc = glGetUniformLocation(shaderProgram, "view");
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, value_ptr(view));

	glm::mat4 projection;
	projection = glm::mat4();
	projection = glm::perspective(glm::radians(75.0f), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 5.0f);

	unsigned int projectionLoc = glGetUniformLocation(shaderProgram, "projection");
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, value_ptr(projection));

}

void CamaraTerceraPersona() {
	angulo = 0;

	glViewport(0, 0, SCR_WIDTH, SCR_HEIGHT);
	glm::mat4 view;
	view = glm::mat4();


	view = glm::lookAt(glm::vec3(base.px - 0.8 * cos(ARADIANES * base.angulo_trans), base.py - 0.8 *
		sin(ARADIANES * base.angulo_trans), base.pz + 0.4f), glm::vec3(base.px + 10 * cos(ARADIANES *
			base.angulo_trans), base.py + 10 * sin(ARADIANES * base.angulo_trans), base.pz + 0.4f), glm::vec3(0.0f,
				0.0f, 1.0f));

	unsigned int viewLoc = glGetUniformLocation(shaderProgram, "view");
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, value_ptr(view));

	glm::mat4 projection;
	projection = glm::mat4();
	projection = glm::perspective(glm::radians(75.0f), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 5.0f);

	unsigned int projectionLoc = glGetUniformLocation(shaderProgram, "projection");
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, value_ptr(projection));
}

void CamaraExterior() {
	glViewport(0, 0, SCR_WIDTH, SCR_HEIGHT);
	glm::mat4 view;
	view = glm::mat4();
	view = glm::lookAt(glm::vec3(0.0f, 0.0f, 3.0f), glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 1.0f, 0.0f));

	unsigned int viewLoc = glGetUniformLocation(shaderProgram, "view");
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, value_ptr(view));

	glm::mat4 projection;
	projection = glm::mat4();
	projection = glm::perspective(glm::radians(60.0f), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 5.0f);

	unsigned int projectionLoc = glGetUniformLocation(shaderProgram, "projection");
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, value_ptr(projection));
}

//*********************************FUNCIONES CONSTRUCCIÓN****************************

glm::mat4 transform; // es la matriz de transformacion	
glm::mat4 transformtemp;

void AlgoritmoSuelo(unsigned int COLOR, unsigned int transformLoc) {
	float i, j;
	float escalasuelo = 10;
	glUniform3f(COLOR, 1.0, 1.0, 1.0);

	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_2D, PisoP);

	for (i = -2; i <= 2; i += (1 / escalasuelo)) {
		for (j = -2; j <= 2; j += (1 / escalasuelo)) {
			transform = glm::mat4();
			transform = glm::rotate(transform, (float)(angulo * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
			transform = glm::translate(transform, glm::vec3(i, j, 0.0f));

			//transform = glm::rotate(trasnform, angulo, glm:: vec3(1.0f,0.0f,0.0f);
			transform = glm::scale(transform, glm::vec3((1 / escalasuelo), (1 / escalasuelo), (1 / escalasuelo)));
			//la cargo
			glUniformMatrix4fv(transformLoc, 1, GL_FALSE, glm::value_ptr(transform));
			glBindVertexArray(VAOCuadrado); // el dibujo

			glDrawArrays(GL_TRIANGLES, 0, 6);
			//glDrawElements(GL_LINES, 8, GL_UNSIGNED_INT, 0);

		}

	}
}

void AlgoritmoGrua(unsigned int COLOR, unsigned int transformLoc,objeto obj){

	//glUniform3f(COLOR, brazo.col1, brazo.col2, brazo.col3);
	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_2D, obj.textura);
	
	transform = glm::mat4();

	if (obj.base == false) {
		transform = transformtemp;
	} else if (obj.base == true) {
		transform = glm::rotate(transform, (float)(angulo * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
	}

	transform = glm::translate(transform, glm::vec3(obj.px,obj.py,obj.pz));

	if (obj.base == true) {
		transform = glm::rotate(transform, (float)(base.angulo_trans * ARADIANES), glm::vec3(0.0f, 0.0f, 1.0f));
	}

	if (obj.articulacion == true) {
		transform = glm::rotate(transform, (float)(obj.angulo_trans * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
		transform = glm::rotate(transform, (float)(obj.angulo_trans_2 * ARADIANES), glm::vec3(0.0f, 1.0f, 0.0f));

	}

	transformtemp = transform;
	transform = glm::scale(transform, glm::vec3((obj.sx), (obj.sy), (obj.sz)));
	glUniformMatrix4fv(transformLoc, 1, GL_FALSE, glm::value_ptr(transform));
	glBindVertexArray(obj.listarender);

	if (obj.articulacion == true) {
		if (obj.foco == false) {
			glDrawArrays(GL_TRIANGLES, 0, 1080);
		}
	} else if (obj.articulacion == false || obj.base == true) {
			glDrawArrays(GL_TRIANGLES, 0, 36);
		
	} 
}

void AlgoritmoTexturaPiscina() {

	double t = glfwGetTime();
	t *= 250;

	int T = (int)t;
	if (T % 100 == 0) {
		for (int i = 0; i < 16; i++) {
			if (NowT == PiscinaT[i]) {
				if (NowT != PiscinaT[14]) {
					NowT = PiscinaT[i + 1];
				}
				else {
					NowT = PiscinaT[0];
				}
				break;
			}
		}
	}
}

void AlgoritmoPiscina(int COLOR,int transformLoc) {
	
	glUniform3f(COLOR, 0.0f, 0.0f, 0.0f);
	glm::mat4 transformP;
	transformP = glm::mat4();
	transformP = glm::rotate(transformP, (float)(angulo * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
	transformP = glm::translate(transformP, glm::vec3(-1.0f,0.0f,0.44f));
	transformP = glm::scale(transformP, glm::vec3(1.0f,1.0f,-0.77f));
	
	glUniformMatrix4fv(transformLoc, 1, GL_FALSE, glm::value_ptr(transformP));
	glActiveTexture(GL_TEXTURE0);
	AlgoritmoTexturaPiscina();
	glBindTexture(GL_TEXTURE_2D, NowT);
	glBindVertexArray(VAOCuadrado);
	glDrawArrays(GL_TRIANGLES, 0, 10);
}

//*******************************FUNCIONES TEXTURAS************************
int Carga_Texturas(const char *i) {

	unsigned int textura;
	glGenTextures(1, &textura);
	glBindTexture(GL_TEXTURE_2D, textura);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


	int w, h, nrChannels;
	unsigned char* data = stbi_load(i, &w, &h, &nrChannels, 0);
	if (data) {

		int f;
		
		if (nrChannels == 1) {
			f = GL_RED;
		}
		else if (nrChannels == 2) {
			f = GL_ALPHA;
		}
		else if (nrChannels == 3){
			f = GL_RGB;
		}
		else if (nrChannels == 4) {
			f = GL_RGBA;
		}
		
		glTexImage2D(GL_TEXTURE_2D,0, f, w,h,0, f, GL_UNSIGNED_BYTE,data);
	}
	else {
		textura = 0;
	}

	stbi_image_free(data);
	return textura;
}

void DescargaDeTexturas() {

	PisoP = Carga_Texturas("hierba.jpg");

	PiscinaT[0] = Carga_Texturas("caust00.png");
	NowT = PiscinaT[0];
	PiscinaT[1] = Carga_Texturas("caust01.png");
	PiscinaT[2] = Carga_Texturas("caust02.png");
	PiscinaT[3] = Carga_Texturas("caust03.png");
	PiscinaT[4] = Carga_Texturas("caust04.png");
	PiscinaT[5] = Carga_Texturas("caust05.png");
	PiscinaT[6] = Carga_Texturas("caust06.png");
	PiscinaT[7] = Carga_Texturas("caust07.png");
	PiscinaT[8] = Carga_Texturas("caust08.png");
	PiscinaT[9] = Carga_Texturas("caust09.png");
	PiscinaT[10] = Carga_Texturas("caust10.png");
	PiscinaT[11] = Carga_Texturas("caust11.png");
	PiscinaT[12] = Carga_Texturas("caust12.png");
	PiscinaT[13] = Carga_Texturas("caust13.png");
	PiscinaT[14] = Carga_Texturas("caust14.png");
	PiscinaT[15] = Carga_Texturas("caust15.png");
	
	base.textura = base1.textura = base2.textura = Carga_Texturas("ladrillo.jpg");
	baseA1.textura = baseA2.textura = foco.textura = Carga_Texturas("cemento.jpg");
}

//*********************************************************************************************************************************
int main(){

	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	

	//Creo la ventana														
	GLFWwindow* window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "GRUA", NULL, NULL);
	if (window == NULL)
	{
		std::cout << "Fallo Crear Ventana" << std::endl;
		glfwTerminate();
		return -1;
	}
	glfwMakeContextCurrent(window);
	glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

	if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
	{
		std::cout << "Failed to initialize GLAD" << std::endl;
		return -1;
	}

	glfwSetKeyCallback(window, keyCallBack);
	openGlInit();
	
	shaderProgram = setShaders("shader.vert", "shader.frag");
	//dibujaEjes();
	dibujaCubo();
	dibujaCuadrado();
	dibujaEsfera();

	DescargaDeTexturas();

	glUseProgram(shaderProgram); 
		glUniform1i(glGetUniformLocation(shaderProgram, "texture1"), 0);
		glUniform1i(glGetUniformLocation(shaderProgram, "texture2"), 1);

	while (!glfwWindowShouldClose(window)){
	
		processInput(window);
		
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f); //Borro el Buffer the la ventana
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		switch (camara){
			case 0:
				CamaraExterior();
				break;
			case 1:
				CamaraTerceraPersona();
				break;
			case 2:
				CamaraPrimeraPersona();
				break;
		}

		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); 

		base.listarender = VAOCubo;
		baseA1.listarender = VAOEsfera;
		base1.listarender = VAOCubo;
		baseA2.listarender = VAOEsfera;
		base2.listarender = VAOCubo;
	    foco.listarender = VAOEsfera;

		//la busco en el shader
		unsigned int transformLoc = glGetUniformLocation(shaderProgram, "transform");
		unsigned int lightColorLoc = glGetUniformLocation(shaderProgram, "lightColor");
		glUniform3f(lightColorLoc,	1.0f, 1.0f, 1.0f);

		//SUELO
		unsigned int lightColorSueloLoc = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoSuelo(lightColorSueloLoc,transformLoc);
	
		//PISCINA
		unsigned int ColorPiscina = glGetUniformLocation(shaderProgram, " objectColor");
		AlgoritmoPiscina(ColorPiscina, transformLoc);

		//BASE
		base.base = true;
		unsigned int ColorBase = glGetUniformLocation(shaderProgram, " objectColor");
		AlgoritmoGrua(ColorBase, transformLoc,base);
		
		//Primera articulación
		baseA1.articulacion = true;
		unsigned int ColorBaseA1 = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoGrua(ColorBaseA1, transformLoc, baseA1);
		//Primer brazo
		base1.articulacion = false;
		unsigned int ColorBase1 = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoGrua(ColorBase1,transformLoc,base1);
		//Segunda articulación
		baseA2.articulacion = true;
		unsigned int ColorBaseA2 = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoGrua(ColorBaseA2, transformLoc,baseA2);
		//Segundo brazo
		base2.articulacion = false;
		unsigned int ColorBase2 = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoGrua(ColorBase2, transformLoc, base2);

		//FOCO
		foco.foco = true;
		unsigned int colorLocfoco = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoGrua(colorLocfoco, transformLoc, foco);

		unsigned int lightPosLoc = glGetUniformLocation(shaderProgram, "lightPos");
		glm::vec3 FocoPos = ActualizarPosicion(transform);
		glUniform3f(lightPosLoc, FocoPos.x, FocoPos.y, FocoPos.z);

		float lightCuttoffLoc = glGetUniformLocation(shaderProgram, "lightCutOff");
		glUniform1f(lightCuttoffLoc, G);
		
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	glDeleteVertexArrays(1, &VAO);

	glfwTerminate();
	return 0;
}

void processInput(GLFWwindow *window)
{
	if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
		glfwSetWindowShouldClose(window, true);
}

void keyCallBack(GLFWwindow *window, int key, int scancode, int action, int mods) {

	std::cout << key << std::endl;

	//primera arti
	if (key == 65) {
		baseA1.angulo_trans++;
	}
	if (key == 83) {
		baseA1.angulo_trans--;
	}
	if (key == 87) {
		baseA1.angulo_trans_2++;
	}
	if (key == 68) {
		baseA1.angulo_trans_2--;
	}

	//segundo arti
	if (key == 84) {
		baseA2.angulo_trans++;
	}
	if (key == 71) {
		baseA2.angulo_trans--;
	}
	if (key == 70) {
		baseA2.angulo_trans_2++;
	}
	if (key == 72) {
		baseA2.angulo_trans_2--;
	}

	if (key == 75) {
		angulo++; //printf("%f\n",angulo);
	}

	if (key == 76) {
		angulo--; //printf("%f\n", angulo);
	}

	if (key == GLFW_KEY_LEFT) {
		base.angulo_trans++;
	}
	if (key == GLFW_KEY_RIGHT) {
		base.angulo_trans--;
	}

	if (key == GLFW_KEY_UP) {
		base.px += 0.0075 * cos(base.angulo_trans * ARADIANES);
		base.py += 0.0075 * sin(base.angulo_trans * ARADIANES);
	}

	if (key == GLFW_KEY_DOWN) {
		base.px -= 0.0075 *cos(base.angulo_trans * ARADIANES);
		base.py -= 0.0075 * sin(base.angulo_trans * ARADIANES);
	}

	if (key == 257) {
		camara++;
		if (camara == 3) {
			camara = 0;
		}
	}


	if (key == GLFW_KEY_SPACE && action == GLFW_REPEAT) {
		std::cout << "Key presionado" << std::endl;
	}

	
}

void framebuffer_size_callback(GLFWwindow* window,  int width, int height){
	SCR_WIDTH = width;
	SCR_HEIGHT = height;

	//glViewport(0, 0, width, height);
}
