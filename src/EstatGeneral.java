import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class EstatGeneral
{
    private static CentrosDistribucion centros_distribucion;
    private static Gasolineras gasolineres;
    private static int num_gasolineres;
    private static int num_camions;
    // 0..num_camions-1: camions, num_camions..(num_camions+num_gasolineres): gasolineres
    private static int [][] distancies;
    private Ruta[] rutes;
    private ArrayList<ArrayList<Boolean>> diposits_plens;

    public EstatGeneral(@NotNull CentrosDistribucion centres, @NotNull Gasolineras _gasolineres)
    {
        centros_distribucion = centres;
        gasolineres = _gasolineres;
        num_gasolineres = _gasolineres.size();
        diposits_plens = new ArrayList<>(num_gasolineres);
        num_camions = centres.size();
        distancies = new int[num_camions+num_gasolineres][num_camions+num_gasolineres];
        for (int i = 0; i < num_camions; ++i) {
            for (int j = 0; j < num_camions; ++j)
                distancies[i][j] = UtilsGasolineres.distancia(centres.get(i).getCoordX(), centres.get(i).getCoordY(), centres.get(i).getCoordX(), centres.get(i).getCoordY());
            for (int j = 0; j < num_gasolineres; ++j)
                distancies[i][num_camions+j] = UtilsGasolineres.distancia(centres.get(i).getCoordX(), centres.get(i).getCoordY(), gasolineres.get(i).getCoordX(), gasolineres.get(i).getCoordY());
        }
        for (int i = 0; i < num_gasolineres; ++i) {
            for (int j = 0; j < num_camions; ++j)
                distancies[num_camions+i][j] = UtilsGasolineres.distancia(gasolineres.get(i).getCoordX(), gasolineres.get(i).getCoordY(), centres.get(i).getCoordX(), centres.get(i).getCoordY());
            for (int j = 0; j < num_gasolineres; ++j)
                distancies[num_camions+i][num_camions+j] = UtilsGasolineres.distancia(gasolineres.get(i).getCoordX(), gasolineres.get(i).getCoordY(), gasolineres.get(i).getCoordX(), gasolineres.get(i).getCoordY());
            diposits_plens.set(i, new ArrayList<Boolean>(Arrays.asList(new Boolean[gasolineres.get(i).getPeticiones().size()])));
        }
        rutes = new Ruta[num_camions];
        for (int i = 0; i < num_camions; ++i)
        {
            rutes[i] = new Ruta(centres.get(i).getCoordX(), centres.get(i).getCoordY());
        }
    }


}
