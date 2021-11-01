import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MainExperiment5 {
    public static void main() throws Exception {
        final int ITERS = 1000;
        final int N = 10;
        try {
            double[][] benefici = new double[2][ITERS];
            double[][] km_recorreguts = new double[2][ITERS];


            for (int k = 0; k < ITERS; k++) {

                Gasolineras s = new Gasolineras(100, k);
                CentrosDistribucion c = new CentrosDistribucion(10, 1, k);
                Estat estat = new Estat(c, s);

                estat.creaAssignacioPerDefecte();

                HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                GoalTest goalTest = new GasolineraGoalTest();

                SuccessorFunction successorFunction = new GasolineraSuccesorFunction(N, false, false, true, false);
                Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem, search);


                Estat estatFinal = (Estat) search.getGoalState();

                benefici[0][k] = Benefici.getValor(estatFinal);
                km_recorreguts[0][k] = Benefici.getQuilometresTotals(estatFinal);
            }

            for (int k = 0; k < ITERS; k++) {

                Gasolineras s = new Gasolineras(100, k);
                CentrosDistribucion c = new CentrosDistribucion(5, 2, k);
                Estat estat = new Estat(c, s);

                long start = System.nanoTime();
                estat.creaAssignacioPerDefecte();

                HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                GoalTest goalTest = new GasolineraGoalTest();

                SuccessorFunction successorFunction = new GasolineraSuccesorFunction(N, false, false, true, false);
                Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem, search);

                Estat estatFinal = (Estat) search.getGoalState();

                benefici[1][k] = Benefici.getValor(estatFinal);
                km_recorreguts[1][k] = Benefici.getQuilometresTotals(estatFinal);
            }

            for (int i = 0; i < 2; ++i) {
                String filename = "../resultatsExperiments/experiment5/cas" + (i) + "_boxplot_benefici.csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                for (int j = 0; j < ITERS; j++) {
                    writer.write(benefici[i][j] + "\n");
                }
                writer.close();
            }
            for (int i = 0; i < 2; ++i) {
                String filename = "../resultatsExperiments/experiment5/cas" + (i) + "_boxplot_quilometres.csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                for (int j = 0; j < ITERS; j++) {
                    writer.write(km_recorreguts[i][j]  + "\n");
                }
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
