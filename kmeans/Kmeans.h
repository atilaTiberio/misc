//
// Created by hiturbe on 17/04/18.
//


#include<vector>
#include <random>
#include <limits>
#include <mutex>
#include <time.h>

using namespace std;
struct observations{
    float edad=0.0f;
    float genero=0.0f;
    float entidad=0.0f;
    float sector=0.0f;
    float padecimiento=0.0f;
    float medicamento=0.0f;
};

struct cluster{
    struct observations centro;
    vector<observations> puntos;
};



class Kmeans {
public:
    /*
     * edad 0-99
     * genero 1-2
     * entidad 1-31
     * sector 1-5
     * padecimiento 1-3400
     * medicamento 1-5000
     *
     *
     */
    int NUMTHREADS;
    int offset;


    random_device rd;
    mt19937 gen{rd()};
    uniform_int_distribution<> edad{0, 99};
    uniform_int_distribution<> genero{1, 2};
    uniform_int_distribution<> entidad{1, 31};
    uniform_int_distribution<> sector{1, 5};
    uniform_int_distribution<> padecimiento{1, 3400};
    uniform_int_distribution<> medicamento{1, 5000};
    vector<observations> dataset;
    vector<cluster> kCluster;
    const char *archivo;
    double distanciaMinima(struct observations punto, struct observations centroide);

    void info();
    void cargaDataset();
    void creaFile(int size);
    void getRandom(struct observations *observations);
    void printStruct(struct observations structobservations);

    void kmeansSerial(int clusterSize, bool ejecutar);
    void kmeansParalelo(int clusterSize, bool ejecutar);
    bool centroidesIguales(struct observations centroideActual,struct observations nuevoCentroide);
    void* asignaPuntos(void *args);

    void calculaNuevoCentroide(struct observations *nuevoCentroide,struct observations punto, unsigned long totalPuntosAsignados);

    //Kmeans(int clusterSize,bool ejecutar=false,int type=0,const char *file="/home/hiturbe/etc/kmeans/kmeans.bin");
    Kmeans(int clusterSize,bool ejecutar=false,int type=0,const char *file="/Users/iturbeh/etc/dataBinario/kmeans.bin");

    Kmeans();
    ~Kmeans();



};



