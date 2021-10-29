import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class MainAnnealing {
    public static ArrayList<Double> valor_iteracio;
    private static final int STEPS = 2000;
    private static final int NUM_IT = 10;
    public static void main(String[] args) throws Exception{
        valor_iteracio = new ArrayList<>();
        try {
            String filename = "./grafics/annealing3.txt";
            File fitxer = new File(filename);
            if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
            else System.out.println("Fitxer modificat: " + filename);
            FileWriter writer = new FileWriter(filename);
            int k = 1;
            int[] seeds = new int[NUM_IT];
            for (int i = 0; i < NUM_IT; ++i)
                seeds[i] = ThreadLocalRandom.current().nextInt(0, 10000);
            for (int it_k = 0; it_k < 5; ++it_k) {
                double lambda = 10.0;
                for (int it_lambda = 0; it_lambda < 20; ++it_lambda) {
                    double[] resultats = new double[STEPS + 1];
                    double resultat_final = 0;
                    for (int it = 0; it < NUM_IT; ++it) {
                        Gasolineras s = new Gasolineras(100, seeds[it]);
                        CentrosDistribucion c = new CentrosDistribucion(10, 1, seeds[it]);
                        Estat estat = new Estat(c, s);
                        estat.generaAssignacioInicial1();

                        Problem p = new Problem(estat,
                                new GasolineraAnnealingSuccesorFunction(),
                                new GasolineraGoalTest(),
                                new GasolineraHeuristicFunction1());
                        SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(STEPS, 100, k, lambda);
                        SearchAgent agent = new SearchAgent(p, search);
                        for (int i = 0; i < Math.min(STEPS, valor_iteracio.size()); ++i) {
                            resultats[i] -= valor_iteracio.get(i);
                        }
                        resultat_final += Benefici.getValor((Estat) search.getGoalState());
                        valor_iteracio = new ArrayList<>();
                    }
                    for (int i = 0; i < STEPS; ++i) {
                        resultats[i] /= NUM_IT;
                    }
                    resultat_final /= NUM_IT;
                    writer.write(lambda + " " + k + " " + resultat_final);
                    for (int i = 0; i < STEPS; ++i) {
                        writer.write(" " + resultats[i]);
                    }
                    writer.write("\n");
                    lambda /= 8.;
                }
                k *= 5;
            }
            writer.close();
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
