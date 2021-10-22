import IA.Gasolina.Gasolinera;

/**
 * Implementa una funcio heuristica que consisteix en minimitzar la suma dels percentatges perduts en atendre les peticions no
 * servides dema en comptes d'avui.
 */
public class GasolineraHeuristicFunction4 extends GasolineraHeuristic {

    public GasolineraHeuristicFunction4() {
    }

    public double getHeuristicValue(Object estat) {
        Estat dist = (Estat) estat;

        int percentatge_perdut_total = 0;

        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            Gasolinera gasolinera = Estat.getGasolinera(i);
            for (int j = 0; j < gasolinera.getPeticiones().size(); ++j) {
                int dies_peticio = Estat.getDiesPeticio(i, j);
                if (!dist.haServitPeticio(i, j)) {
                    percentatge_perdut_total += PercentatgePreu.getPerdut(dies_peticio);
                }
            }
        }

        return percentatge_perdut_total;
    }
}
