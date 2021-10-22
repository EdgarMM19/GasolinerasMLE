import IA.Gasolina.Gasolinera;

public class Benefici {
    static public double getValor(Object estat) {
        Estat dist = (Estat) estat;
        int cost_quilometres_totals = 0;
        int diners_cobrats = 0;

        for (int i = 0; i < Estat.getNumCentres(); ++i) {
            cost_quilometres_totals += dist.getRuta(i).getQuilometresRecorreguts() * Estat.cost_quilometre;
        }

        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            Gasolinera gasolinera = Estat.getGasolinera(i);
            for (int j = 0; j < gasolinera.getPeticiones().size(); ++j) {
                if (dist.haServitPeticio(i, j)) {
                    diners_cobrats += Estat.valor_diposit * PercentatgePreu.getGuanyat( Estat.getDiesPeticio(i, j));
                }
            }
        }
        return diners_cobrats - cost_quilometres_totals;
    }
}
