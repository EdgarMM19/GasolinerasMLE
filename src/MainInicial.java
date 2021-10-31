import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MainInicial {
    public static void main(String[] args) throws Exception{
        final int ITERS = 10;

        for (int k = 0; k < ITERS; ++k) {
            Gasolineras s = new Gasolineras(100, 1234);
            CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
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
            double timeElapsed = (finish - start) / 1000000.0;
            System.out.println("time elapsed " + timeElapsed + " ms");

            System.out.println();
            System.out.println("Benefici: " + Benefici.getValor(search.getGoalState()));
        }
    }
}
