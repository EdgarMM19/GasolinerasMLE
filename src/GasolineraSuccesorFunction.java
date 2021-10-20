import IA.TSP2.ProbTSPHeuristicFunction;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

public class GasolineraSuccesorFunction implements SuccessorFunction{
    public GasolineraSuccesorFunction() {
    }
    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        Estat estat = (Estat) state;
        new GasolineraHeuristicFunction();
        ArrayList<Estat> successors = estat.GetSuccessors();
        for (int i = 0; i < successors.size(); ++i)
        {
            retval.add(new Successor("successor " + i, successors.get(i)));
        }
        return retval;
    }
}
