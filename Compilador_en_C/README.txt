1. Introduce todos arquivos .c e .h na mesma carpeta.
2. Sitúate na carpeta coa terminal e compila os ficheiros a través do comando:

gcc -Wall main.c abb.c AnalizadorLexico.c AnalizadorSintactico.c errores.c SistemaEntrada.c TS.c -o executable.exe && ./executable.exe

3. Os resultados móstranse na terminal e no "resultados.txt" (aquí non aparecen os errores que se imprimen co errores.c)

