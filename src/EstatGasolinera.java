import IA.Gasolina.Gasolinera;

import java.util.Arrays;

public class EstatGasolinera {
    // conté l'índex del centre de distribució
    // assignat a cadascun dels seus dipòsits. Si un dipòsit no té centre de distribució assignat, hi ha -1 a la seva
    // posició.
    private final int[] assignacio_diposit;
    private final int numero_peticions;
    private int numero_peticions_satisfetes;
    private final Gasolinera gasolinera;

    public EstatGasolinera(Gasolinera gasolinera)
    {
        this.gasolinera = gasolinera;
        numero_peticions = gasolinera.getPeticiones().size();
        numero_peticions_satisfetes = 0;
        assignacio_diposit = new int[numero_peticions];
        Arrays.fill(assignacio_diposit, -1);
    }

    public int[] GetAssignacioDiposit()
    {
        return assignacio_diposit;
    }
    public Boolean GasolineraSatisfeta()
    {
        return numero_peticions == numero_peticions_satisfetes;
    }
    public void CamioArribat(int camio)
    {
        assert(numero_peticions > numero_peticions_satisfetes);
        numero_peticions_satisfetes++;

        int diposit_agafat = -1, dies_diposit = -1;
        for (int i = 0; i < numero_peticions; ++i) {
            if (assignacio_diposit[i] == -1 && gasolinera.getPeticiones().get(i) > dies_diposit) {
                diposit_agafat = i;
                dies_diposit = gasolinera.getPeticiones().get(i);
            }
        }
        assignacio_diposit[diposit_agafat] = camio;
    }
}
