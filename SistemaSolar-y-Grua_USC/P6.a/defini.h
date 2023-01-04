typedef struct {
	float x;
	float y;
	float z;
} punto;

typedef struct {
	float x;
	float y;
	float z;
}vector;


//objeto generico que tiene velocidad, acelracion, lineal y angular
typedef struct {
	float distancia;
	float velocidad_trans;
	float angulo_trans;
	float velocidad_rot;
	float angulo_rot;
	int tamano;
	int listarender;
	float c1;
	float c2;
	float c3;
	int textura;
	boolean satelite;
} objeto;


#define CR 0.0175
#define MYTIEMPO 41

void myCamara();
void myTeclado(unsigned char tras, int x, int y);
void myTeclasespeciales(int cursor, int x, int y);
void myEjes();
void myTelescopio(float distancia, float angulo, float distancia_obj, float angulo_obj);
void myTelescopioConSatelite(float distancia, float angulo, float distancia_obj, float angulo_obj);



