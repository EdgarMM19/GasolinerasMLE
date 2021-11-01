# Compilació

Per compilar (ignorar els warning):

make

# Execució

Per executar els experiments:

make experiments

Per poder guardar els resultats dels experiments, és necessari que existeixin
les carpetes resultatsExperiments/experiment{i} per i = 1..7

L'expriment 3 no es pot executar amb aquest programa. Per a fer-ho, s'ha de 
modificar el codi indicat a MainExperiment3 i GasolineraAnnealingSuccesorFunction
i executar el programa MainExperiment3.

Per fer els gràfics, hem utilitzat els programes inclosos a la carpeta grafics.

# Neteja

Per eliminar els fitxers binaris:

make clean

Per eliminar els fitxers de resultats

make clean-resultats