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
    private static final int STEPS = 5000;
    private static final int NUM_IT = 40;
    public static void main(String[] args) throws Exception{
        valor_iteracio = new ArrayList<>();
        try {
            String filename = "./grafics/annealing5.txt";
            File fitxer = new File(filename);
            if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
            else System.out.println("Fitxer modificat: " + filename);
            FileWriter writer = new FileWriter(filename);
            int k = 5;
            int[] seeds = new int[NUM_IT];
            for (int i = 0; i < NUM_IT; ++i)
                seeds[i] = ThreadLocalRandom.current().nextInt(0, 10000);
            for (int it_k = 0; it_k < 1; ++it_k) {
                double lambda = 1;
                for (int it_lambda = 0; it_lambda < 10; ++it_lambda) {
                    double[] resultats = new double[STEPS + 1];
                    double resultat_final = 0;
                    double iteracions = 0;
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
                        double benefici = Benefici.getValor((Estat) search.getGoalState());
                        for (int i = 0; i < Math.min(STEPS, valor_iteracio.size()); ++i){
                            if (Math.abs(valor_iteracio.get(i) - valor_iteracio.get(valor_iteracio.size()-1)) < 2){
                                iteracions += i;
                                break;
                            }
                        }
                        resultat_final += benefici;
                        valor_iteracio = new ArrayList<>();
                    }
                    for (int i = 0; i < STEPS; ++i) {
                        resultats[i] /= NUM_IT;
                    }
                    resultat_final /= NUM_IT;
                    iteracions /= NUM_IT;
                    writer.write(lambda + " " + k + " " + resultat_final + " " + iteracions);
                    for (int i = 0; i < STEPS; ++i) {
                        writer.write(" " + resultats[i]);
                    }
                    writer.write("\n");
                    lambda /= 3.;
                }
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
