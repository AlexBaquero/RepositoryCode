#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "errores.h"

//Funcion para manexar os errores por pantalla.
//Imprime o tipo e error e a liña e o ficheiro onde se encontra o error
void PrintError(int tipoerror, char *lina)
{
    switch (tipoerror)
    {
    case 2:
        printf("-------------------------------------------------\n");
        printf("ERROR: Existe algún número que non ten expoñente\n");
        printf("(%s)\n", lina);
        printf("-------------------------------------------------\n");
        break;
    case 3:
        printf("--------------------------------------------------------------\n");
        printf("ERROR: O ficheiro do codigo fonte non foi leido correctamente\n");
        printf("(%s)\n", lina);
        printf("--------------------------------------------------------------\n");
        break;
    case 4:
        printf("----------------------------------------------------------------------\n");
        printf("ERROR: O lexema que se está lendo sobrepasa o tamaño máximo do lexema\n");
        printf("(%s)\n", lina);
        printf("----------------------------------------------------------------------\n");
        break;
    case 5:
        printf("---------------------------------\n");
        printf("ERROR: Non se recoñece o automata\n");
        printf("(%s)\n", lina);
        printf("----------------------------------\n");
    default:
        printf("------------------------------------\n");
        printf("|ERROR: Tipo de error non recoñecido| \n");
        printf("------------------------------------\n");
        break;
    }
}
