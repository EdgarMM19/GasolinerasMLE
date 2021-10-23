import IA.Gasolina.Gasolinera;

/**
 * Implementa una funcio heuristica que consisteix en minimitzar la suma dels percentatges perduts en atendre les peticions no
 * servides dema en comptes d'avui.
 */
public class GasolineraHeuristicFunctionTroll extends GasolineraHeuristic {

    public GasolineraHeuristicFunctionTroll() {
    }

    public double getHeuristicValue(Object estat) {
        Estat dist = (Estat) estat;

        int cost_quilometres_totals = 0;

        for (int i = 0; i < Estat.getNumCentres(); ++i) {
            cost_quilometres_totals += dist.getRuta(i).getQuilometresRecorreguts() * Estat.cost_quilometre;
        }

        return cost_quilometres_totals;
    }
}
