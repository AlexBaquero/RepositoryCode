#include <stdio.h>
#include <stdlib.h>
#include <pmmintrin.h>
#include <immintrin.h>
#include <unistd.h>
#include <time.h>
#include <math.h>

#define N 250 //250, 500, 750, 1000, 1500, 2550, 3000

void start_counter();
double get_counter();
double mhz();

FILE *fp;


/* Initialize the cycle counter */


static unsigned cyc_hi = 0;
static unsigned cyc_lo = 0;


/* Set *hi and *lo to the high and low order bits of the cycle counter.
Implementation requires assembly code to use the rdtsc instruction. */
void access_counter(unsigned *hi, unsigned *lo) {
    asm("rdtsc; movl %%edx,%0; movl %%eax,%1" /* Read cycle counter */
                : "=r" (*hi), "=r" (*lo) /* and move results to */
                : /* No input */ /* the two outputs */
                : "%edx", "%eax");
}

/* Record the current value of the cycle counter. */
void start_counter() {
    access_counter(&cyc_hi, &cyc_lo);
}

/* Return the number of cycles since the last call to start_counter. */
double get_counter() {
    unsigned ncyc_hi, ncyc_lo;
    unsigned hi, lo, borrow;
    double result;

    /* Get cycle counter */
    access_counter(&ncyc_hi, &ncyc_lo);

    /* Do double precision subtraction */
    lo = ncyc_lo - cyc_lo;
    borrow = lo > ncyc_lo;
    hi = ncyc_hi - cyc_hi - borrow;
    result = (double) hi * (1 << 30) * 4 + lo;
    if (result < 0) {
        fprintf(stderr, "Error: counter returns neg value: %.0f\n", result);
    }
    return result;
}

double mhz(int verbose, int sleeptime) {
    double rate;

    start_counter();
    sleep(sleeptime);
    rate = get_counter() / (1e6 * sleeptime);
    if (verbose)
        printf("\n Processor clock rate = %.1f MHz\n", rate);
    return rate;
}

int main(int argc, char const *argv[]) {

    printf("N: %d\n", N); //imprimir el valor de N

    fp = fopen("archivo_medidas_optimizados.txt", "a");

    double **a, **b, *c, **d, *e;
    int *ind, *usados;


    double f = 0;

    double ck;

    int ran;

    a = (double**) malloc(N * sizeof (double*));
    b = (double**) malloc(8 * sizeof (double*));

    d = (double**) malloc(N * sizeof (double*));

    for (int i = 0; i < N; i++) {
        d[i] = (double*) malloc(N * sizeof (double));
        a[i] = (double*) malloc(8 * sizeof (double));
    }

    for (int i = 0; i < 8; i++) {
        b[i] = (double*) malloc(N * sizeof (double));
    }

    ind = (int*) malloc(N * sizeof (int));
    c = (double*) malloc(8 * sizeof (double));
    e = (double*) malloc(N * sizeof (double));
    usados = (int*) malloc(N * sizeof (int));


    srand(42); //establecer la semilla de aleatoriedad


    for (int i = 0; i < N; i++) {

        usados[i] = 0;

        for (int j = 0; j < 8; j++) {
            a[i][j] = rand() / 2;
        }
    }


    //Calcular el vector índices
    for (int i = 0; i < N; i++) {
        ran = rand() % N;

        //calculamos números hasta que se llegue a una no usada
        while (usados[ran] != 0) {
            ran = rand() % N;
        }

        usados[ran] = 1; //marcamos la fila como usada
        ind[i] = ran;
    }

    for (int i = 0; i < 8; i++) {

        c[i] = rand() / 2;

        for (int j = 0; j < N; j++) {
            b[i][j] = rand() / 2;
        }
    }



    ////////////////////
    start_counter();

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {

            d[i][j] = 0;

            d[i][j] += 2 * a[i][0] * (b[0][j] - c[0]);
            d[i][j] += 2 * a[i][1] * (b[1][j] - c[1]);
            d[i][j] += 2 * a[i][2] * (b[2][j] - c[2]);
            d[i][j] += 2 * a[i][3] * (b[3][j] - c[3]);
            d[i][j] += 2 * a[i][4] * (b[4][j] - c[4]);
            d[i][j] += 2 * a[i][5] * (b[5][j] - c[5]);
            d[i][j] += 2 * a[i][6] * (b[6][j] - c[6]);
            d[i][j] += 2 * a[i][7] * (b[7][j] - c[7]);

        }

    }


    for (int i = 0; i < N; i++) {
        e[i] = (d[ind[i]][ind[i]]) / 2.0;
        f += ((double) e[i]);
    }

    ck = get_counter();
    ////////////////////



    printf("El valor total de f es igual a: %lf\n", f);

    printf("\n Clocks=%1.10lf \n", ck);

    fprintf(fp, "%lf\n", ck);
    fclose(fp);

    /* Esta rutina imprime a frecuencia de reloxo estimada coas rutinas start_counter/get_counter */
    mhz(1, 1);


    return 0;
}
