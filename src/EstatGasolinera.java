import IA.Gasolina.Gasolinera;

import java.util.Arrays;

public class EstatGasolinera {
    // Conté l'índex del centre de distribució assignat a cadascun dels dipòsits d'una gasolinera.
    // Si un dipòsit no té centre de distribució assignat, hi ha -1 a la seva posició.
    private final Gasolinera gasolinera;
    private int num_peticions;
    private int num_peticions_servides;
    private int[] assignacio_peticions;

    /* Constructors */

    public EstatGasolinera(Gasolinera gasolinera)
    {
        this.gasolinera = gasolinera;
        this.num_peticions = gasolinera.getPeticiones().size();
        this.num_peticions_servides = 0;
        this.assignacio_peticions = new int[num_peticions];
        Arrays.fill(this.assignacio_peticions, -1);
    }

    public EstatGasolinera(EstatGasolinera estatGasolinera) {
        this.gasolinera = estatGasolinera.gasolinera;
        this.num_peticions = estatGasolinera.num_peticions;
        this.num_peticions_servides = estatGasolinera.num_peticions_servides;
        this.assignacio_peticions = estatGasolinera.assignacio_peticions.clone();
    }

    /* Getters */

    public int getNumPeticionsServides() {
        return num_peticions_servides;
    }

    public int[] getAssignacioPeticions() {
        return assignacio_peticions;
    }

    /* Utils */

    public boolean estaServida()
    {
        return num_peticions == num_peticions_servides;
    }

    public boolean estaServida(int index_peticio)
    {
        return assignacio_peticions[index_peticio] != -1;
    }

    public int getPeticioMesAntiga() {
        int peticio = -1;
        int dies_peticio = -1;
        for (int i = 0; i < num_peticions; ++i) {
            if (assignacio_peticions[i] == -1 ||
                    (assignacio_peticions[i] != -1 && gasolinera.getPeticiones().get(i) > dies_peticio)) {
                peticio = i;
                dies_peticio = gasolinera.getPeticiones().get(i);
            }
        }
        return peticio;
    }

    public void serveixPeticio(int index_centre) {
        assert(num_peticions > num_peticions_servides);
        ++num_peticions_servides;
        assignacio_peticions[getPeticioMesAntiga()] = index_centre;
    }

    /*
    // TODO(maria): refactoritzar aquesta funcio
    public void CamioMarxat(int camio) {
        int diposit_agafat = -1, dies_diposit = -1;
        for (int i = 0; i < numero_peticions; ++i) {
            if (assignacio_diposit[i] == camio && (dies_diposit == -1 || gasolinera.getPeticiones().get(i) < dies_diposit)) {
                diposit_agafat = i;
                dies_diposit = gasolinera.getPeticiones().get(i);
            }
        }
        if (diposit_agafat == -1) return;
        numero_peticions_satisfetes--;
        assignacio_diposit[diposit_agafat] = -1;
        int diposit_canviar = -1, dies_diposit_canvi = dies_diposit;
        for (int i = 0; i < numero_peticions; ++i) {
            if (assignacio_diposit[i] != -1 && gasolinera.getPeticiones().get(i) < dies_diposit_canvi)
            {
                dies_diposit_canvi = gasolinera.getPeticiones().get(i);
                diposit_canviar = i;
            }
        }
        if (diposit_canviar != -1) {
             assignacio_diposit[diposit_agafat] = assignacio_diposit[diposit_canviar];
             assignacio_diposit[diposit_canviar] = -1;
        }
    }

     */

}
