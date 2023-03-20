#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "abb.h"
#include <ctype.h>

abb TS; 


//Función que co lexema que se lle da, comproba que este
//está na taboa de simbolos (inicializada ao principio do programa)
int buscarTaboaSimbolos(char *lex){
  tipoelem elemento;
  elemento.compLexico = 0;

  //Inicializase o compoñente lexico a 0.
  //Se despois se buscar na táboa de simbolos, non cambiou o int es que ese 
  //lexema non está na táboa de simbolos.
  buscar_nodo(TS,lex,&elemento); 
  //Se non está na TS, insértase na táboa e daslle o número de ID da TS. 
  if(elemento.compLexico == 0){
    elemento.compLexico = IDENTIFICADOR;
    elemento.lexema = malloc(strlen(lex)+1);
    strcpy(elemento.lexema,lex);
    insertar(&TS, elemento);
  }
  return elemento.compLexico;
}

//Imprimir Taboa ao estilo dos árbores binarios
void printTaboaAux(abb TS) {
  tipoelem elemento;

	if(!es_vacio(TS)){
	  printTaboaAux(der(TS));
	  leer(TS,&elemento);
    printf("<%d,%s>\n", elemento.compLexico, elemento.lexema);
	  printTaboaAux(izq(TS));
	}
}

//Función para inicializar a taboa de simbolos
void inicializarTaboa(){
   char str1[50], str2[50];
   int componentelexico;
   tipoelem elemento;
   FILE * fp;

   //Primeiro creamos a táboa coa función crear da libreria de arbores binarios
   crear(&TS);
   //Abrimos o definicións.h onde temos todas os operadores e palabras reservadas
   fp = fopen ("definicions.h", "r");

//Ata que encontramos o comentario //fin de palabras reservas, 
//vamos leendo todas as palabras reservadas,pasando a minusculas e insertando
// no dato TS (arbore binario)
    while (strcmp(str2,"PalabrasReservadas") != 0){
      fscanf(fp, "%s %s %d", str1, str2, &componentelexico);

      if(strcmp(str2,"PalabrasReservadas") != 0){
        for (int indice = 0; str2[indice] != '\0'; ++indice){
          str2[indice] = tolower(str2[indice]);
    	  }
        elemento.lexema = (char *)malloc(strlen(str2)+90);
        strcpy(elemento.lexema, str2);
        elemento.compLexico = componentelexico;

        //printf("|%d", elemento.compLexico );
        //printf(",%s|\n", elemento.lexema );        
        insertar(&TS, elemento);
      }
  }
  printf("\n<INICIALIZANDO TÁBOA DE SÍMBOLOS....>\n");
  //Cerramos o ficheiro definicions.h
  fclose(fp);
}


void printTaboa(){
    printf("----------------IMPRIMIR TABOA---------------\n");
    printTaboaAux(TS);
    printf("---------------------------------------------\n");
}

//Usase a libreria dos arbores binarios para destruir a TS
void destruirTaboa(){
    printf("<DESTRUÍNDO TÁBOA DE SÍMBOLOS....>\n\n");
    destruir(&TS);
}
