import IA.probTSP.ProbTSPBoard;
import IA.probTSP.ProbTSPHeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GasolineraAnnealingSuccesorFunction implements SuccessorFunction{
    public GasolineraAnnealingSuccesorFunction() {
    }

    public List getSuccessors(Object state) {
        ArrayList retVal = new ArrayList();
        Estat estat = (Estat) state;
        new GasolineraHeuristicFunction1();
        Random myRandom = new Random();
        int i, j;
        int centres = Estat.getNumCentres();
        i = myRandom.nextInt(centres);
        do {
            j = myRandom.nextInt(centres);
        } while (i == j);
        int tipus = myRandom.nextInt(6);
        if (tipus == 0) {
            retVal.add(new Successor("tipus 1", estat.getSuccessorsAnnealingGrup1(i,j)));
        }
        else if (tipus == 1) {
            retVal.add(new Successor("tipus 2", estat.getSuccessorsAnnealingGrup2(i,j)));
        }
        else if (tipus == 2) {
            retVal.add(new Successor("tipus 3", estat.getSuccessorsAnnealingGrup3(i,j)));
        }
        else if (tipus == 3) {
            retVal.add(new Successor("tipus 4", estat.getSuccessorsAnnealingGrup4(i,j)));
        }
        else if (tipus == 4) {
            retVal.add(new Successor("tipus 5", estat.getSuccessorsAnnealingGrup5(i,j)));
        }
        else {
            retVal.add(new Successor("tipus 6", estat.getSuccessorsAnnealingGrup6(i,j)));
        }
        return retVal;
    }
}
