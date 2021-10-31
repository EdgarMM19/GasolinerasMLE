import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

public class GasolineraSuccesorFunction implements SuccessorFunction{
    boolean op1;
    boolean op2;
    boolean op3;
    boolean op4;
    int N;

    public GasolineraSuccesorFunction() {
    }

    public GasolineraSuccesorFunction(int N, boolean op1, boolean op2, boolean op3, boolean op4) {
        this.N = N;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
    }

    public List<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<Successor>();
        Estat estat = (Estat) state;
        GasolineraHeuristic heuristic = new GasolineraHeuristicFunction1();

        if (op1) { // afegeix
            ArrayList<Estat> successors = estat.getSuccessorsAfegeix();
            for (int i = 0; i < successors.size(); ++i) {
                retval.add(new Successor("successor afegeix" + i, successors.get(i)));
            }
        }
        if (op2) { // esborra
            ArrayList<Estat> successors = estat.getSuccessorsEsborra();
            for (int i = 0; i < successors.size(); ++i) {
                retval.add(new Successor("successor esborra" + i, successors.get(i)));
            }
        }
        if (op3) { // combina
            ArrayList<Estat> successors = estat.getSuccessorsCombina();
            for (int i = 0; i < successors.size(); ++i) {
                retval.add(new Successor("successor combina" + i, successors.get(i)));
            }
        }
        if (op4) { // serveix
            ArrayList<Estat> successors = estat.getSuccessorsServeix(N);
            for (int i = 0; i < successors.size(); ++i) {
                retval.add(new Successor("successor serveix" + i, successors.get(i)));
            }
        }
        return retval;
    }
}
