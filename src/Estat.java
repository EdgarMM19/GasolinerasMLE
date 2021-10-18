import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;

import java.util.Arrays;

public class Estat {
    private static final int max_quilometres = 40;
    private static final int max_num_viatges = 5;
    private static final int valor_diposit = 1000;
    private static final int cost_quilometre = 2;
    private static int num_centres;
    private static CentrosDistribucion centres;
    private static int num_gasolineres;
    private static Gasolineras gasolineres;
    // La matriu 'distancies' guarda la distància de Manhattan precalculada entre dos elements de l'àrea geogràfica.
    // Els centres s'indexen amb els elements 0..(num_centres - 1).
    // Les gasolineres s'indexen amb els elements num_centres..(num_centres + num_gasolineres - 1).
    private static int[][] distancies;
    // El vector 'rutes' guarda, per cada centre de distribució, quina ruta té assignada el seu camió cisterna.
    private Ruta[] rutes;
    // La matriu 'assignacio_diposit' guarda, per cada gasolinera, un vector que conté l'índex del centre de distribució
    // assignat a cadascun dels seus dipòsits. Si un dipòsit no té centre de distribució assignat, hi ha -1 a la seva
    // posició.
    private int[][] assignacio_diposit;

    public Estat(@NotNull CentrosDistribucion _centres, @NotNull Gasolineras _gasolineres)
    {
        num_centres = _centres.size();
        centres = _centres;
        num_gasolineres = _gasolineres.size();
        gasolineres = _gasolineres;

        // Precalculem totes les distàncies per evitar càlculs innecessaris.
        distancies = new int[num_centres + num_gasolineres][num_centres + num_gasolineres];
        for (int i = 0; i < num_centres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                distancies[i][j] = Utils.GetDistancia(centres.get(i), centres.get(j));
                distancies[j][i] = distancies[i][j];
            }
            for (int j = 0; j < num_gasolineres; ++j) {
                distancies[i][num_centres + j] = Utils.GetDistancia(centres.get(i), gasolineres.get(j));
                distancies[num_centres + j][i] = distancies[i][num_centres + j];
            }
        }

        for (int i = 0; i < num_gasolineres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                distancies[num_centres + i][j] = Utils.GetDistancia(gasolineres.get(i), centres.get(j));
                distancies[j][num_centres + i] = distancies[num_centres + i][j];
            }
            for (int j = 0; j < num_gasolineres; ++j) {
                distancies[num_centres + i][num_centres + j] = Utils.GetDistancia(gasolineres.get(i),
                        gasolineres.get(j));
                distancies[num_centres + j][num_centres + i]  = distancies[num_centres + i][num_centres + j];
            }
        }

        assignacio_diposit = new int[num_gasolineres][];
        // Omplim l'assignació de dipòsits amb els valors i mides inicials.
        for (int i = 0; i < num_gasolineres; ++i) {
            assignacio_diposit[i] = new int[gasolineres.get(i).getPeticiones().size()];
            Arrays.fill(assignacio_diposit[i], -1);
        }

        // Per cada centre de distribució, li assignem la ruta inicial, que correspon a que el camió cisterna no s'ha
        // mogut del centre.
        rutes = new Ruta[num_centres];
        for (int i = 0; i < num_centres; ++i) {
            rutes[i] = new Ruta(centres.get(i).getCoordX(), centres.get(i).getCoordY());
        }
    }


}
