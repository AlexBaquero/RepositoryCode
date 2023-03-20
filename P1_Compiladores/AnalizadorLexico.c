#include <stdlib.h>

#include <stdio.h>

#include "abb.h"
#include "SistemaEntrada.h"
#include "errores.h"
#include "TS.h"

//AUTOMATA PARA OS OPERADORES DOBRES.
//Soluciona os operadores existente no regression.d: ==,+=,++
void automata_operadores_dobles(tipoelem *e, char estado){
    char estado_recorrido;
    estado_recorrido = seguinte_caracter();

    switch (estado){
    case '=':
            //CASO: ==
            if(estado_recorrido == '='){
                e->compLexico = DOS_IGUALES_SEGUIDOS;
                e->lexema = estado_de_aceptacion();    
            }else{
                //Simplemente é un =
                retrasar_posicion();
                e->compLexico = '=';
                e->lexema = estado_de_aceptacion();   
            }      
        break;
    case '+':
        switch (estado_recorrido){
        case '=':
        //CASO: +=
            e->compLexico = SUMA_Y_IGUAL;
            e->lexema = estado_de_aceptacion(); 
            break;
        //CASO: ++
        case '+':
            e->compLexico = SUMA_Y_SUMA;
            e->lexema = estado_de_aceptacion();
            break;
        default:
        //Simplemente estamos lendo un +
            retrasar_posicion();
            e->compLexico = '+';
            e->lexema = estado_de_aceptacion(); 
            break;
        }
        break;
    }     
}

//AUTOMATA PARA OS NUMEROS CON PUNTO FLOTANTE
void subautomata_punto_flotante(tipoelem *elemento, char estado,char estado_recorrido){

    //Para solucionar o caso: 8_000
    while (estado_recorrido == '_'){
        estado_recorrido = seguinte_caracter();
    }
    
    estado_recorrido = seguinte_caracter();

    //vas recorrendo letras ata que encontras o e,+,- e devolves o estado
    while (estado_recorrido >= 48 && estado_recorrido <= 57){
        estado_recorrido = seguinte_caracter();
    }
    
    if (estado_recorrido == 'e' || estado_recorrido == 'E'){
        estado_recorrido = seguinte_caracter();

        if (estado_recorrido == '+' || estado_recorrido == '-'){
            estado_recorrido = seguinte_caracter();
        }

        if (estado_recorrido >= 48 && estado_recorrido <= 57){
            while (estado_recorrido >= 48 && estado_recorrido <= 57){
                estado_recorrido = seguinte_caracter();
            }
        }else{
            PrintError(2,"LIÑA: 70, FICHEIRO: AnalizadorLexico.c");
        }
    }


    retrasar_posicion();
    elemento->compLexico = FLOTANTES;
    elemento->lexema = estado_de_aceptacion();
}

//AUTOMATA PARA NUMEROS BINARIOS
void automata_numero_binario(tipoelem *elemento, char estado,char estado_recorrido){
    estado_recorrido = seguinte_caracter();

    //Vaise recorrendo 0 e 1 ata que xa remate o numero binario e devolves
    //o estado de aceptacion
    while (estado_recorrido == '1' || 
    estado_recorrido == '0'){;
        estado_recorrido = seguinte_caracter();
    }

    retrasar_posicion();
    elemento->compLexico = ENTEIROS ;
    elemento->lexema = estado_de_aceptacion();
}

//AUTOMATA PARA CADA TIPO DE NÚMEROS
void automata_numeros_nobinarios_o_binarios(tipoelem *elemento, char estado){
    char estado_recorrido;
    estado_recorrido = seguinte_caracter(); 

//-----------1.CASO: LEER UN PUNTO-------------------------------------------
      if (estado == '.'){
            retrasar_posicion();
            elemento->lexema = estado_de_aceptacion();
            elemento->compLexico = estado;
//-----------2.CASO: LEER UN 0-9------------------------------------------------
     }else if (estado >= 48 && estado <= 57){
//-----------2.2 CASO: LEER UN BINARIO------------------------------------------------
            if((estado == 48) && (estado_recorrido == 'b' || estado_recorrido == 'B')){
                automata_numero_binario(elemento,estado,estado_recorrido);
            }else if(estado_recorrido == '.' || estado_recorrido == '_'){
//-----------2.3 CASO: LEER UN DECIMAL/FLOTANTE CON UN DIXITO AO PRINCIPIO----------------------------
                subautomata_punto_flotante(elemento,estado,estado_recorrido);
//-----------2.4 CASO: LEER UN ENTERO-------------------------------------------
            }else if(estado >= 48 && estado <= 57){
//-----------2.4.1 CASO: LEER UN PUNTO FLOTANTE CON VARIOS DIXITOS ANTES DO e-----               
                if(estado_recorrido >= 48 && estado_recorrido <= 57){
                    while (estado_recorrido >= 48 && estado_recorrido <= 57){
                        estado_recorrido = seguinte_caracter();
                  }

                if (estado_recorrido == 'e' || estado_recorrido == 'E'){
                        estado_recorrido = seguinte_caracter();
                        if (estado_recorrido == '+' || estado_recorrido == '-'){
                            estado_recorrido = seguinte_caracter();
                        }

                        if (estado_recorrido >= 48 && estado_recorrido <= 57){
                            while (estado_recorrido >= 48 && estado_recorrido <= 57){
                                estado_recorrido = seguinte_caracter();
                            }
                        }else{
                            PrintError(2,"LIÑA: 132, FICHEIRO: AnalizadorLexico.c");
                        }
                    }
                    retrasar_posicion();
                    elemento->compLexico = FLOTANTES;
                    elemento->lexema = estado_de_aceptacion();
//-----------2.4.2 CASO: LEER UN ENTEIRO SIMPLEMENTE--
                }else{
                    retrasar_posicion();
                    elemento->lexema = estado_de_aceptacion();
                    elemento->compLexico = ENTEIROS;
                }
        }
    }
}

//AUTOMATA PARA AS CADENAS ALFANUMERICAS
void automata_cadenas_alfanumericas(tipoelem *elemento, char estado){
    char estado_recorrido;
    estado_recorrido = seguinte_caracter();
    
    //unha vez que vemos que é unha cadena alfanumericas vamos recorrendo
    //todas as letras 
    while 
    ((estado_recorrido  >= 65 && estado_recorrido  <= 90) || 
    (estado_recorrido  >= 97 && estado_recorrido  <= 122) ||
     (estado_recorrido  >= 48 && estado_recorrido  <= 57) || estado_recorrido  == 95)
    {
        estado_recorrido = seguinte_caracter();  
    }
    //ata que chegamos a outro tipo de automata enton devolvemos o estado
    //de aceptacion

    retrasar_posicion();
    elemento->lexema = estado_de_aceptacion();
    elemento->compLexico = buscarTaboaSimbolos(elemento->lexema);
}

//AUTOMATA PARA OS STRINGS
void automata_strings(tipoelem *e, char estado){
    char estado_recorrido;
    estado_recorrido = seguinte_caracter();

    //Unha vez que entramos no string, vamos recorrendo os caracteres...
    while( estado_recorrido != EOF){
        

        //Solucion para casos non que hai dentro do string /" e confundao co fin
        //do string
        if (estado_recorrido == '\\'){
            estado_recorrido = seguinte_caracter();

            if (estado_recorrido == '"'){
                estado_recorrido = seguinte_caracter();
                continue;
            }
        }

        //Chegouse ao final do string e devolves o string
        if(estado_recorrido == '"'){
            e->compLexico = STRINGS;
            e->lexema = estado_de_aceptacion();
            break;
        }
        estado_recorrido = seguinte_caracter();
    }
}

//Dependendo do caso que se lle adxudicou anteriormente, chama a un autoamta
void automata_finito_determinista(tipoelem *e, char estado,int caso){
    switch (caso){
        case 0:
        // + - ....
            e->compLexico = estado;
            e->lexema = estado_de_aceptacion();
            ignorar_caracter();
            break;
        case 1:
        //Numeros
            automata_numeros_nobinarios_o_binarios(e,estado);
            break;
        case 2:
        //Cadenas alfanumericas
            automata_cadenas_alfanumericas(e,estado);
            break;
        case 3:
        //Strings
            automata_strings(e,estado);
            break;
        case 4:
        //Operadores dobles: += ++...
            automata_operadores_dobles(e,estado);
            break;
        default:
            PrintError(5,"LIÑA: 221, FICHEIRO: AnalizadorLexico.c");
            break;
        }
}

//FUNCIÓN QUE UTILIZA O ANALIZADOR SINTÁCTICO PARA SOLICITAR OS COMPOÑENTES
//LÉXICOS NECESARIOS PARA IMPRIMIR POR PANTALLA
tipoelem *seguinte_componente_lexico(){
    tipoelem *elemento;
    elemento = malloc(sizeof(tipoelem));
    char c;
    int caso=0;

    c = seguinte_caracter();
 
 //Antes de chamar aos automatas, comprobamos de que non estamos leendo un comnetario
 //------------COMENTARIOS--------
 while (c == 32 ||
        c == EOF || 
        c == '/' || 
        c == '\n' ){
            
            switch(c) {
                //CASO: ESPACIO EN BLANCO
                case 32: 
                    ignorar_caracter();
                    c = seguinte_caracter();
                break; 
                case EOF:
                //CASO: FIN ARQUIVO
                    return 0;
                break; 
                case '/':
                //CASO: /
                    c = seguinte_caracter();
                    switch (c){
                        //CASO: /*
                        case '*':
                            while (c != EOF){
                                c = seguinte_caracter(); ignorar_caracter();
                                    if (c == '*'){
                                        c = seguinte_caracter(); ignorar_caracter();              
                                        if (c == '/'){
                                            c = seguinte_caracter(); ignorar_caracter();
                                    
                                            break;              
                                        }
                                }
                            }
                            break;
                        //CASO: /+
                        case '+':
                            // /+
                            while (c != EOF){
                                c = seguinte_caracter(); ignorar_caracter();

                                //Se entramos noutro /+, buscamos o +/ que cerra o comentario
                                //antes de continuar buscando o primer /+
                                if( c == '/'){
                                    c = seguinte_caracter(); ignorar_caracter();
                                    if( c == '+'){
                                        c = seguinte_caracter(); ignorar_caracter();
                                        while(c != EOF){
                                             c = seguinte_caracter(); ignorar_caracter();
                                             if (c == '+'){
                                                c = seguinte_caracter(); ignorar_caracter();              
                                                if (c == '/'){
                                                    c = seguinte_caracter(); ignorar_caracter();    
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                //buscase +/ que cerra o comentario
                                }else if (c == '+'){
                                    c = seguinte_caracter(); ignorar_caracter();              
                                    if (c == '/'){
                                        ignorar_caracter();c = seguinte_caracter(); 
                                        break;              
                                    }
                                }
                            }
                            break;
                        case '/':
                        //CASO: //
                            while (c != '\n'){
                                c = seguinte_caracter(); ignorar_caracter();
                                
                            }
                            break;
                        default:
                        //CASO: / 
                            c = '/';
                            retrasar_posicion();
                            elemento->compLexico = c;
                            elemento->lexema = estado_de_aceptacion();
                            ignorar_caracter();
                            return elemento;
                            break;
                        }
                    break; 
                case '\n':
                //CASO: SALTO LINEA
                    ignorar_caracter();
                    c = seguinte_caracter();
                    break;
               
        }
    }

//----------------------- FIN COMENTARIOS ---------------------


//Vemos que tipo de caracter es para adjudicarlle un caso para logo manexalo
//no automata finito determinista....

    if ((c >= 48 && c <= 57) || c == '.'){
        //CASO: numeros binarios o no binarios
        caso=1;
    }else if(((c >=65 && c <=90) || (c >= 97 && c <= 122)) || 
    c == '_'){
        //CASO: cadenas alfanumericas
        caso=2;
    }else if(c == '"'){
        //CASO: strings
        caso=3;
    }else if(c == 61 || c == 43){
        //CASO Operadores dobres: ==,+=,++
        caso=4;
    }else{
        //casos como ), +, - etc...
        caso=0;
    }

    //Chamamos ao autoamta finito determinista que manexará os diferentes
    //automatas
    automata_finito_determinista(elemento,c,caso);
    return elemento;
}