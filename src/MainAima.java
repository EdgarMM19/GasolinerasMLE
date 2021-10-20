import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MainAima {
    public static void main(String[] args) throws Exception{

        Gasolineras s = new Gasolineras(100, 1234);
        CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
        Estat estat = new Estat(c, s);
        estat.CreaEstatPropers();

        // Create the Problem object
        Problem p = new Problem(estat,
                new GasolineraSuccesorFunction(),
                new GasolineraGoalTest(),
                new GasolineraHeuristicFunction());

        // Instantiate the search algorithm
        Search search = new HillClimbingSearch();

        // Instantiate the SearchAgent object
        SearchAgent agent = new SearchAgent(p, search);

        // We print the results of the search
        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());


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
