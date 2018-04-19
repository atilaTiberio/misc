
#include <iostream>
#include "Kmeans.h"
#include <thread>



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




 void* Kmeans::asignaPuntos(void *args) {

    int idThread=*(int*)args;

    /*
     * dataset de 0 a 10 000 000
     *
     *
     */
    int inicio= (idThread*offset);
    int fin= ((idThread+1)*offset);

printf("Procesando asignacion de puntos thread: %d de %d a %d \n",idThread,inicio,fin);

    for(int j=inicio;j<fin;j++){

        double dMin=numeric_limits<double>::max();
        double dist=0;

        struct cluster *seleccion;

        for(int k=0;k<kCluster.size();k++){

            dist=distanciaMinima(dataset[j],kCluster[k].centro);
            if(dist<dMin){
                dMin=dist;
                seleccion=&kCluster[k];
            }
        }
        seleccion->puntos.push_back(dataset[j]);
    }


}

Kmeans::Kmeans() {


}

Kmeans::Kmeans(int clusterSize, bool ejecutar, int type, const char *fileDirectory) {



    archivo=fileDirectory;
    kCluster.resize(clusterSize);
    cargaDataset();
    for(int i=0; i<clusterSize;i++) {
        getRandom(&kCluster[i].centro);
    }


    if(ejecutar){

/*
 * Genera los clusters randoms
 */


    printf("revisando puntos\n");
    time_t rawtime;

    time (&rawtime);
    printf ("The current local time is: %s", ctime (&rawtime));


    for(int j=0;j<dataset.size();j++){
        double dMin=numeric_limits<double>::max();
        double dist=0;

        struct cluster *seleccion;

        for(int k=0;k<clusterSize;k++){

            dist=distanciaMinima(dataset[j],kCluster[k].centro);
            if(dist<dMin){
                dMin=dist;
                seleccion=&kCluster[k];
            }
        }
        seleccion->puntos.push_back(dataset[j]);
    }

    time (&rawtime);
    printf ("The current local time is: %s", ctime (&rawtime));
    }


}

void Kmeans::kmeansParalelo(int clusterSize, bool ejecutar) {


    kCluster.resize(clusterSize);
    cargaDataset();



/*
 * Genera los clusters randoms
 */
    for(int i=0; i<clusterSize;i++) {
        getRandom(&kCluster[i].centro);
    }





}


void Kmeans::kmeansSerial(int clusterSize,bool ejecutar) {

    /*
     * generar los kClusters random
     */

    if(!ejecutar)
        return;


    /*kCluster.resize(clusterSize);
    cargaDataset();
     */
/*
 * Genera los clusters randoms
 */
    for(int i=0; i<clusterSize;i++) {
        getRandom(&kCluster[i].centro);
    }


    printf("revisando puntos\n");
    time_t rawtime;

    time (&rawtime);
    printf ("The current local time is: %s", ctime (&rawtime));




/*
 * Asignar puntos
 * Dividir el tamaño del arreglo y revisar si es necesario aplicar mutex al cluster
 * cuando se esta agregando un punto
 *


    unsigned long totalPuntos=dataset.size();
    unsigned long base=dataset.size()/16;

    for(int ite=1;ite<=16;ite++){
        printf("Procesando %d %d\n",base*(ite-1),base*ite);
        procesa(base*(ite-1),base*ite,clusterSize);

    }






  */

    int reajustes=0;
    int ite=0;
        while(reajustes<clusterSize &&ejecutar){
            ite++;
            printf("ite %d\n",ite);
            for(int i=0; i<clusterSize;i++) {
                kCluster[i].puntos.clear();
            }

            reajustes=0;

            for(int j=0;j<dataset.size();j++){
                double dMin=numeric_limits<double>::max();
                double dist=0;

                struct cluster *seleccion;

                for(int k=0;k<clusterSize;k++){

                    dist=distanciaMinima(dataset[j],kCluster[k].centro);
                    if(dist<dMin){
                        dMin=dist;
                        seleccion=&kCluster[k];
                    }
                }
                seleccion->puntos.push_back(dataset[j]);
            }


            /*
             * Reajustar centroides
             */

            unsigned long puntosSize=0;

            for(int k=0;k<clusterSize;k++){


                if(kCluster[k].puntos.size()==0){
                    reajustes++;
                    continue;
                }
                struct observations nuevoCentroide;

                puntosSize=kCluster[k].puntos.size();

                for(int c=0;c<puntosSize;c++){

                    struct observations punto=kCluster[k].puntos[c];
                    /*
                     * Para evitar condiciones de concurso, mejor hacer esto aqui
                     */

                    calculaNuevoCentroide(&nuevoCentroide,kCluster[k].puntos[c],kCluster[k].puntos.size());

                }

                if(centroidesIguales(kCluster[k].centro,nuevoCentroide)){
                    reajustes++;
                    if(reajustes==clusterSize) {
                        printf("--- %d\n", k);
                        printStruct(nuevoCentroide);
                        printStruct(kCluster[k].centro);
                    }

                }
                else{

                    kCluster[k].centro=nuevoCentroide;
                }

            }



        }



    printf(" Total de iteraciones  %d \n",ite);
    time (&rawtime);
    printf ("The current local time is: %s", ctime (&rawtime));





}
void Kmeans::calculaNuevoCentroide(struct observations *nuevoCentroide,struct observations punto, unsigned long totalPuntosAsignados){

    nuevoCentroide->edad=nuevoCentroide->edad+(punto.edad/totalPuntosAsignados);
    nuevoCentroide->entidad=nuevoCentroide->entidad+(punto.entidad/totalPuntosAsignados);
    nuevoCentroide->genero=nuevoCentroide->genero+(punto.genero/totalPuntosAsignados);
    nuevoCentroide->medicamento=nuevoCentroide->medicamento+(punto.medicamento/totalPuntosAsignados);
    nuevoCentroide->padecimiento=nuevoCentroide->padecimiento+(punto.padecimiento/totalPuntosAsignados);
    nuevoCentroide->sector=nuevoCentroide->sector+(punto.sector/totalPuntosAsignados);


}
/*
 *
 */


double Kmeans::distanciaMinima(struct observations punto, struct observations centroide){

    double d=0;


    d += pow(punto.edad -centroide.edad, 2);
    d += pow(punto.genero -centroide.genero, 2);
    d += pow(punto.entidad -centroide.entidad, 2);
    d += pow(punto.sector -centroide.sector, 2);
    d += pow(punto.padecimiento -centroide.padecimiento, 2);
    d += pow(punto.medicamento -centroide.medicamento, 2);
    return sqrt(d);
}


void Kmeans::getRandom(struct observations *centroide){

    //struct observations *centroide=(observations*)malloc(sizeof(struct observations));
    centroide->edad=(float)edad(gen);
    centroide->entidad=(float)entidad(gen);
    centroide->genero=(float)genero(gen);
    centroide->medicamento=(float)medicamento(gen);
    centroide->padecimiento=(float)padecimiento(gen);
    centroide->sector=(float)sector(gen);

}





void Kmeans::creaFile(int size){



    struct observations *structobservations;
    structobservations=(observations*)malloc(sizeof(observations));

    FILE *file = NULL;    // File pointer


    if ((file = fopen(archivo, "wb")) == NULL)
        cout << "Could not open specified file" << endl;
    else
        cout << "File opened successfully for writing" << endl;

    float edadMaxima=0.0f;
    float edadMinima=120.0f;

    fwrite(&size,sizeof(int),1,file);

    for (int j=0;j<size;j++){

        structobservations->edad=(float)edad(gen);
        structobservations->entidad=(float)entidad(gen);
        structobservations->genero=(float)genero(gen);
        structobservations->medicamento=(float)medicamento(gen);
        structobservations->padecimiento=(float)padecimiento(gen);
        structobservations->sector=(float)sector(gen);
        fwrite(structobservations,sizeof(observations),1,file);
    }
    fclose(file);

    printf("Fin de escritura\n");


}
void Kmeans::cargaDataset(){



    struct  observations obs;

    int size=0;

    float edadMaxima=0.0f;
    float edadMinima=120.0f;

    FILE *file = NULL;    // File pointer


    if ((file = fopen(archivo, "rb")) == NULL)
        cout << "Could not open specified file" << endl;
    else
        cout << "File opened successfully for reading" << endl;

    fread(&size,sizeof(int),1,file);
    dataset.resize(size);
    fread(&dataset[0],sizeof(observations),size,file);
    fclose(file);

    printf("Lectura terminada\n");





}

void Kmeans::info() {



    struct  observations obs;

    int size=0;

    float edadMaxima=0.0f;
    float edadMinima=120.0f;

    FILE *file = NULL;    // File pointer


    if ((file = fopen(archivo, "rb")) == NULL)
        cout << "Could not open specified file" << endl;
    else
        cout << "File opened successfully for reading" << endl;

    fread(&size,sizeof(int),1,file);
    printf("size %d\n",size);
    int j=0;


    for(j=0; j<size;j++){
        /*if(j%10000==0){
            printf("%d\n",j);
        }
         */
        fread(&obs, sizeof(observations), 1, file);

        if(edadMaxima<obs.medicamento){
            edadMaxima=obs.medicamento;
        }

        if(edadMinima>obs.medicamento){
            edadMinima=obs.medicamento;
        }
    }


    printf("Resultado de lectura %d - %f %f \n",j,edadMaxima,edadMinima);

    fclose(file);




}
void Kmeans::printStruct(struct observations structobservations) {

    printf("Edad %f ",structobservations.edad);
    printf("genero %f ",structobservations.genero);
    printf("entidad %f ",structobservations.entidad);
    printf("sector %f ",structobservations.sector);
    printf("padecimiento %f ",structobservations.padecimiento);
    printf("medicamento %f \n",structobservations.medicamento);



}

bool Kmeans::centroidesIguales(struct observations centroideActual, struct observations nuevoCentroide) {
    return nuevoCentroide.edad==centroideActual.edad &&
    nuevoCentroide.entidad==centroideActual.entidad &&
    nuevoCentroide.genero==centroideActual.genero &&
    nuevoCentroide.medicamento==centroideActual.medicamento &&
    nuevoCentroide.padecimiento==centroideActual.padecimiento &&
    nuevoCentroide.sector==centroideActual.sector;

}


