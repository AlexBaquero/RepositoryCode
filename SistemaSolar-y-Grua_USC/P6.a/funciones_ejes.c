#include <windows.h>	//Inclusion de variables de windows

#include <glut.h>	//Inclusion de GLUT
#include <gl.h>		//Inclusion de GL		 	
#include <glu.h>	
#include <stdio.h>
#include <math.h>	//Inclusion de librerias auxiliares	
#include "P6.a/defini.h"

#define INCREMENTO .03
#define DISTANCIA 7500
#define PI 3.1416

extern int orbitas;
extern W_WIDTH;
extern W_HEIGHT;

float alpha = 0;
float beta = 0;

void myCamara() {


	//Configuración de la matriz de proyeccion
	glMatrixMode(GL_PROJECTION);
	//La ponemos auno
	glLoadIdentity();
	//glOrtho(-3500.0,3500.0f,-3500.0,3500.0f,-3500.0,3500.0f);
	gluPerspective(50, (float)W_WIDTH / W_HEIGHT, 1, 15000);

	//gluPerspective(FOVY, (float)W_WIDTH / (float)W_HEIGHT, 1, DISTANCIA*3);
	gluLookAt(0, ((float)DISTANCIA * (float) sin(beta)), ((float)DISTANCIA * cos(alpha) * cos(beta)), 0, 0, 0, 0, cos(beta), 0);


}


void myTeclado(unsigned char tras, int x, int y)
{
	switch (tras) {
	case 'l':
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		break;
	case 'c':
		glDisable(GL_CULL_FACE);
		break;
	case 'k':
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		break;
	case 'x':
		glEnable(GL_CULL_FACE);
		break;
	case 'o':
		orbitas = !orbitas;
		break;
	default:
		break;
	}
	// Se se modificou algo redebúxase
	glutPostRedisplay();
}

void myTeclasespeciales(int cursor, int x, int y)
{
	switch (cursor)
	{
		//Traslaciones:
	case GLUT_KEY_F1:
		break;
	case GLUT_KEY_F2:
		break;
	case GLUT_KEY_F3:
		break;
	case GLUT_KEY_F4:
		break;
	case GLUT_KEY_F5:
		break;
	case GLUT_KEY_F6:
		break;

		//Giros:
	case GLUT_KEY_UP:
		beta += INCREMENTO;
		break;
	case GLUT_KEY_DOWN:
		beta -= INCREMENTO;
		break;
	case GLUT_KEY_RIGHT:
		alpha -= INCREMENTO;
		break;
	case GLUT_KEY_LEFT:
		alpha += INCREMENTO;
		break;
	default:
		break;
	}

	if (alpha > PI * 2.0)
		alpha = 0;
	if (alpha < 0)
		alpha = PI * 2.0;
	if (beta > PI * 2.0)
		beta = 0;
	if (beta < 0)
		beta = PI * 2.0;


	glutPostRedisplay();
}

 void myEjes(void) {
	 // Creamos el eje X
	 glBegin(GL_LINES);
	 glColor3f(0.0f, 0.0f, 1.0f);
	 glVertex3f(0.0f, 0.0f, 0.0f);
	 glVertex3f(50.0f, 0.0f, 0.0f);
	 glEnd();

	 // Creamos el eje Y
	 glBegin(GL_LINES);
	 glColor3f(1.0f, 0.0f, 0.0f);
	 glVertex3f(0.0f, 0.0f, 0.0f);
	 glVertex3f(0.0f, 50.0f, 0.0f);
	 glEnd();

	 // Creamos el eje Z
	 glBegin(GL_LINES);
	 glColor3f(0.0f, 1.0f, 0.0f);
	 glVertex3f(0.0f, 0.0f, 0.0f);
	 glVertex3f(0.0f, 0.0f, 50.0f);
	 glEnd();

	 // Creamos la normal 
	 glBegin(GL_LINES);
	 glColor3f(1.0f, 1.0f, 1.0f);
	 glVertex3f(0.0f, 0.0f, 0.0f);
	 glVertex3f(30.0f, 30.0f, 30.0f);
	 glEnd();
 }

 void myTelescopio(float distancia, float angulo, float distancia_obj, float angulo_obj) {
	 //configuracion de la matriz de proyeccion
	 glMatrixMode(GL_PROJECTION);
	 //la ponemos a uno
	 glLoadIdentity();

	 gluPerspective(45.0, (float)W_WIDTH / W_HEIGHT, 1.0, 5000);
	// gluPerspective(65.0, 1, 1.0, 5000.0);
	 gluLookAt(distancia * cos(angulo * PI / 180.0), 0, -1 * distancia * sin(angulo * PI / 180.0), distancia_obj * cos(angulo_obj * PI / 180), 0, -1 * distancia_obj * sin(angulo_obj * PI / 180.0), 0, 1, 0);


 }

 void myTelescopioConSatelite(float distancia, float angulo, float distancia_obj, float angulo_obj) {

	 //Configuracion de la matriz de proyeccion
	 glMatrixMode(GL_PROJECTION);

	 //La ponemos a uno
	 glLoadIdentity();

	 gluPerspective(65.0, 1, 1.0, 5000.0);
	 gluLookAt(distancia * cos(angulo * PI / 180.0), 0, -1 * distancia * sin(angulo * PI / 180.0), distancia_obj * cos((angulo_obj + angulo) * PI / 180) + distancia * cos(angulo * PI / 180.0), 0, -1 * distancia_obj * sin((angulo_obj + angulo) * PI / 180.0) + (-1 * distancia * sin(angulo * PI / 180.0)), 0, 1, 0);

 }

