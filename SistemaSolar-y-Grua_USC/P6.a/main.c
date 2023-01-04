#include <windows.h>	//Inclusion de variables de windows
#include "P6.a/defini.h"
#include <glut.h>	//Inclusion de GLUT
#include <gl.h>		//Inclusion de GL		 	
#include <glu.h>	
#include <stdio.h>
#include <math.h>	//Inclusion de librerias auxiliares	

#define STB_IMAGE_IMPLEMENTATION
#include <stb_image.h>


GLint W_WIDTH = 1000;	 //Ancho de la ventana
GLint W_HEIGHT = 1000;		//Alto de la ventana

#define GL_PI 3.14f
#define FOVY 165
								// Ángulos de rotación
static GLfloat xRot = 0.0f;
static GLfloat yRot = 0.0f;
GLfloat angulo;
GLuint lista;
float theta; 

//Variables de iluminacion
GLfloat Ambiente[] = { 0.0f, 0.0f, 0.0f, 1.0f };
GLfloat Difuso[] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat EspecularRef[] = { 1.0f,1.0f,1.0f,1.0f };
GLfloat Especular[] = { 1.0f, 1.0f , 1.0f ,1.0f };


//Variables de definición de la posición del foco y dirección de iluminación
GLfloat PosicionLuz[] = { 0.0f,0.0f,0.0f,1.0f };
GLfloat DireccionSpot[] = { 1.0f, 1.0f,1.0f };


int camara = 1;
int orbitas = 1;

// Funciones externas
extern void myCamara();
extern void myTeclado(unsigned char tras, int x, int y);
extern void myTeclasespeciales(int cursor, int x, int y);
extern int myEsfera();
extern void myEjes(void);
extern void myTelescopio(float distancia, float angulo, float distancia_obj, float angulo_obj);
extern void myTelescopioConSatelite(float distancia, float angulo, float distancia_obj, float angulo_obj);

objeto sol = { 0,0,0,10,0,250,0,1.0,1.0,1.0,0};
objeto mercurio = { 400,10,0,50,0,50,0,1.0,1.0,1.0,0};
objeto venus = { 700, 7.5, 0, 10, 0, 100 ,0,1.0,1.0,1.0,0};
objeto tierra = { 1100,5,0,10,0,110,0,1.0,1.0,1.0,0};
objeto luna = { 170,25,0,10,0,20,0,1.0,1.0,1.0,0};
objeto iss = { 280, 20, 0 ,10 ,0 ,35 , 0,1.0,1.0,1.0,0};
objeto marte = { 1650,4,0,10,0,110,0,1.0,1.0,1.0,0};
objeto jupiter = { 2050, 2.75, 0, 10, 0, 200,0, 1.0f,1.0f,1.0f,0};
objeto saturno = { 2540, 2,0 ,10, 0, 150, 0,1.0,1.0,1.0,0};
objeto urano = { 2850, 1.5, 0, 10, 0, 80, 0,1.0,1.0,1.0,0};
objeto neptuno = { 3150,1,0,10,0,80,0,1.0,1.0,1.0,0};


void changeSize(GLint w, GLint h) {

	//ajusta la vista a las dimensaiones de la ventana
	glViewport(0, 0, w, h);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	gluPerspective(FOVY, (float)w / (float)h, 1.0, 500.0);
	glutPostRedisplay();
}

void myOrbitas(int a) {
	if (orbitas) {

		glBegin(GL_LINE_LOOP);

		for (int i = 0; i < 360; i++) {
			theta = ((i * GL_PI) / 180);
			glVertex3f(a * cos(theta), 0.0, a * sin(theta));
		}

		glEnd();
	}
}

void AlgoritmomyDisplay(objeto obj) {
	glPushMatrix();

	glColor3f(obj.c1, obj.c2, obj.c3);
	glBindTexture(GL_TEXTURE_2D, obj.textura);
	myOrbitas(obj.distancia);

	glRotatef(obj.angulo_trans, 0.0, 1.0, 0.0);
	glTranslatef(obj.distancia, 0.0, 0.0);

	glPushMatrix();

	glRotatef(obj.angulo_rot, 0.0, 1.0, 0.0);
	myEjes();
	glScalef(obj.tamano, obj.tamano, obj.tamano);

	glColor3f(obj.c1, obj.c2, obj.c3);
	glBindTexture(GL_TEXTURE_2D, obj.textura);
	glCallList(obj.listarender);
	glPopMatrix();

		if (obj.satelite == TRUE ) {
			AlgoritmomyDisplay(luna);
			AlgoritmomyDisplay(iss);
		}

	glPopMatrix();


}

void myDisplay(void) {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the window with current clearing color

	tierra.satelite = TRUE;

	switch (camara){
		case 1:
			myCamara();
			break;
		case 2:
			myTelescopio(tierra.distancia, tierra.angulo_trans, sol.distancia, sol.angulo_trans);
			break;
		case 3:
			myTelescopio(tierra.distancia, tierra.angulo_trans, mercurio.distancia, mercurio.angulo_trans);
			break;
		case 4:
			myTelescopio(tierra.distancia, tierra.angulo_trans, marte.distancia, marte.angulo_trans);
			break;
		case 5:
			myTelescopio(tierra.distancia, tierra.angulo_trans, venus.distancia, venus.angulo_trans);
			break;
		case 6:
			myTelescopioConSatelite(tierra.distancia, tierra.angulo_trans, luna.distancia, luna.angulo_trans);
			break;
		case 7:
			myTelescopioConSatelite(tierra.distancia, tierra.angulo_trans, iss.distancia, iss.angulo_trans);
			break;
		case 8:
			myTelescopio(tierra.distancia, tierra.angulo_trans, jupiter.distancia, jupiter.angulo_trans);
			break;
		case 9:
			myTelescopio(tierra.distancia, tierra.angulo_trans, saturno.distancia, saturno.angulo_trans);
			break;
		case 10:
			myTelescopio(tierra.distancia, tierra.angulo_trans, urano.distancia, urano.angulo_trans);
			break;
		case 11:
			myTelescopio(tierra.distancia, tierra.angulo_trans, neptuno.distancia, neptuno.angulo_trans);
			break;
	}
	
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	// Activamos y configuramos Blend
	//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	//glEnable(GL_BLEND); 

	glDisable(GL_LIGHTING);
	AlgoritmomyDisplay(sol);
	glEnable(GL_LIGHTING);

	AlgoritmomyDisplay(mercurio);
	AlgoritmomyDisplay(venus);
	AlgoritmomyDisplay(tierra);
	AlgoritmomyDisplay(marte);
	AlgoritmomyDisplay(jupiter);
	AlgoritmomyDisplay(saturno);
	AlgoritmomyDisplay(urano);
	AlgoritmomyDisplay(neptuno);

	glFlush();
	glutSwapBuffers();
}

void AlgoritmoMyMovimiento(objeto* obj) {
	obj->angulo_trans += obj->velocidad_trans;
	if (obj->angulo_trans > 360) obj->angulo_trans -= 360;
	obj->angulo_rot += obj->velocidad_rot;
	if (obj->angulo_rot > 360) {
		obj->angulo_rot -= 360;
	}
}

void myMovimiento() {
	AlgoritmoMyMovimiento(&sol);
	AlgoritmoMyMovimiento(&mercurio);
	AlgoritmoMyMovimiento(&venus);
	AlgoritmoMyMovimiento(&tierra);
	AlgoritmoMyMovimiento(&luna);
	AlgoritmoMyMovimiento(&iss);
	AlgoritmoMyMovimiento(&marte);
	AlgoritmoMyMovimiento(&jupiter);
	AlgoritmoMyMovimiento(&saturno);
	AlgoritmoMyMovimiento(&urano);
	AlgoritmoMyMovimiento(&neptuno);
	glutPostRedisplay();
	glutTimerFunc(MYTIEMPO, myMovimiento, 0);
}

void onMenu(int opcion) {
	switch (opcion) {
	case 1:
		camara = 1;
		break;
	case 2:
		camara = 2;
		break;
	case 3:
		camara = 3;
		break;
	case 4:
		camara = 4;
		break;
	case 5:
		camara = 5;
		break;
	case 6:
		camara = 6;
		break;
	case 7:
		camara = 7;
		break;
	case 8:
		camara = 8;
		break;
	case 9:
		camara = 9;
		break;
	case 10:
		camara = 10;
		break;
	case 11:
		camara = 11;
		break;
	}
	glutPostRedisplay();
}

void myMenu(void) {
	int menuFondo;
	menuFondo = glutCreateMenu(onMenu);
	glutAddMenuEntry("Voyayer", 1);
	glutAddMenuEntry("Sol", 2);
	glutAddMenuEntry("Mercurio", 3);
	glutAddMenuEntry("Marte", 4);
	glutAddMenuEntry("Venus", 5);
	glutAddMenuEntry("Luna", 6);
	glutAddMenuEntry("Iss", 7);
	glutAddMenuEntry("Jupiter", 8);
	glutAddMenuEntry("Saturno", 9);
	glutAddMenuEntry("Urano", 10);
	glutAddMenuEntry("Neptuno", 11);


	glutAttachMenu(GLUT_RIGHT_BUTTON);
}

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
		gluBuild2DMipmaps(GL_TEXTURE_2D, GL_RGB, w, h, GL_RGB, GL_UNSIGNED_BYTE, data);
	}
	else {
		textura = 0;
	}

	stbi_image_free(data);
	return textura;
}

void DescargarTexturas(){
	sol.textura= Carga_Texturas("sol.bmp");
	mercurio.textura =Carga_Texturas("mercurio.bmp");
	venus.textura= Carga_Texturas("venus.bmp");
	tierra.textura = Carga_Texturas("terra.bmp");
	luna.textura = Carga_Texturas("luna.bmp");
	iss.textura = Carga_Texturas("iss.bmp");
	marte.textura = Carga_Texturas("marte.bmp");
	jupiter.textura = Carga_Texturas("jupiter.bmp");
	saturno.textura = Carga_Texturas("saturno.bmp");
	urano.textura =Carga_Texturas("urano.bmp");
	neptuno.textura = Carga_Texturas("neptuno.bmp");
}

int main(int args, char** argv) {

	glutInit(&args, argv);
	glutInitWindowPosition(50, 50);
	glutInitWindowSize(W_WIDTH, W_HEIGHT);
	glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH);
	glutCreateWindow("SISTEMA SOLAR");

	//Activa el test de profundidad y oculta las caras internas
	glClearDepth(1.0f);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	//normaliza las normales
	glEnable(GL_NORMALIZE);

	glEnable(GL_TEXTURE_2D);
	glShadeModel(GL_SMOOTH);

	//Eventos
	glutKeyboardFunc(myTeclado);
	glutSpecialFunc(myTeclasespeciales);
	lista = myEsfera(); //llamar a todas las listas.render a myesferra
	
	sol.listarender = lista;
	mercurio.listarender = lista;
	venus.listarender = lista;
	tierra.listarender = lista;
	luna.listarender = lista;
	iss.listarender = lista;
	marte.listarender = lista;
	jupiter.listarender = lista;
	saturno.listarender = lista;
	urano.listarender = lista;
	neptuno.listarender = lista;
	glutReshapeFunc(changeSize);
	glutDisplayFunc(myDisplay);


	//Color en el que se limpian los buffer
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	glEnable(GL_NORMALIZE);

	//Definimos las iluminiaciones
	glLightfv(GL_LIGHT0, GL_AMBIENT, Ambiente);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, Difuso);
	glLightfv(GL_LIGHT0, GL_SPECULAR, Especular);
	glLightfv(GL_LIGHT0, GL_POSITION, PosicionLuz);
	glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, DireccionSpot);


	//Efectos de foco
	glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 179.0f);

	//Activamos las luces
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHTING);

	//Definimos el seguimiento del color como propiedad luminosa de los materiales
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	glShadeModel(GL_SMOOTH);

	//Definimos las propiedas de brillo metalico
	glMaterialfv(GL_FRONT, GL_SPECULAR, EspecularRef);
	glMateriali(GL_FRONT, GL_SHININESS, 1);

	//Activa las texturas y el modelo de sombreado suavizado
	glEnable(GL_TEXTURE_2D);
	glShadeModel(GL_SMOOTH);

	//Empieza el bucle principal
	DescargarTexturas();
	myMovimiento();
	myMenu();
	myCamara();
	//Carga de texturas e xeración de listas:
	glutMainLoop();
	return 0;
}
