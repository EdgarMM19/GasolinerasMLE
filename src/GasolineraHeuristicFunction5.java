import IA.Gasolina.Gasolinera;

/**
 * Implementa una funcio heuristica que consisteix en maximitzar els beneficis obtinguts com:
 * diners_cobrats - diners_perduts - cost_total_quilometres_recorreguts
 */
public class GasolineraHeuristicFunction5 extends GasolineraHeuristic {

    public GasolineraHeuristicFunction5() {
    }

    public double getHeuristicValue(Object estat) {
        return -Benefici.getValor((Estat) estat);
    }
}
