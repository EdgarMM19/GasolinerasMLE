/**
 * Implementa una funcio heuristica que consisteix en minimitzar el ratio entre el total de quilometres recorreguts i
 * el total de diposits servits.
 */
public class GasolineraHeuristicFunction3 extends GasolineraHeuristic {

    public GasolineraHeuristicFunction3() {
    }

    public double getHeuristicValue(Object estat) {
        Estat dist = (Estat) estat;
        int num_diposits_servits = 0;
        int quilometres_recorreguts = 0;

        for (int i = 0; i < Estat.getNumCentres(); ++i) {
            quilometres_recorreguts += dist.getRuta(i).getQuilometresRecorreguts();
        }

        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            num_diposits_servits += dist.getNumPeticionsServides(i);
        }
        return (double)quilometres_recorreguts/num_diposits_servits;
    }
}

