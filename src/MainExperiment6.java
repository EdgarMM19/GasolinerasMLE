import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MainExperiment6 {
    public static void main(String[] args) throws Exception {
        final int ITERS = 100;
        final int CASOS = 7;
        final int N = 10;
        try {
            double[][] num_peticions_servides = new double[CASOS][5];
            int cost_km_recorregut = 1;

            for(int i = 0; i < CASOS; i++) {
                cost_km_recorregut *= 2;
                for (int j = 0; j < 5; j++) {
                    num_peticions_servides[i][j] = 0;
                }
                for (int k = 0; k < ITERS; k++) {
                    Gasolineras s = new Gasolineras(100, k);
                    CentrosDistribucion c = new CentrosDistribucion(10, 1, k);
                    Estat estat = new Estat(c, s);
                    estat.cost_quilometre = cost_km_recorregut;

                    estat.creaAssignacioPerDefecte();

                    HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                    GoalTest goalTest = new GasolineraGoalTest();

                    SuccessorFunction successorFunction = new GasolineraSuccesorFunction(N, false, false, true, false);
                    Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                    Search search = new HillClimbingSearch();
                    SearchAgent agent = new SearchAgent(problem, search);

                    Estat estatFinal = (Estat) search.getGoalState();

                    for (int j = 0; j < 5; j++) {
                        num_peticions_servides[i][j] += Benefici.getNumPeticionsServides(estatFinal, j-1);
                    }
                }
                for (int j = 0; j < 5; j++) {
                    num_peticions_servides[i][j] /= ITERS;
                }
                System.out.println(num_peticions_servides[i][0]);


            }

            String filename = "./resultatsExperiments/exp6/num_peticions.csv";
            File fitxer = new File(filename);
            if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
            else System.out.println("Fitxer modificat: " + filename);
            FileWriter writer = new FileWriter(filename);

            for (int i = 0; i < CASOS; i++) {
                writer.write(num_peticions_servides[i][0] + "\n");
            }
            writer.close();

            for (int j = 0; j < CASOS; ++j ) {
                filename = "./resultatsExperiments/exp6/num_peticions_cost" + (j+1) + ".csv";
                fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                writer = new FileWriter(filename);

                for (int i = 0; i < 4; i++) {
                    writer.write(num_peticions_servides[j][i+1] + "\n");
                }
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
