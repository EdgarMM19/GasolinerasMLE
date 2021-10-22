/**
 * Implementa una funcio heuristica que consisteix en maximitzar el nombre de diposits servits.
 */
public class GasolineraHeuristicFunction2 extends GasolineraHeuristic {

    public GasolineraHeuristicFunction2() {
    }

    public double getHeuristicValue(Object estat) {
        Estat dist = (Estat) estat;
        int num_diposits_servits = 0;

        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            num_diposits_servits += dist.getNumPeticionsServides(i);
        }

        // La funcio heuristica es minimitza. Per tal de maximitzar els nombre de diposits servits, en canviem el signe.
        return -(num_diposits_servits);
    }
}
