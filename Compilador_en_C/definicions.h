#define IMPORT 301
#define DOUBLE 302
#define INT 303
#define WHILE 304
#define FOREACH 305
#define RETURN 306
#define VOID 307
#define CAST 308
#define ENTEIROS 309
#define FLOTANTES 310
#define STRINGS 311
#define DOS_IGUALES_SEGUIDOS 312
#define SUMA_Y_IGUAL 313
#define SUMA_Y_SUMA 314
//Fin PalabrasReservadas

#define IDENTIFICADOR 316

// Estructura da aŕbore na taboa de simbolos
typedef struct{
	int compLexico;
	char *lexema;
} tipoelem;

//Tamaño maximo do lexema
#define T_MAX 32
