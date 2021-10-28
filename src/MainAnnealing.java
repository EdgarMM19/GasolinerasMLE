import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MainAnnealing {
    public static void main(String[] args) throws Exception{

        for (int k)
        Gasolineras s = new Gasolineras(100, 1234);
        CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
        Estat estat = new Estat(c, s);
        //System.out.println(Benefici.getValor(estat));
        estat.generaAssignacioInicial1();
        //estat.generaAssignacioInicialPropers();
        //System.out.println(Benefici.getValor(estat));
        //estat.printResultats();
        // Create the Problem object

        Problem p = new Problem(estat,
                new GasolineraAnnealingSuccesorFunction(),
                new GasolineraGoalTest(),
                new GasolineraHeuristicFunction1());
        SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(2000, 100, 5, 0.001D);
        SearchAgent agent = new SearchAgent(p, search);
        //System.out.println();
        //printInstrumentation(agent.getInstrumentation());
        //((Estat) search.getGoalState()).printResultats();

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
