#include <stdlib.h>
#include <stdio.h>

#include "errores.h"
#include "SistemaEntrada.h"
#include "TS.h"
#include "AnalizadorSintactico.h"
#include <ctype.h>

int main(int argc, char const *argv[]){
    FILE *fp;

    //Abrimos o ficheiro para leelo
    fp = fopen("regression.d", "r");

    //Se existe, cargarse o array e o sistema de entrada co codigo fonte correspondente
    //ademais, inicializase a taboa de simbolos e imprimese pola terminal para ver que esta todo correcto
    if(fp == NULL){
        PrintError(3,"LIÑA: 16, FICHEIRO: main.c");
        return 0;
    }else{
        cargar_array_no_codigofonte(fp);
        inicializarTaboa();
        printTaboa();
    }
    
    //Empezamos a pedir compoñentes lexicos ata que se chegue a fin de ficheiro
    chamadaSintactica();
    printf("---------------------------------------------\n");
    //Unha vez que se remate de leer todo o contido do ficheiro, destruese a taboa de simbolos
    //e cerrase o ficheiro regression
    destruirTaboa();
    fclose(fp);
    return 0;
}