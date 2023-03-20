#include <stdlib.h>
#include <stdio.h>

#include "abb.h"
#include "SistemaEntrada.h"
#include "errores.h"

FILE *regression;

//arrays 
char A[T_MAX + 1], B[T_MAX + 1];
//punteiros para determinar o inicio e o fin dos arrays
char *p_inicio = A, *p_fin = A;
//comprobador da lonxitude do lexema e asi que non se pase do tamaño maximo definido
//por T_MAX
int comprobador_lonxitude = 0;


//Funcion para calcular  a distancia que hai entre o punteiro fin e inicio
//e asi ver que espazo hai
int calcular_espacio_punteiros(){
    int t=0;
    if( p_fin >=A && p_fin <= ( A + T_MAX ) ){

        if( p_inicio >=  A &&  p_inicio  <= ( A+T_MAX )){

            t =p_fin-p_inicio;
        }else{
            t =( p_fin - A ) + ( ( B + T_MAX ) - p_inicio);
        }

    }else{

        if( p_inicio >=  B &&  p_inicio <= ( B + T_MAX )){
            t = p_fin-p_inicio;
        }else{
            t = ( p_fin-B) + ((A +T_MAX) -p_inicio);
        }
    }
    return t;
}

char *estado_de_aceptacion(){
    char *estado_final;
    int tamano_estado = calcular_espacio_punteiros();
    //printf(" tamaño: %d \n", tamano_estado);
    estado_final = malloc( tamano_estado + 1 );
     *(estado_final + tamano_estado) = '\0';
     

    int contador_caracteres=0;
    comprobador_lonxitude=0;
    //poñemos o contador da lonxitude a 0 pois vamos a devolver un estado
    //de aceptacion ao analizador lexico...

    while (p_inicio != p_fin){
        
        if (*p_inicio == EOF){

            if(p_inicio ==(B + T_MAX ) )
            {
                p_inicio = A;
            }else if (p_inicio == (A + T_MAX)){
                p_inicio = B;
            }
            
        }

        //Caso para solucionar que o punteiro fin esta na primeira posicion
        if(p_inicio == p_fin) {
            break;
        }
        *(estado_final + contador_caracteres) = *p_inicio;
        contador_caracteres++;
        p_inicio += 1;
    }
    return estado_final;        
}

//Funcion para ignorar un caracter
//Tipo de uso: espacios, comentarios etc....
void ignorar_caracter(){
    comprobador_lonxitude=0;
    //leva o inicio a posicion do fin para comenzar de novo
    p_inicio = p_fin;
}

//Funcion para darlle para atras a posicion do punteiro do fin
void retrasar_posicion(){
    p_fin--;
}


//Cargamos o array A ou B para empezar a enchelo
void cargar_array(char *array){
    if(p_inicio <= (array+T_MAX) && p_inicio>=array){
        if(array == B){
            p_inicio = A;
        }else if (array == A){
            if(p_fin <= (B+T_MAX) && p_fin >= B){
                p_inicio = B;
            }
        }
    }

    //o cargar o bloque, queda o punteiro apuntando ao seguinte carácter. 
    //De esta maneira, vou lendo o código fonte avanzando bloque a bloque.

    if (!feof(regression)){
        //ir leendo de tamaño 1 en 1
        int array_recorrido = fread(array, 1, T_MAX, regression);
        //o fread non mete o EOF asi que o metemos non a man
        if(array_recorrido < T_MAX){
            *(array + array_recorrido) = EOF;
        }
    }
}

//Funcion que sirve para pasarlle caracter a caracter ao Analizador Lexico para
//que el forme o compoñente lexico e o pase ao Analizador Sintactico
char seguinte_caracter(){
    char c;
    c = *p_fin;
    if (comprobador_lonxitude != T_MAX){
        if(c != EOF){
            p_fin++;
        }else{
          if(p_fin == (A+T_MAX)){
            cargar_array(B);
            c = *B;
            p_fin = B +1;
          }else if (p_fin == (B+T_MAX)){
            cargar_array(A);
            c = *A;
            p_fin = A +1;
          }else{
           c = EOF;
           p_fin++;
          }
        }
    }else{    
        PrintError(4,"LIÑA: 121 | FICHEIRO: SistemaEntrada.c");
    }

    comprobador_lonxitude++;
    return c;
}


//Ao principio do main.c, cargarse o primer array A e gardase o ficheiro que
//estamos leendo
void cargar_array_no_codigofonte(FILE *ficheiro){
    *(A + T_MAX)= EOF; *(B + T_MAX) = EOF;
    regression = ficheiro;
    cargar_array(A);
}