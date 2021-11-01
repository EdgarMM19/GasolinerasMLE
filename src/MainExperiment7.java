import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MainExperiment7 {
    public static void main() throws Exception {
        final int ITERS = 1000;
        final int N = 10;
        final int[] max_km = {560, 640, 720};
        final int viatges_max = 10;
        try {
            double[][] benefici = new double[3][ITERS];

            for (int  i = 0; i < 3; i++) {
                for (int k = 0; k < ITERS; k++) {

                    Gasolineras s = new Gasolineras(100, k);
                    CentrosDistribucion c = new CentrosDistribucion(10, 1, k);
                    Estat estat = new Estat(c, s);
                    Estat.max_quilometres = max_km[i];
                    Estat.max_num_viatges = viatges_max;

                    estat.creaAssignacioPerDefecte();

                    HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                    GoalTest goalTest = new GasolineraGoalTest();

                    SuccessorFunction successorFunction = new GasolineraSuccesorFunction(N, false, false, true, false);
                    Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                    Search search = new HillClimbingSearch();
                    SearchAgent agent = new SearchAgent(problem, search);


                    Estat estatFinal = (Estat) search.getGoalState();

                    benefici[i][k] = Benefici.getValor(estatFinal);
                }
            }

            for (int i = 0; i < 3; ++i) {
                String filename = "../resultatsExperiments/experiment7/boxplot_benefici_" + (7+i) + "hores.csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                for (int j = 0; j < ITERS; j++) {
                    writer.write(benefici[i][j] + "\n");
                }
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
