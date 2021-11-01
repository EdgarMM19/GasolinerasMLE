import IA.Gasolina.Gasolinera;

import java.util.Arrays;

public class EstatGasolinera {
    // Conté l'índex del centre de distribució assignat a cadascun dels dipòsits d'una gasolinera.
    // Si un dipòsit no té centre de distribució assignat, hi ha -1 a la seva posició.
    private final Gasolinera gasolinera;
    private final int num_peticions;
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
        assert(num_peticions == assignacio_peticions.length);
    }

    /* Getters */

    public int getNumPeticionsServides() {
        return num_peticions_servides;
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

    public int getPeticioNoServidaMesAntiga() {
        int peticio = -1;
        int dies_peticio = -1;
        for (int i = 0; i < num_peticions; ++i) {
            if (assignacio_peticions[i] == -1 && gasolinera.getPeticiones().get(i) > dies_peticio) {
                peticio = i;
                dies_peticio = gasolinera.getPeticiones().get(i);
            }
        }
        return peticio;
    }
    public int getDiesPeticioNoServidaMesAntiga() {
        assert(estaServida());
        int dies_peticio = -1;
        for (int i = 0; i < num_peticions; ++i) {
            if (assignacio_peticions[i] == -1 && gasolinera.getPeticiones().get(i) > dies_peticio) {
                dies_peticio = gasolinera.getPeticiones().get(i);
            }
        }
        return dies_peticio;
    }

    public void serveixPeticio(int index_centre) {
        assert(num_peticions > num_peticions_servides);
        ++num_peticions_servides;
        assignacio_peticions[getPeticioNoServidaMesAntiga()] = index_centre;
    }

    /*
     * Esborra el servei de la peticio mes recent fet pel centre donat.
     */
    public void esborraServei(int index_centre) {
        int peticio = -1;
        int dies_peticio = -1;
        for (int i = 0; i < num_peticions; ++i) {
            if (assignacio_peticions[i] == index_centre && (dies_peticio == -1 || gasolinera.getPeticiones().get(i) < dies_peticio)) {
                peticio = i;
                dies_peticio = gasolinera.getPeticiones().get(i);
            }
        }
        if (peticio != -1) {
            --num_peticions_servides;
            assignacio_peticions[peticio] = -1;
        }
    }

    public void printPeticions() {
        for (int i = 0; i < num_peticions; ++i) {
            System.out.print("Peticio amb dies ");
            System.out.print(gasolinera.getPeticiones().get(i));
            System.out.print(" assignada a ");
            System.out.print(assignacio_peticions[i]);
            System.out.println();
        }
    }

    public void printPeticionsNoServides() {
        for (int i = 0; i < num_peticions; ++i) {
            if (assignacio_peticions[i] == -1) {
                System.out.print(" " + gasolinera.getPeticiones().get(i));
            }
        }
    }
}
