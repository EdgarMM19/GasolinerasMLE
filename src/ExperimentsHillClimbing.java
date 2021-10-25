import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExperimentsHillClimbing {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean continuar = true;
        int experiment;
        try {
            while (continuar) {
                String s;
                System.out.println("Escull experiment: operadors (0), estats inicials (1)");
                s = reader.readLine();
                experiment = Integer.parseInt(s);

                if (experiment == 0) {
                    experimentOperadors();
                    System.out.println("Dades guardades a operadors/operadorsN.csv, amb N = 0,...,*");
                } else if (experiment == 1) {
                    System.out.println("Quin conjunts d'operadors vols (de l'0 al *)?");
                    s = reader.readLine();
                    int k = Integer.parseInt(s);
                    experimentEstatsInicials();
                    System.out.println("Dades guardades a estatInicialN.csv, amb N = 0,...,*");
                }

                System.out.println("Vols tornar a fer experiments? (sí = 1, no = 0)");
                s = reader.readLine();
                continuar = (Integer.parseInt(s) == 1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void experimentOperadors() {
        final int ITERACIONS = 25;
        try{
            // ArrayList<ArrayList<Double>> elQueSigui = new ArrayList<>(length);

            for (int k = 0; k < ITERACIONS; k++) {
                // ArrayList<Double> elQueSigui2 = new ArrayList<>();

                Gasolineras g = new Gasolineras(100, 1234);
                CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
                Estat estat = new Estat(c, g);

                Problem p = new Problem(estat,
                        new GasolineraSuccesorFunction(),
                        new GasolineraGoalTest(),
                        new GasolineraHeuristicFunction1());

                Search search = new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(p, search);

                List<?> actions = agent.getActions();
                for (Object o : actions) {
                    String s = (String) o;
                    String[] split = s.split(" ");
                    double h = Double.parseDouble(split[1]);
                    // add h a on sigui
                }

                // afegir valors actuals on que sigui
            }

            // processem el que calgui

            for (int i = 0; i < 7; ++i) {
                String filename = "../resultats/operadors/operadors" + i + ".csv";
                File fitxer = new File(filename);
                if (fitxer.createNewFile()) System.out.println("Fitxer creat: " + filename);
                else System.out.println("Fitxer modificat: " + filename);
                FileWriter writer = new FileWriter(filename);

                // writer.write el que sigui

                writer.close();

                String filename2 = "../resultats/operadors/operadors" + i + "_plot.csv";
                File fitxer2 = new File(filename2);
                if (fitxer2.createNewFile()) System.out.println("Fitxer creat: " + filename2);
                else System.out.println("Fitxer modificat: " + filename2);
                FileWriter writer2 = new FileWriter(filename2);

                // writer2.write el que sigui

                writer2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void experimentEstatsInicials() {
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


