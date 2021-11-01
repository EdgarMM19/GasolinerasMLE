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

public class MainExperiment4 {
    private static final int NUM_IT = 20;
    public static void main(String[] args) throws Exception{
        try {
            String filename = "./grafics/grandaria2.txt";
            File fitxer = new File(filename);
            if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
            else System.out.println("Fitxer modificat: " + filename);
            FileWriter writer = new FileWriter(filename);

            for (int centres = 10; centres <= 200; centres += 10) {
                    double temps_hill = 0.;
                    double temps_annealing = 0.;
                    double temps_solucio_inicial = 0.;
                    double ben_hill = 0.;
                    double ben_annealing = 0.;
                    for (int it = 0; it < NUM_IT; ++it) {
                        int seed = ThreadLocalRandom.current().nextInt(0, 10000);
                        Gasolineras s = new Gasolineras(10*centres, seed);
                        CentrosDistribucion c = new CentrosDistribucion(centres, 1, seed);
                        Estat estat = new Estat(c, s);
                        long startTimeIni = System.nanoTime();
                        estat.generaAssignacioInicial1();
                        long endTimeIni = System.nanoTime();
                        temps_solucio_inicial += endTimeIni - startTimeIni;
                        Problem p = new Problem(estat,
                                new GasolineraAnnealingSuccesorFunction(),
                                new GasolineraGoalTest(),
                                new GasolineraHeuristicFunction1());
                        Search search_hill = new HillClimbingSearch();
                        SimulatedAnnealingSearch search_ann = new SimulatedAnnealingSearch(2000, 100, 5, Math.pow(10,-5));

                        long startTimeHill = System.nanoTime();
                        SearchAgent agent_hill = new SearchAgent(p, search_hill);
                        long endTimeHill = System.nanoTime();
                        temps_hill += endTimeHill - startTimeHill;
                        ben_hill += Benefici.getValor((Estat) search_hill.getGoalState());

                        long startTimeAnn = System.nanoTime();
                        SearchAgent agent_ann = new SearchAgent(p, search_ann);
                        long endTimeAnn = System.nanoTime();
                        temps_annealing += endTimeAnn - startTimeAnn;
                        ben_annealing += Benefici.getValor((Estat) search_ann.getGoalState());
                    }

                    temps_hill /= NUM_IT;
                    temps_annealing /= NUM_IT;
                    temps_solucio_inicial /= NUM_IT;
                    ben_annealing /= NUM_IT;
                    ben_hill /= NUM_IT;
                    System.out.println(centres);
                    writer.write(centres + " " + temps_solucio_inicial + " " + temps_annealing + " " + temps_hill
                            + " " + ben_annealing + " " + ben_hill + "\n");
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
