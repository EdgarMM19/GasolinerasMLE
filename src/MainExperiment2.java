import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;

import java.io.File;
import java.io.FileWriter;

public class MainExperiment2 {
    public static void main(String[] args) throws Exception {
        final int ITERS = 1000;
        try {
            double[] benefici_buida = new double[ITERS];
            double[] temps_buida = new double[ITERS];
            double[] nodes_buida = new double[ITERS];

            double[] benefici_greedy = new double[ITERS];
            double[] temps_greedy = new double[ITERS];
            double[] nodes_greedy = new double[ITERS];

            for (int k = 0; k < ITERS; k++) {

                Gasolineras s = new Gasolineras(100, 2000 + k);
                CentrosDistribucion c = new CentrosDistribucion(10, 1, 2000 + k);
                Estat estat = new Estat(c, s);

                long start = System.nanoTime();
                estat.creaAssignacioPerDefecte();

                HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                GoalTest goalTest = new GasolineraGoalTest();

                SuccessorFunction successorFunction = new GasolineraSuccesorFunction(0, false, false, true, false);
                Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem, search);
                long finish = System.nanoTime();
                double timeElapsed = (finish - start)/1000000.0;

                Estat estatFinal = (Estat) search.getGoalState();

                benefici_buida[k] = Benefici.getValor(estatFinal);
                temps_buida[k] = timeElapsed;
                nodes_buida[k] = Integer.parseInt(agent.getInstrumentation().getProperty("nodesExpanded"));
            }

            for (int k = 0; k < ITERS; k++) {

                Gasolineras s = new Gasolineras(100, 2000 + k);
                CentrosDistribucion c = new CentrosDistribucion(10, 1, 2000 + k);
                Estat estat = new Estat(c, s);

                long start = System.nanoTime();
                estat.generaAssignacioInicial1();

                HeuristicFunction heuristica = new GasolineraHeuristicFunction1();
                GoalTest goalTest = new GasolineraGoalTest();

                SuccessorFunction successorFunction = new GasolineraSuccesorFunction(0, false, false, true, false);
                Problem problem = new Problem(estat, successorFunction, goalTest, heuristica);
                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem, search);

                long finish = System.nanoTime();
                double timeElapsed = (finish - start)/1000000.0;

                Estat estatFinal = (Estat) search.getGoalState();

                benefici_greedy[k] = Benefici.getValor(estatFinal);
                temps_greedy[k] = timeElapsed;
                nodes_greedy[k] = Integer.parseInt(agent.getInstrumentation().getProperty("nodesExpanded"));
            }

            {
                String filename_b1 = "./resultatsExperiments/inicial/buida_boxplot_benefici.csv";
                File fitxer_b1 = new File(filename_b1);
                if (fitxer_b1.createNewFile()) System.out.println("Fitxer creat: " + filename_b1);
                else System.out.println("Fitxer modificat: " + filename_b1);
                FileWriter writer_b1 = new FileWriter(filename_b1);

                for (int j = 0; j < ITERS; j++) {
                    writer_b1.write(benefici_buida[j] + "\n");
                }
                writer_b1.close();

                String filename_b2 = "./resultatsExperiments/inicial/buida_boxplot_temps.csv";
                File fitxer_b2 = new File(filename_b2);
                if (fitxer_b2.createNewFile()) System.out.println("Fitxer creat: " + filename_b2);
                else System.out.println("Fitxer modificat: " + filename_b2);
                FileWriter writer_b2 = new FileWriter(filename_b2);

                for (int j = 0; j < ITERS; j++) {
                    writer_b2.write(temps_buida[j] + "\n");
                }
                writer_b2.close();

                String filename_b3 = "./resultatsExperiments/inicial/buida_boxplot_nodes.csv";
                File fitxer_b3 = new File(filename_b3);
                if (fitxer_b3.createNewFile()) System.out.println("Fitxer creat: " + filename_b3);
                else System.out.println("Fitxer modificat: " + filename_b3);
                FileWriter writer_b3 = new FileWriter(filename_b3);

                for (int j = 0; j < ITERS; j++) {
                    writer_b3.write(nodes_buida[j] + "\n");
                }
                writer_b3.close();
            }

            {
                String filename_b1 = "./resultatsExperiments/inicial/voraç_boxplot_benefici.csv";
                File fitxer_b1 = new File(filename_b1);
                if (fitxer_b1.createNewFile()) System.out.println("Fitxer creat: " + filename_b1);
                else System.out.println("Fitxer modificat: " + filename_b1);
                FileWriter writer_b1 = new FileWriter(filename_b1);

                for (int j = 0; j < ITERS; j++) {
                    writer_b1.write(benefici_greedy[j] + "\n");
                }
                writer_b1.close();

                String filename_b2 = "./resultatsExperiments/inicial/voraç_boxplot_temps.csv";
                File fitxer_b2 = new File(filename_b2);
                if (fitxer_b2.createNewFile()) System.out.println("Fitxer creat: " + filename_b2);
                else System.out.println("Fitxer modificat: " + filename_b2);
                FileWriter writer_b2 = new FileWriter(filename_b2);

                for (int j = 0; j < ITERS; j++) {
                    writer_b2.write(temps_greedy[j] + "\n");
                }
                writer_b2.close();

                String filename_b3 = "./resultatsExperiments/inicial/voraç_boxplot_nodes.csv";
                File fitxer_b3 = new File(filename_b3);
                if (fitxer_b3.createNewFile()) System.out.println("Fitxer creat: " + filename_b3);
                else System.out.println("Fitxer modificat: " + filename_b3);
                FileWriter writer_b3 = new FileWriter(filename_b3);

                for (int j = 0; j < ITERS; j++) {
                    writer_b3.write(nodes_greedy[j] + "\n");
                }
                writer_b3.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
