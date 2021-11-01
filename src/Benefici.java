import IA.Gasolina.Gasolinera;

public class Benefici {
    static public double getValor(Object estat) {
        Estat dist = (Estat) estat;
        //return getDinersCobrats(dist) - getQuilometresTotals(dist) * Estat.cost_quilometre + getDinersFuturs(dist);
        return getDinersCobrats(dist) - getQuilometresTotals(dist) * Estat.cost_quilometre;

    }

    public static void printBenefici(Estat estat) {
        System.out.println("Benefici: " + getValor(estat));
        System.out.println("Diners cobrats: " + getDinersCobrats(estat));
        System.out.println("Cost quilometres totals: " + getQuilometresTotals(estat) * Estat.cost_quilometre);
        System.out.println("Diners futurs: " + getDinersFuturs(estat));
    }

    public static int getQuilometresTotals(Estat estat) {
        int cost_quilometres_totals = 0;
        for (int i = 0; i < Estat.getNumCentres(); ++i) {
            cost_quilometres_totals += estat.getRuta(i).getQuilometresRecorreguts();
        }
        return cost_quilometres_totals;
    }

    public static int getNumPeticionsServides(Estat estat, int dia) {
        int peticions_servides = 0;
        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            Gasolinera gasolinera = Estat.getGasolinera(i);
            for (int j = 0; j < gasolinera.getPeticiones().size(); ++j) {
                int dies_peticio = Estat.getDiesPeticio(i, j);
                if (estat.haServitPeticio(i, j) && (dies_peticio == dia || dia == -1)) {
                    ++peticions_servides;
                }
            }
        }
        return peticions_servides;
    }

    private static int getDinersCobrats(Estat estat) {
        int diners_cobrats = 0;
        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            Gasolinera gasolinera = Estat.getGasolinera(i);
            for (int j = 0; j < gasolinera.getPeticiones().size(); ++j) {
                if (estat.haServitPeticio(i, j)) {
                    diners_cobrats += Estat.valor_diposit * PercentatgePreu.getGuanyat(Estat.getDiesPeticio(i, j));
                }
            }
        }
        return diners_cobrats;
    }

    private static int getDinersFuturs(Estat estat) {
        int diners_futurs = 0;
        for (int i = 0; i < Estat.getNumGasolineres(); ++i) {
            Gasolinera gasolinera = Estat.getGasolinera(i);
            for (int j = 0; j < gasolinera.getPeticiones().size(); ++j) {
                if (!estat.haServitPeticio(i, j)) {
                    diners_futurs += Estat.valor_diposit * PercentatgePreu.getGuanyat(Estat.getDiesPeticio(i, j) + 1);
                }
            }
        }
        return diners_futurs;
    }


}
