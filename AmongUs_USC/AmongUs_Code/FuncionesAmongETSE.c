#include <stdio.h>
#include <stdlib.h> //necesaria para rand()
#include <string.h> //para comparar cadenas
#include <math.h> //para calcular el número de impostores con round()
#include "abb.h"
#include "FuncionesAmongETSE.h"
#include "cola.h"
#define T 5

//***************FUNCIONES PRIVADAS***********
//Función privada que genera un número aleatorio entre inf y sup
unsigned int _aleatorio(int inf, int sup) {
    return (rand() % (sup - inf + 1)) +inf;
}

/* Función auxiliar para contar nodos. Función recursiva de recorrido en
   preorden, el proceso es aumentar el contador */
void _auxContador(abb nodo, int *c) {
    if (!es_vacio(nodo)){
	(*c)++; /* Otro nodo */
    	/* Continuar recorrido */
    	_auxContador(izq(nodo), c);
    	_auxContador(der(nodo), c);
    }
}


//Función privada que calcula el número de nodos de un árbol A
int _NumeroNodos(abb A) {
    int contador = 0;
    _auxContador(A, &contador); /* Función auxiliar privada */
    return contador;
}

//Función privada para inicializar los datos de un jugador, necesaria en varias funciones públicas
void _inicializarJugador(tipoelem *registro){
    //inicializa los campos rol,descripcionTarea y lugarTarea    
    registro->rol= ' ';
    crear_cola(&registro->tareas);
}

//Funcion privada que imprime las tareas de los jugadores
void _imprimirTareas(cola C){
    cola Caux;
    tipoelemCola p;
    
    crear_cola(&Caux);
    //hacemos un bucle cogiendo el primero de la cola y vaciarndola en una cola auxiliar
    //después volvemos a introducir los elementos para que queden en el mismo orden.
    if(!es_vacia_cola(C)){
        while(!es_vacia_cola(C)){
            p=primero(C);
            printf(" |%s", p.descripcionTarea);
            printf("|en %s|", p.lugarTarea);
            insertar_cola(&Caux,p);
            suprimir_cola(&C);
        }
        while(!es_vacia_cola(Caux)){
            p=primero(Caux);
            insertar_cola(&C,p);
            suprimir_cola(&Caux);
        }
    }else{
        printf("Este usuario no tiene ninguna tarea asignada \n");
    }
}

//Función privada que imprime los datos de un jugador
void _imprimirJugador(tipoelem E) {
    //imprimos los datos del jugador al estilo del ejemplo que se colgo en la aula virtual
    printf("%s        ", E.nombreJugador);
    printf("Rol: %c", E.rol); 
    //llamamos a la funcion imprimir cola tareas
    _imprimirTareas(E.tareas);
}

//Función privada para limpiar los datos de partida
void _limpiarDatos(abb A){
    tipoelem E;
    if(!es_vacio(A)){
        //hacemos una funcion baseandonos en la funcion inorden de las anteriores practicas, pero en
        //vez de imprimir pues inicilizamos los jugadores
        leer(A,&E);_inicializarJugador(&E);
        _limpiarDatos(izq(A));
	_limpiarDatos(der(A));
	}	
}

//Funcion privada para seleccionar los jugadores del arbol
int _seleccionarJugador(abb Arbol, unsigned int nAl, int *j, tipoelem *registro){
    
    //si el arbol no esta  vacio...
    if (!es_vacio(Arbol)){
        *j = *j + 1;
        
        if (nAl == *j){ //si el numero aleatorio introducido en generar partida es igual al contador
            leer(Arbol, registro); //leemos el jugador del arbol
            if (registro->rol != ' ') {
                //devolvemos un 0 si no hay rol
                return 0;
            }else{
                //devolvemos un 1 si hay rol
                return 1;
            }
            //inorden:
        }else if (_seleccionarJugador(izq(Arbol),nAl,j,registro)){
            return 1;
        }
        return _seleccionarJugador(der(Arbol),nAl,j,registro);
    }
    return 0;
}

int _seleccionarImpostores(abb Arbol, unsigned int nAl, int *j, tipoelem *registro) {
    //si el arbol no está vacio..
    if (!es_vacio(Arbol)) {
        
        leer(Arbol, registro); //leemos el jugador del arbol
        
        if (registro->rol == 'T') { //si es un tripulante, pues aumentamos el contador  
            *j = *j+1;
            
            if (*j == nAl) { //comprobamos que el numero aleatorio es iguak al contador y devolvemos uno
                return 1; 
            }
            //inorden
        }else if (_seleccionarImpostores(izq(Arbol),nAl,j,registro)){
            return 1;
        }
        return _seleccionarImpostores(der(Arbol),nAl,j,registro);
    }
    return 0;
}

//Funcion privada para asignar una tarea y sala aleatoriamente a un jugador
void _asignarTareaYSala(abb A,tipoelem *registro){
    
    int i=0;
    tipoelemCola tarea;
    
    //para realizar esta funcion, vemos la ultima pagina del pdf "AmongETSE_v1" donde se observa
    //la tarea y la habitacion que debe tener cada caso
    
    
    //meteremos las tareas(lugar y descripcion) en la cola 
    if (!es_vacio(A)){
        leer(A, registro);
        if (registro->rol == 'T' || registro->rol == 'I'){
            while (i!=5) {
                switch (_aleatorio(1, 8)) {
        case 1:
            strcpy(tarea.lugarTarea, "Motores");
            strcpy(tarea.descripcionTarea, "Alinear la salida del motor");
            insertar_cola(&registro->tareas, tarea);
            break;
        case 2:
            strcpy(tarea.lugarTarea, "Electricidad");
            strcpy(tarea.descripcionTarea, "Calibrar distribuidor");
            insertar_cola(&registro->tareas, tarea);
            break;
        case 3:
            strcpy(tarea.descripcionTarea,"Descargar datos/subir datos");
            switch(_aleatorio(1,5)){
                case 1:
                    strcpy(tarea.lugarTarea,"Cafeteria");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 2:
                    strcpy(tarea.lugarTarea,"Comunicaciones");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 3:
                    strcpy(tarea.lugarTarea,"Armeria");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 4:
                    strcpy(tarea.lugarTarea,"Electricidad");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 5:
                    strcpy(tarea.lugarTarea,"Navegación");
                    insertar_cola(&registro->tareas, tarea);
                    break;    
            }
            break;
        case 4:
            strcpy(tarea.descripcionTarea, "Desviar Energia");
            switch(_aleatorio(1,7)){
                case 1:
                    strcpy(tarea.lugarTarea,"Navegacion");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 2:
                    strcpy(tarea.lugarTarea,"02");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 3:
                    strcpy(tarea.lugarTarea,"Seguridad");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 4:
                    strcpy(tarea.lugarTarea,"Armeria");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 5:
                    strcpy(tarea.lugarTarea,"Comunicaciones");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 6:
                    strcpy(tarea.lugarTarea,"Escudos");
                    insertar_cola(&registro->tareas, tarea);
                    break;
                case 7:
                    strcpy(tarea.lugarTarea,"Motores");
                    insertar_cola(&registro->tareas, tarea);
                    break;       
            }
            break;
        case 5:
            strcpy(tarea.lugarTarea,"Escudos");
            strcpy(tarea.descripcionTarea, "Encender Escudos");
            insertar_cola(&registro->tareas, tarea);
            break;
        case 6:
            strcpy(tarea.lugarTarea,"Navegacion");
            strcpy(tarea.descripcionTarea,"Estabilizar direccion");
            insertar_cola(&registro->tareas, tarea);
            break;
        case 7:
            strcpy(tarea.lugarTarea,"02");
            strcpy(tarea.descripcionTarea, "Limpiar el filtro 02");
            insertar_cola(&registro->tareas, tarea);
            break;
        case 8:
            strcpy(tarea.lugarTarea,"Navegacion");
            strcpy(tarea.descripcionTarea,"Mapa de Navegacion");
            insertar_cola(&registro->tareas, tarea);
            break;
            }
        i++;
        }
    _imprimirJugador(*registro);
    printf("\n");
    modificar(A,*registro);
        }
    _asignarTareaYSala(izq(A),registro);
    _asignarTareaYSala(der(A),registro);
    }
}

//Funcion privada para encontrar la habitacion que necesitamos al consultar la habitacion con las tareas
void _encontrarLaHabitacion(abb A, char Habitacion[L]){
    tipoelem E;
    tipoelemCola p;
    
    //recorremos recursivamente postorden en arbol
    if(!es_vacio(A)){
        _encontrarLaHabitacion(izq(A),Habitacion);
        _encontrarLaHabitacion(der(A),Habitacion);
        leer(A,&E); 
        //si la sala insertada es igual a la que esta dentro de lugarTarea, imprimimos el primera tarea de la cola de la haba
        if(!es_vacia_cola(E.tareas)){
            p=primero(E.tareas);
            if(strcmp(p.lugarTarea,Habitacion) == 0){
                printf("%s", p.descripcionTarea);
            }
        }
    }
}

//Funcion recursiva postorden para ir leyendo el jugador del arbol y ir metiendolo en el archivo
void _guardarEnArchivo(abb A,FILE *fp){
    tipoelem E;

    if(fp){
        if(!es_vacio(A)){
            _guardarEnArchivo(izq(A),fp);
            _guardarEnArchivo(der(A),fp);
            leer(A,&E);
            fputs(E.nombreJugador,fp);
            fprintf(fp,"\n");
        }
    }
}

 // ***********FUNCIONES PUBLICAS*****************
//Función para leer el archivo de disco
void leerArchivo(abb *A) {
    tipoelem registro;
    FILE *fp;
    fp = fopen("ETSErsG3.txt", "rt");
    if (fp) {
        fscanf(fp, " %s", registro.nombreJugador);
        while (!feof(fp)) {
            _inicializarJugador(&registro);
            insertar(A, registro);
            fscanf(fp, " %s", registro.nombreJugador);
        }
        fclose(fp);
        listadoJugadores(*A);
    }
}

//Función que añade un jugador al árbol
void altaJugador(abb *Arbol){
    tipoelem registro;
    cola C;
    
    //solicitamos el nombre del jugador que se volvera a preguntar si no empieza por @
     do{
         printf("Nombre de Jugador(@nickname): ");
         scanf("%s", registro.nombreJugador);
     }while(registro.nombreJugador[0] != '@');
     
     //si pertenece al arbol donde se leyo el archivo, pues enviara un error por pantalla
     if(es_miembro(*Arbol, registro)){
         printf("ERROR: Este nickname ya esta siendo utilizado...\n");
     }else{
         //si no existe, incializa el jugador y lo mete en el archivo. Manda un mensaje por pantalla 
         //de que la accion se ha relizado con exito
         _inicializarJugador(&registro);
         insertar(Arbol,registro);
         printf("El jugador seleccionado ha sido dado de alta \n");
     }
}

//Función que elimina un jugador del árbol
void bajaJugador(abb *Arbol){
    tipoelem registro;
    
    //solicitamos el nombre del jugador que se volvera a preguntar si no empieza por @
    do{
         printf("Nombre de Jugador(@nickname): ");
         scanf("%s", registro.nombreJugador);
     }while(registro.nombreJugador[0] != '@');
    
     //si no pertenece al arbol donde se leyo el archivo, pues enviara un error por pantalla
    if(!es_miembro(*Arbol,registro)){
        printf("\nERROR: Este nickname no existe... \n");
    }else{
        //si existe, pues lo eliminara del arbol y mandará mensaje confirmando que se ha realizado con exito
        suprimir(Arbol, registro);
        printf("El jugador seleccionado ha sido dado de baja");
    }
}

//Función pública que imprime los nombres de los jugadores por orden alfabético
void listadoJugadores(abb A) {
    tipoelem E;
    //copiamos la funcion inorden de practicas anteriores:
    if(!es_vacio(A)){
        listadoJugadores(izq(A));
	leer(A,&E);_imprimirJugador(E);
	listadoJugadores(der(A));
	}	
}

//Función que genera los datos de una partida: jugadores, roles y tareas
void generarPartida(abb *Arbol){ 
    tipoelem registro;
    tipoelemCola p;
    tipoelem registroaux;
    
    unsigned int nAl=0;
    int nJugadores, nImp,nTrip, i, *j;
    
    
    //Antes de generar una partida nueva, limpiamos los datos establecidos en la ultima partida generada
    _limpiarDatos(*Arbol);

    //preguntamos al usuario cuantos jugadores quiere en esta partida 
    do{
        printf("En esta versión del programa, los participantes se seleccionarán aleatoriamente \n");
        printf("Numero de Jugadores(4-10) ");
        scanf("%d", &nJugadores);
        printf("\n");
    }while(nJugadores<4 || nJugadores>10);
    
    //reservamos memoria para el contador j:
    j = (int *) malloc(sizeof(int));
    //el numero de impostores se seleccionara de forma aleatoria usando "round"
    nImp = (int)round(nJugadores / 5.0);
    //el numero de tripulantes sera el resto de los jugadores que no sean los impostores
    nTrip = nJugadores - nImp;
    
     //hacemos un bucle que recorra todos los jugadores y seleccione jugadores aleatoriamente del arbol:
    printf("Jugadores seleccionados: \n");
    for (i = 0; i < nJugadores; i++){
        *j= 0;
        nAl = _aleatorio(1, _NumeroNodos(*Arbol));
        
        //llamamos a la funcion que seleccionara un jugador 
        while (!_seleccionarJugador(*Arbol,nAl,j,&registro)) {
            *j= 0;
            nAl= _aleatorio(1, _NumeroNodos(*Arbol));
        }
        
        printf("%s\n", registro.nombreJugador);
        registro.rol = 'T';
        modificar(*Arbol,registro);
        
    }
    
    //hacemos un bucle que recorra todos los jugadores y seleccione impostores aleatoriamente del arbol:
    printf("\nNumero de Impostores: %d",nImp);
    for (i= 0;i<nImp;i++) {
        *j= 0;
        nAl= _aleatorio(1, nImp);
        
        //llamamos a la funcion que seleccioanara al impostor
        while (!_seleccionarImpostores(*Arbol,nAl,j,&registro)) {
            *j= 0;
            nAl= _aleatorio(1, nImp);
        }
        
        //le asignamos el rol de I y modificamos jugador en el arbol
        registro.rol = 'I';
        modificar(*Arbol,registro);
    }
   
    //imprimimos los jugadores:
    printf("\nEstado final de los jugadores: \n");
    //le asignamos tarea y sala a cada uno,metiendolo en la cola de tarea, y modificamos el arbol
    _asignarTareaYSala(*Arbol,&registro);
    modificar(*Arbol,registro);
}

//Función que imprime los datos de un usuario cuyo nombre se introduce por teclado
void consultarJugador(abb Arbol){
    tipoelem registro;
    
    //pedimos al usuario el nombre de usuario deseado. Si no tiene @, repetira la pregunta
    do{
        printf("Nombre de Jugador:(@nickname): ");
        scanf("%s", registro.nombreJugador);
    }while(registro.nombreJugador[0] != '@');
    
    //si el usuario no pertenece a nuestra lista informa al usuario
    if(!es_miembro(Arbol, registro)){
        printf("Este nickname no existe...");    
    }else{ //si pertenece lo busca en el arbol, y imprime sus caracteristicas
        buscar_nodo(Arbol,registro.nombreJugador,&registro);
        _imprimirJugador(registro);
    }
}

//Función que imprime todos los usuarios que están en una habitación determinada
void consultarPorHabitacion(abb Arbol) {
    int habitacion;
    char sala[L];
    
   //le preguntamos al usuario que habtiacion quiere consultar
    do{
        printf(" 1. Armería \n 2. Cafetería \n 3. Comunicaciones \n 4. Electricidad \n 5. Escudos \n 6. Motor \n 7. Navegación \n 8. O2 \n 9. Seguridad \n Selecciona número de ubicación: ");
        scanf("%d", &habitacion);
    }while(habitacion>9 || habitacion<1);
    
    
        //depende de la sala seleccionada, pues meteremos la constante deseada en la variable char 
        switch(habitacion){
            case 1:
                strcpy(sala, "Armeria");
                break;
            case 2:
                strcpy(sala, "Cafeteria");
                break;
            case 3:
                strcpy(sala, "Comunicaciones");
                break;
            case 4:
                strcpy(sala, "Electricidad");
                break;
            case 5:
                strcpy(sala, "Escudos");
                break;
            case 6:
                strcpy(sala, "Motor");
                break;
            case 7:
                strcpy(sala, "Navegacion");
                break;
            case 8:
                strcpy(sala, "O2");
                break;
            case 9:
                strcpy(sala, "Seguridad");
                break;
        }
        
    //buscamos la habitacion que queremos, la imprimos y enseñamos la primera tarea de esa habitacion de la cola
    printf("Primera tarea en la sala %s: \n",sala);
    _encontrarLaHabitacion(Arbol,sala);
}

//Funcion para guardar la lista actualizada en un archivo
void guardarEnArchivo(abb A){
    FILE *fp;
    //abrimos el archivo
    fp= fopen("jugadores.txt","w");
    //guardamos los jugadores en el archivo recursivamente
    _guardarEnArchivo(A,fp);
    //cerramos el archivo
    fclose(fp);
}
