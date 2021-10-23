import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ExperimentsAima {
    public static void main(String[] args) throws Exception{

        double beneficis1, beneficis2, beneficis3, beneficis4;
        beneficis1 = beneficis2 = beneficis3 = beneficis4 = 0;
        for (int i = 0; i < 1000; ++i) {
            Gasolineras s = new Gasolineras(100, 1234);
            CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
            Estat estat1 = new Estat(c, s);
            estat1.generaAssignacioInicial1();
            Estat estat2 = new Estat(estat1);
            Estat estat3 = new Estat(estat1);
            Estat estat4 = new Estat(estat1);
            // Create the Problem object
            Problem p1 = new Problem(estat1,
                    new GasolineraSuccesorFunction(),
                    new GasolineraGoalTest(),
                    new GasolineraHeuristicFunction1());
            Problem p2 = new Problem(estat1,
                    new GasolineraSuccesorFunction(),
                    new GasolineraGoalTest(),
                    new GasolineraHeuristicFunction1());
            Problem p3 = new Problem(estat1,
                    new GasolineraSuccesorFunction(),
                    new GasolineraGoalTest(),
                    new GasolineraHeuristicFunction1());
            Problem p4 = new Problem(estat1,
                    new GasolineraSuccesorFunction(),
                    new GasolineraGoalTest(),
                    new GasolineraHeuristicFunction1());

            // Instantiate the search algorithm
            Search search1 = new HillClimbingSearch();
            Search search2 = new HillClimbingSearch();
            Search search3 = new HillClimbingSearch();
            Search search4 = new HillClimbingSearch();

            // Instantiate the SearchAgent object
            SearchAgent agent1 = new SearchAgent(p1, search1);
            SearchAgent agent2 = new SearchAgent(p2, search2);
            SearchAgent agent3 = new SearchAgent(p3, search3);
            SearchAgent agent4 = new SearchAgent(p4, search4);

            // We print the results of the search
           // System.out.println();
            //printActions(agent1.getActions());
           // printInstrumentation(agent1.getInstrumentation());

            //System.out.println();
            beneficis1 += Benefici.getValor((Estat) search1.getGoalState());
            beneficis2 += Benefici.getValor((Estat) search2.getGoalState());
            beneficis3 += Benefici.getValor((Estat) search3.getGoalState());
            beneficis4 += Benefici.getValor((Estat) search4.getGoalState());
        }
        System.out.println(beneficis1);
        System.out.println(beneficis2);
        System.out.println(beneficis3);
        System.out.println(beneficis4);
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
