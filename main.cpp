#include <windows.h>
#include <glad.h>
#include <glfw3.h>
#include <stdio.h>
#include <math.h>
#include <iostream>

#include <stdlib.h>  
#include <stdio.h>  
#include <time.h>  

#include "esfera.h"
#define STB_IMAGE_IMPLEMENTATION
#include <stb_image.h>

//Para las transformaciones
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <random>
#include <new.h>

//VENTANA
unsigned int SCR_WIDTH = 700;
unsigned int SCR_HEIGHT = 700;
#define ARADIANES  3 //0.0174

extern GLuint setShaders(const char *nVertx, const char *nFrag);
GLuint shaderProgram;

// unsigned int VBO, VAO, EBO;
unsigned int VAO;
unsigned int VAOCubo;
unsigned int VAOCuadrado;
unsigned int VAOEsfera;

float  angulo;
int camara=0;
int G = 20;
glm::mat4 transform; // es la matriz de transformacion	
glm::mat4 transformtemp;
glm::mat4 transformtemp2;
glm::mat4 transformM;
boolean LaManzanaFueComida = false;
int TECLA = 0;
int PisoP = 0;

typedef struct {
	double px, py, pz; //posicio inicial
	float angulo_trans; //angulo giro x
	float angulo_trans_2; //angulo giro z
	float sx, sy, sz; //escalado en los dos ejes
	unsigned int listarender; //VAO
	float col1,col2,col3; //color 
	boolean manzana; 
	boolean base;
	boolean foco;
	GLuint textura;
	double px2, py2, pz2;
} objeto ;

//************************MANZANA***********************
objeto manzana = { 0,0,0.15,0,0,.05,.05,.05,0,0,0.0,0.,0,0,0 };

objeto foco = {0,0,0.5,0,0,.02,.02,.02,0,0,0,0,0,0,0 };

//***************************MURO**********************************
//arriba
objeto muroUP = {	0,2,0.15,0,0,4.0,.1,0.8,0, 0.0,0.0,1.0,0,0,0 };
//abajo
objeto muroDOWN = { 0,-2,0.15,0,0,4.0,.1,0.8,0, 0.0,0.0,1.0,0,0,0 };
//derecha
objeto muroDER = { 2,0,0.15,0,0,.1,4.0,0.8,0, 0.0,0.0,1.0,0,0,0 };
//izquierda
objeto muroIZQ = {-2,0,0.15,0,0,.1,4.0,0.8,0, 0.0,0.0,1.0,0,0,0 };

//***************************CABEZA SERPIENTE**************************
objeto base = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
int contadorCuadrado = 0;
boolean LaSerpienteAumento = false;
boolean colision = false;
double aAntX = 0;
double aAntY = 0;


//*************************************FUNCIONES DE AUXILIARES****************************

void openGlInit() {
	//Habilito aqui el depth en vez de arriba para los usuarios de linux y mac mejor arriba
	//Incializaciones varias
	glClearDepth(1.0f); //Valor z-buffer
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // valor limpieza buffer color
	glEnable(GL_DEPTH_TEST); // z-buffer
	//glEnable(GL_CULL_FACE); //ocultacion caras back
	//glEnable(GL_TEXTURE_2D);
	glCullFace(GL_BACK);
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

double PosicionAleatoriaEJEX(double min, double max) {
	double a;

	srand(time(0));
	do {
		//double range = (max - min);
		//double div = RAND_MAX / range;
		//a = min + (rand() / div);
		a = min + (rand() % (int)(max - min));
	} while (a == aAntX);

	aAntX = a;



	srand(time(0));
	return a;
}

double PosicionAleatoriaEJEY(double min, double max) {
	double a;

	srand(time(0));
	do {
		a = min + (rand() % (int)(max - min));
	} while (a == aAntY);

	aAntY = a;


	srand(time(0));
	return a;
}

glm::vec3 ActualizarPosicion(glm::mat4 transform) {
	return glm::vec3(transform * glm::vec4(0.0f, 0.0f, 0.0f, 1.0f));
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
	projection = glm::perspective(glm::radians(80.0f), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 5.0f);

	unsigned int projectionLoc = glGetUniformLocation(shaderProgram, "projection");
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, value_ptr(projection));
}

//*************************************FUNCIONES DE TEXTURA****************************

int Carga_Texturas(const char* i) {

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
		else if (nrChannels == 3) {
			f = GL_RGB;
		}
		else if (nrChannels == 4) {
			f = GL_RGBA;
		}

		glTexImage2D(GL_TEXTURE_2D, 0, f, w, h, 0, f, GL_UNSIGNED_BYTE, data);
	}
	else {
		textura = 0;
	}

	stbi_image_free(data);
	return textura;
}

void DescargaDeTexturas() {

	PisoP = Carga_Texturas("1.jpg");
	manzana.textura = Carga_Texturas("manzana2.jpg");
	base.textura = Carga_Texturas("serpiente.jpg");
	muroUP.textura = muroDOWN.textura = muroDER.textura = muroIZQ.textura = Carga_Texturas("2.jpg");

}

//*********************************FUNCIONES CONSTRUCCIÓN****************************

void AlgoritmoBloque(unsigned int COLOR, unsigned int transformLoc, objeto b) {

	glUniform3f(COLOR, b.col1, b.col2, b.col3);
	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_2D, b.textura);

	transform = glm::mat4();

	if (b.base == false) {
		transform = transformtemp;
	}
	else if (b.base == true) {
		transform = glm::rotate(transform, (float)(angulo * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
	}


	transform = glm::translate(transform, glm::vec3(b.px, b.py, b.pz));

	if (base.px > 2.0 || base.px < -2.0 || base.py > 2.0 || base.py < -2.0) {

		MessageBoxW(NULL, L"FIN DE LA PARTIDA", L" ",
			MB_SYSTEMMODAL | MB_ICONEXCLAMATION);
		exit(0);
	}

	if (b.base == true) {
		transform = glm::rotate(transform, (float)(b.angulo_trans * ARADIANES), glm::vec3(0.0f, 0.0f, 1.0f));
	}

	if (b.manzana == true) {
		transform = glm::rotate(transform, (float)(b.angulo_trans * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
		transform = glm::rotate(transform, (float)(b.angulo_trans_2 * ARADIANES), glm::vec3(0.0f, 1.0f, 0.0f));

	}

	transformtemp = transform;
	transform = glm::scale(transform, glm::vec3((b.sx), (b.sy), (b.sz)));
	glUniformMatrix4fv(transformLoc, 1, GL_FALSE, glm::value_ptr(transform));
	glBindVertexArray(b.listarender);

	if (b.manzana == true) {
		if (b.foco == false) {
			glDrawArrays(GL_TRIANGLES, 0, 1080);
		}
	}
	else if (b.manzana == false || b.base == true) {
		glDrawArrays(GL_TRIANGLES, 0, 36);

	}

}

void AlgoritmoSuelo(unsigned int COLOR, unsigned int transformLoc) {
	float i, j;
	float escalasuelo = 10;
	glUniform3f(COLOR, 1.0, 1.0, 1.0);

	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_2D, PisoP);

	for (i = -2; i <= 2 ; i += (1 / escalasuelo)) {
		for (j = -2; j <= 2; j += (1 / escalasuelo)) {
			transform = glm::mat4();
			transform = glm::rotate(transform, (float)(angulo * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
			transform = glm::translate(transform, glm::vec3(i,j, 0.0f));

			transform = glm::scale(transform, glm::vec3((1 / escalasuelo), (1 / escalasuelo), (1 / escalasuelo)));
			//la cargo
			glUniformMatrix4fv(transformLoc, 1, GL_FALSE, glm::value_ptr(transform));
			glBindVertexArray(VAOCuadrado); 

			glDrawArrays(GL_TRIANGLES, 0, 6);

		}

	}

	
	muroUP.base = true;
	muroUP.listarender = VAOCubo;
	unsigned int ColorMuro1 = glGetUniformLocation(shaderProgram, " objectColor");
	AlgoritmoBloque(ColorMuro1 , transformLoc, muroUP);

	muroDOWN.base = true;
	muroDOWN.listarender = VAOCubo;
	unsigned int ColorMuro2 = glGetUniformLocation(shaderProgram, " objectColor");
	AlgoritmoBloque(ColorMuro2, transformLoc, muroDOWN);

	muroDER.base = true;
	muroDER.listarender = VAOCubo;
	unsigned int ColorMuro3 = glGetUniformLocation(shaderProgram, " objectColor");
	AlgoritmoBloque(ColorMuro3, transformLoc, muroDER);

	muroIZQ.base = true;
	muroIZQ.listarender = VAOCubo;
	unsigned int ColorMuro4 = glGetUniformLocation(shaderProgram, " objectColor");
	AlgoritmoBloque(ColorMuro4, transformLoc, muroIZQ);
	

}

void MovimientoSerpiente(unsigned int COLOR, unsigned int transformLoc, objeto *snake[40]) {
	
	int i = 1;
	LaSerpienteAumento = false;
	int ULTIMOBLOQUE = contadorCuadrado - 1;

	while (i < contadorCuadrado) {
		
		if ((i == ULTIMOBLOQUE) && (colision == true)) {
			colision = false;
			LaSerpienteAumento = true;

			snake[i]->py = snake[i - 1]->py; snake[i]->px = snake[i - 1]->px;
			snake[i]->py2 = snake[i - 1]->py; snake[i]->px2 = snake[i - 1]->px;
		}
		
		snake[i]->py2 = snake[i]->py; snake[i]->px2 = snake[i]->px;
		snake[i]->py = snake[i - 1]->py2; snake[i]->px = snake[i - 1]->px2;
		i++;
	}

	if (LaSerpienteAumento == true) {
		snake[ULTIMOBLOQUE]->py2 = snake[ULTIMOBLOQUE]->py; snake[ULTIMOBLOQUE]->px2 = snake[ULTIMOBLOQUE]->px;
		snake[ULTIMOBLOQUE]->py = snake[ULTIMOBLOQUE - 1]->py2; snake[ULTIMOBLOQUE]->px = snake[ULTIMOBLOQUE - 1]->px2;
	}

}

void processInput(GLFWwindow* window) {
	if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
		glfwSetWindowShouldClose(window, true);
}

void AlgoritmoKeyCallBack() {

	switch (TECLA) {
		//abajo
	case 264:
		base.px2 = base.px;
		base.py2 = base.py;
		base.py = base.py - 0.1;
		break;
		//arriba
	case 265:
		base.px2 = base.px;
		base.py2 = base.py;
		base.py = base.py + 0.1;
		break;
		//derecha
	case 262:
		base.px2 = base.px;
		base.px = base.px + 0.1;
		base.py2 = base.py;
		break;
		//izquierda
	case 263:
		base.px2 = base.px;
		base.px = base.px - 0.1;
		base.py2 = base.py;
		break;
	}
	Sleep(50);
}

void keyCallBack(GLFWwindow* window, int key, int scancode, int action, int mods) {

	std::cout << key << std::endl;

	switch (key) {
		
	case 265:
		if (TECLA != 264) {
			TECLA = 265;
		}
		break;

	case 264:
		if (TECLA != 265) {
			TECLA = 264;
		}
		break;
	case 263:
		if (TECLA != 262) {
			TECLA = 263;
		}
		break;
	case 262:
		if (TECLA != 263) {
			TECLA = 262;
		}

		break;
		
	case 51:
		camara = 1;
		break;
	case 50:
		camara = 0;
		break;
	case 49:
		camara = 2;
		break;
	}

	if (key == GLFW_KEY_SPACE && action == GLFW_REPEAT) {
		std::cout << "Key presionado" << std::endl;
	}

	

}

void framebuffer_size_callback(GLFWwindow* window, int width, int height) {
	SCR_WIDTH = width;
	SCR_HEIGHT = height;
}

void AlgoritmoManzana(unsigned int COLOR, unsigned int transformLoc,objeto m) {
	glUniform3f(COLOR, m.col1, m.col2, m.col3);
	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_2D, m.textura);
	
	transformM = glm::mat4();
	
	if (m.base == true) {
		transformM = glm::rotate(transformM, (float)(angulo * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
	}


	if (m.foco == false) {

		double u1 = PosicionAleatoriaEJEX(-1.8, 1.8);
		double u2 = PosicionAleatoriaEJEY(-1.8, 1.8);

		if ((float)base.px == (float)m.px && (float)base.py ==(float)m.py) {
			colision = true;
			m.px = manzana.px = foco.px = u1;
			m.py = manzana.py = foco.py = u2;
			contadorCuadrado++;
		}

		transformM = glm::translate(transformM, glm::vec3(m.px, m.py, m.pz));
		
	} else {
		transformM = glm::translate(transformM, glm::vec3(m.px, m.py, m.pz));
	}


	if (m.base == true) {
		transformM = glm::rotate(transformM, (float)(m.angulo_trans * ARADIANES), glm::vec3(0.0f, 0.0f, 1.0f));
	}

	if (m.manzana == true) {
		transformM = glm::rotate(transformM, (float)(m.angulo_trans * ARADIANES), glm::vec3(1.0f, 0.0f, 0.0f));
		transformM= glm::rotate(transformM, (float)(m.angulo_trans_2 * ARADIANES), glm::vec3(0.0f, 1.0f, 0.0f));

	}

	transformM = glm::scale(transformM, glm::vec3((m.sx), (m.sy), (m.sz)));
	glUniformMatrix4fv(transformLoc, 1, GL_FALSE, glm::value_ptr(transformM));
	glBindVertexArray(m.listarender);

	if (m.manzana == true) {
		if (m.foco == false) {
			glDrawArrays(GL_TRIANGLES, 0, 1080);
		}
	}
	else if (m.manzana == false || m.base == true) {
		glDrawArrays(GL_TRIANGLES, 0, 36);

	}



}


//*********************************SERPIENTE****************************

objeto base1 = { 10,10,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base2 = { 20,20,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base3 = { 30,30,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base4 = { 40,40,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base5 = { 50,50,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base6 = { 60,60,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base7 = { 70,70,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base8 = { 80,80,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base9 = { 90,90,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base10 = { 100,100,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base11 = { 110,110,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base12 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base13 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base14 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base15 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base16 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base17 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base18 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base19 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base20 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base21 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base22 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base23 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base24 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base25 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base26 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base27 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base28 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base29 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base30 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base31 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base32 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base33 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base34 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base35 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base36 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base37 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base38 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base39 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };
objeto base40 = { 0,0,0.15,0,0,.1,.1,.1,0, 0.0,0.0,1.0,0,0,0 };

objeto *snake[40] = { 
&base, &base1, &base2, &base3, &base4, 
&base5, &base6, &base7, &base8, &base9, 
&base10,& base11,& base12,& base13,& base14, 
&base15,& base16,& base17,& base18,& base19, 
&base20,& base21,& base22,& base23,& base24,
&base25,& base26,& base27,& base28,& base29,
&base30,& base31,& base32,& base33,& base34,
&base35,& base36,& base37,& base38,& base39,
};

void CrearListaSnake() {

	for (int i = 0; i < 40; i++) {
		snake[i]->listarender = VAOCubo;
		snake[i]->textura = base.textura;
		snake[i]->base = true;
		snake[i]->manzana = false,
		snake[i]->foco = false;
	}

	manzana.listarender = VAOEsfera;
	foco.listarender = VAOEsfera;

	manzana.base = false;
	manzana.manzana = true;
	manzana.foco = false;

	foco.foco = true;
	foco.manzana = false;
	foco.base = false;
}

int main(){

	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);


	//Creo la ventana														
	GLFWwindow* window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "SNAKE GAME", NULL, NULL);
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
	dibujaCubo();
	dibujaCuadrado();
	dibujaEsfera();

	DescargaDeTexturas();

	glUseProgram(shaderProgram); 
		glUniform1i(glGetUniformLocation(shaderProgram, "texture1"), 0);
		glUniform1i(glGetUniformLocation(shaderProgram, "texture2"), 1);

	//la busco en el shader
	unsigned int transformLoc = glGetUniformLocation(shaderProgram, "transform");

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

		CrearListaSnake();

		unsigned int lightColorLoc = glGetUniformLocation(shaderProgram, "lightColor");
		glUniform3f(lightColorLoc,	1.0f, 1.0f, 1.0f);

		unsigned int lightColorSueloLoc = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoSuelo(lightColorSueloLoc,transformLoc);

		unsigned int ColorBase = glGetUniformLocation(shaderProgram, " objectColor");
		AlgoritmoBloque(ColorBase, transformLoc, *snake[0]);

		unsigned int colorManzana = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoManzana(colorManzana, transformLoc,manzana);
	    unsigned int colorFoco = glGetUniformLocation(shaderProgram, "objectColor");
		AlgoritmoManzana(colorFoco, transformLoc,foco);
		
		unsigned int lightPosLoc = glGetUniformLocation(shaderProgram, "lightPos");
		glm::vec3 FocoPos = ActualizarPosicion(transformM);
		glUniform3f(lightPosLoc, FocoPos.x, FocoPos.y, FocoPos.z);
		float lightCuttoffLoc = glGetUniformLocation(shaderProgram, "lightCutOff");
		glUniform1f(lightCuttoffLoc, G);
		
		for (int i= 1; i < contadorCuadrado; i++) {
			AlgoritmoBloque(ColorBase, transformLoc, *snake[i]);
			if ((float)base.px != (float)0 && (float)base.py != (float)0) {
				if ((float)snake[i]->px == (float)base.px && (float)snake[i]->py == (float)base.py) {
					MessageBoxW(NULL, L"FIN DE LA PARTIDA", L" ", MB_SYSTEMMODAL | MB_ICONEXCLAMATION);
					exit(0);
				}
			}


		}
		MovimientoSerpiente(ColorBase, transformLoc, snake);
		AlgoritmoKeyCallBack();

		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	glDeleteVertexArrays(1, &VAO);
	glfwTerminate();
	return 0;
}
