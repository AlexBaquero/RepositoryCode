OUTPUT = executable.exe

ARCHS_C = main.c abb.c AnalizadorLexico.c AnalizadorSintactico.c errores.c SistemaEntrada.c TS.c 

LIBRERIAS = definicions.h abb.h AnalizadorLexico.h AnalizadorSintactico.h errores.h SistemaEntrada.h TS.h

$ (OUTPUT): $(ARCHS_C) $(LIBRERIAS)
	gcc -Wall -o $(OUTPUT) $(ARCHS_C) -lm $(LIBRERIAS)