#include <stdio.h>
#include <stdlib.h>
#include <time.h> //para utilizar la semilla aleatoria time(NULL)
#include "FuncionesAmongETSE.h"

int main(int argc, char** argv) {
    
    abb arbol;
    
    srand((unsigned int) time(NULL)); //semilla para aleatorios, se llamar sólo una vez al principio de main
    char opcion;
    
    //Crear el árbol de jugadores
    crear(&arbol);
    
    //Leer el archivo de disco
    leerArchivo(&arbol);
    
    //Menú
    do{
        printf("\nBienvenid@ a AmongETSE: \n");
        printf(" a. Alta de jugador@ \n b. Baja de jugador@ \n l. Listado de jugador@s por orden alfabético \n");
        printf(" g. Generar datos partida \n u. Consulta por jugador@ de la próxima tarea a realizar \n h. Consulta jugador@s por habitación \n f. Guardar Archivo \n s. Salir de la aplicación \n");

        printf("\n Opcion escogida: ");
        scanf(" %c", &opcion);
        printf("\n");
        switch(opcion){
            case 'a':
                altaJugador(&arbol);
                break;    
            case 'b':
                bajaJugador(&arbol);
                break;
            case 'l':
                listadoJugadores(arbol);
                break;
            case 'g':
                generarPartida(&arbol);
                break;
            case 'u':
                consultarJugador(arbol);
                break;
            case 'h':
                consultarPorHabitacion(arbol);
                break;
            case 'f':
                guardarEnArchivo(arbol);
                printf("La lista de usuarios de la aplicación actualizada ha sido guardada en el archivo\n");
                break;
            case 's':
                printf("Saliendo del programa...");
                exit(EXIT_FAILURE);
                break;
        }
    }while(opcion != 's');
    
    //Destruir el árbol al finalizar
    destruir(&arbol);
    return (EXIT_SUCCESS);
}

