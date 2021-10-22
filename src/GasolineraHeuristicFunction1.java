import IA.Gasolina.Gasolinera;

/**
 * Implementa una funcio heuristica que consisteix en maximitzar els beneficis obtinguts com:
 * diners_cobrats - diners_perduts - cost_total_quilometres_recorreguts
 */
public class GasolineraHeuristicFunction1 extends GasolineraHeuristic {

    public GasolineraHeuristicFunction1() {
    }

    public double getHeuristicValue(Object estat) {
        Estat dist = (Estat) estat;
        int cost_quilometres_totals = 0;
        int diners_cobrats = 0;
        int diners_perduts = 0;

        for (int i = 0; i < Estat.getNumCentres(); ++i) {
            cost_quilometres_totals += dist.getRuta(i).getQuilometresRecorreguts() * Estat.cost_quilometre;
        }

        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            Gasolinera gasolinera = Estat.getGasolinera(i);
            for (int j = 0; j < gasolinera.getPeticiones().size(); ++j) {
                int dies_peticio = Estat.getDiesPeticio(i, j);
                if (dist.haServitPeticio(i, j)) {
                    diners_cobrats += Estat.valor_diposit * PercentatgePreu.getGuanyat(dies_peticio);
                } else {
                    diners_perduts += Estat.valor_diposit * PercentatgePreu.getPerdut(dies_peticio);
                }
            }
        }

        // La funcio heuristica es minimitza. Per tal de maximitzar els beneficis, en canviem el signe.
        return -(diners_cobrats - cost_quilometres_totals - diners_perduts);
    }
}
