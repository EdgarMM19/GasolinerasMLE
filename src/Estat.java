import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;
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

    static public int GetDistanciaEntreCentreIGasolinera(int c, int g) {
        return distancies[c][num_centres + g];
    }

    static public int GetDistanciaEntreCentres(int c1, int c2) {
        return distancies[c1][c2];
    }

    static public int GetDistanciaEntreGasolineres(int g1, int g2) {
        return distancies[num_centres + g1][num_centres + g2];
    }

    public int AvaluaFuncioHeuristica(){
        int cost_quilometres_totals = 0;
        int diners_cobrats = 0;
        int diners_perduts = 0;

        for (int i = 0; i < num_centres; ++i) {
            cost_quilometres_totals += rutes[i].GetQuilometresRecorreguts() * cost_quilometre;
        }

        for (int i = 0; i < num_gasolineres; ++i) {
            Gasolinera g = gasolineres.get(i);
            ArrayList<Integer> p = g.getPeticiones();
            int num_diposits = p.size();
            for (int j = 0; j < num_diposits; ++j) {
                if ( assignacio_diposit[i][j] != -1) {
                    int percentatge;
                    if (p.get(j) == 0) {
                        percentatge = 102;
                    } else {
                        percentatge = 100 - (1 << p.get(j));
                    }
                    diners_cobrats += valor_diposit*percentatge;
                } else {
                    // percentatge_perdut = (100 - 2^d) - (100 - 2^(d+1)) = 2^d
                    int percentatge_perdut;
                    if (p.get(j) == 0) {
                        percentatge_perdut = 4;
                    } else {
                        percentatge_perdut = 1 << p.get(j);
                    }
                    diners_perduts += valor_diposit*percentatge_perdut;
                }
            }
        }

        return diners_cobrats - cost_quilometres_totals - diners_perduts;
    }

}
