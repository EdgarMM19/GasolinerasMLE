import aima.search.framework.HeuristicFunction;

public class GasolineraHeuristicFunction implements HeuristicFunction {
    public double getHeuristicValue(Object n){
        return ((Estat) n).AvaluaFuncioHeuristicaBeneficis();
    }
}
