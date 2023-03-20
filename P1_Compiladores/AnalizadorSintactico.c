#include <stdio.h>
#include <stdlib.h>

#include "abb.h"
#include "AnalizadorLexico.h"
#include "errores.h"

//Vai pedindo ao analizador lexico os compoñentes para imprimilo no ficheiro e na
//terminal
void chamadaSintactica(){
    tipoelem *elemento;
    printf("--------IMPRIMIR COMPOÑENTES LÉXICOS---------\n");
    elemento = seguinte_componente_lexico();

    //Imprimese no ficheiro os resultados (os errores non)
    FILE *fp_write;
    fp_write = fopen("resultados.txt", "w");
    if(fp_write == NULL){
        PrintError(3,"LIÑA: 17, FICHEIRO: AnalizadorSintactico.c");
        exit(0);
    }else{
        fprintf(fp_write, "-----------RESULTADOS COMPOÑENTES LÉXICOS--------\n");
        fprintf(fp_write, "-------------<Compoñente Léxico,Lexema>----------\n");
        fprintf(fp_write, "(Os errores imprímense por terminal,non neste arquivo)\n\n");
    }

    //ata que non se chegue ao fin do ficheiro vai pedindo compoñentes lexicos
    //(o analizador lexico devolve un 0 cando chega ao fin de ficheiro)
    while(elemento != 0){

        //vai imprimindose no ficheiro e na terminal...
        printf("<%d,",elemento->compLexico);
        printf("%s> \n", elemento->lexema);

        fprintf(fp_write, "<%d,",elemento->compLexico);
        fprintf(fp_write, "%s> \n", elemento->lexema);
        
        free(elemento->lexema);
        free(elemento);

        elemento = seguinte_componente_lexico();
    }
    
    fprintf(fp_write, "------------------------------------------------\n");
    fclose(fp_write);

}