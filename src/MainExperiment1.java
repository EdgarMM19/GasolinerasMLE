import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class MainExperiment1 {
    public static void main(String[] args) throws Exception {
        boolean[][] OPS = {
                {false, false, false, true},
                {true, true, false, false},
                {false, false, true, false},
                {true, true, true, false},
                {true, true, false, true},
                {false, false, true, true},
                {true, true, true, true}
        };
        final int ITERS = 1000;
        final int N = 10;
        try {
            double[][] benefici = new double[OPS.length][ITERS];
            double[][] temps = new double[OPS.length][ITERS];
            double[][] nodes = new double[OPS.length][ITERS];

            for (int i = 0; i < OPS.length; ++i) {
                for (int k = 0; k < ITERS; k++) {

                    Gasolineras s = new Gasolineras(100, k);
                    CentrosDistribucion c = new CentrosDistribucion(10, 1, k);
                    Estat estat = new Estat(c, s);

                    long start = System.nanoTime();
                    estat.creaAssignacioPerDefecte();

                    HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                    GoalTest goalTest = new GasolineraGoalTest();

                    SuccessorFunction successorFunction = new GasolineraSuccesorFunction(N, OPS[i][0], OPS[i][1], OPS[i][2], OPS[i][3]);
                    Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                    Search search = new HillClimbingSearch();
                    SearchAgent agent = new SearchAgent(problem, search);
                    long finish = System.nanoTime();
                    double timeElapsed = (finish - start)/1000000.0;

                    Estat estatFinal = (Estat) search.getGoalState();

                    benefici[i][k] = Benefici.getValor(estatFinal);
                    temps[i][k] = timeElapsed;
                    nodes[i][k] = java.lang.Integer.parseInt(agent.getInstrumentation().getProperty("nodesExpanded"));
                }
            }

            for (int i = 0; i < 7; ++i) {
                String filename = "./resultatsExperiments/operadors/operadors" + (i + 1) + "_boxplot_benefici.csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                for (int j = 0; j < ITERS; j++) {
                    writer.write(benefici[i][j] + "\n");
                }
                writer.close();
            }
            for (int i = 0; i < 7; ++i) {
                String filename = "./resultatsExperiments/operadors/operadors" + (i + 1) + "_boxplot_temps.csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                for (int j = 0; j < ITERS; j++) {
                    writer.write(temps[i][j]  + "\n");
                }
                writer.close();
            }
            for (int i = 0; i < 7; ++i) {
                String filename = "./resultatsExperiments/operadors/operadors" + (i + 1) + "_boxplot_nodes.csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                for (int j = 0; j < ITERS; j++) {
                    writer.write(nodes[i][j]  + "\n");
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
