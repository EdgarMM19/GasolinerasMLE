import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GasolineraAnnealingSuccesorFunction implements SuccessorFunction{
    public GasolineraAnnealingSuccesorFunction() {
    }

    public List getSuccessors(Object state) {
        ArrayList<Successor> retVal = new ArrayList<Successor>();
        Estat estat = (Estat) state;
        // Per fer el experiment 3 funcionar s'ha de descomentar la següent linia:
        //MainExperiment3.valor_iteracio.add(Benefici.getValor(estat));
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
