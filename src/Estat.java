import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Estat {
    public static final int max_quilometres = 640;
    public static final int max_num_viatges = 5;
    public static final int valor_diposit = 1000;
    public static final int cost_quilometre = 2;
    private static int num_centres;
    private static CentrosDistribucion centres;
    private static int num_gasolineres;
    private static Gasolineras gasolineres;
    // La matriu 'distancies' guarda la distància de Manhattan precalculada entre dos elements de l'àrea geogràfica.
    // Els centres s'indexen amb els elements 0..(num_centres - 1).
    // Les gasolineres s'indexen amb els elements num_centres..(num_centres + num_gasolineres - 1).
    private static int[][] distancies;
    // El vector 'rutes' guarda, per cada centre de distribució, quina ruta té assignada el seu camió cisterna.
    private Ruta[] rutes;
    // El vector 'estat_gasolineres' guarda l'estat de cada gasolinera.
    private EstatGasolinera[] estat_gasolineres;

    /* Indexadors */

    static public int getIndexGasolinera(int index) {
        return num_centres + index;
    }

    static public int getReverseIndexGasolinera(int index) {
        return index - num_centres;
    }

    /* Constructors */

    public Estat(CentrosDistribucion centres, Gasolineras gasolineres) {
        num_centres = centres.size();
        Estat.centres = centres;
        num_gasolineres = gasolineres.size();
        Estat.gasolineres = gasolineres;

        // Precalculem totes les distàncies per evitar càlculs innecessaris mes endavant.
        distancies = new int[num_centres + num_gasolineres][num_centres + num_gasolineres];
        for (int i = 0; i < num_centres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                distancies[i][j] = distancies[j][i] = Utils.getDistancia(Estat.centres.get(i), Estat.centres.get(j));
            }
            for (int j = 0; j < num_gasolineres; ++j) {
                distancies[i][getIndexGasolinera(j)] = distancies[getIndexGasolinera(j)][i] =
                        Utils.getDistancia(Estat.centres.get(i), Estat.gasolineres.get(j));
            }
        }

        for (int i = 0; i < num_gasolineres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                distancies[getIndexGasolinera(i)][j] = distancies[j][getIndexGasolinera(i)] =
                        Utils.getDistancia(Estat.gasolineres.get(i), Estat.centres.get(j));
            }
            for (int j = 0; j < num_gasolineres; ++j) {
                distancies[getIndexGasolinera(i)][getIndexGasolinera(j)] =
                        distancies[getIndexGasolinera(j)][getIndexGasolinera(i)] =
                                Utils.getDistancia(Estat.gasolineres.get(i), Estat.gasolineres.get(j));
            }
        }

        creaAssignacioPerDefecte();
    }

    public Estat(Estat estat) {
        this.rutes = new Ruta[estat.rutes.length];
        for (int i = 0; i < estat.rutes.length; ++i) {
            this.rutes[i] = new Ruta(estat.rutes[i]);
        }
        this.estat_gasolineres = new EstatGasolinera[estat.estat_gasolineres.length];
        for (int i = 0; i < estat.estat_gasolineres.length; ++i) {
            this.estat_gasolineres[i] = new EstatGasolinera(estat.estat_gasolineres[i]);
        }
    }

    /* Getters */

    public static int getNumCentres() {
        return num_centres;
    }

    public static int getNumGasolineres() {
        return num_gasolineres;
    }

    public Ruta[] getRutes()
    {
        return rutes;
    }

    public Ruta getRuta(int index)
    {
        assert(index >= 0 && index < num_centres);
        return rutes[index];
    }

    public static Gasolinera getGasolinera(int index)
    {
        assert(index >= 0 && index < num_gasolineres);
        return gasolineres.get(index);
    }

    /* Print */

    public void printResultats() {
        for (int i = 0; i < num_centres; ++i) {
            System.out.println("* Ruta del centre " + i + " *");
            rutes[i].printRuta();
        }

        System.out.println();

        for (int i = 0; i < num_gasolineres; ++i) {
            if (!estat_gasolineres[i].estaServida()) {
                System.out.print("La gasolinera " + i + " no te servides les peticions amb dies:");
                estat_gasolineres[i].printPeticionsNoServides();
                System.out.println();
            }
        }
        System.out.println();

        Benefici.printBenefici(this);
        System.out.println();
    }

    public void printAssignacionsGasolinera(int index_gasolinera) {
        estat_gasolineres[index_gasolinera].printPeticions();
    }


    /* Utils */

    public void creaAssignacioPerDefecte() {
        estat_gasolineres = new EstatGasolinera[num_gasolineres];
        // Omplim l'assignació de dipòsits amb els valors i mides inicials.
        for (int i = 0; i < num_gasolineres; ++i) {
            estat_gasolineres[i] = new EstatGasolinera(Estat.gasolineres.get(i));
        }

        // Per cada centre de distribució, li assignem la ruta inicial, que correspon a que el camió cisterna no s'ha
        // mogut del centre.
        rutes = new Ruta[num_centres];
        for (int i = 0; i < num_centres; ++i) {
            rutes[i] = new Ruta(Estat.centres.get(i), i);
        }
    }

    public int getDistanciaEntreElements(int i, int j) {
        return distancies[i][j];
    }

    public boolean haServitPeticio(int index_gasolinera, int index_peticio) {
        return estat_gasolineres[index_gasolinera].estaServida(index_peticio);
    }

    public static int getDiesPeticio(int index_gasolinera, int index_peticio) {
        return getGasolinera(index_gasolinera).getPeticiones().get(index_peticio);
    }

    public int getNumPeticionsServides(int index_gasolinera) {
        return estat_gasolineres[index_gasolinera].getNumPeticionsServides();
    }

    public int getIndexGasolineraNoServidaMesPropera(int index) {
        int index_gasolinera = -1;
        for (int i = 0; i < num_gasolineres; ++i) {
            if (!estat_gasolineres[i].estaServida() &&
                    (index_gasolinera == -1 ||
                            (getDistanciaEntreElements(index, getIndexGasolinera(i)) <
                                    getDistanciaEntreElements(index, index_gasolinera)))) {
                index_gasolinera = i;
            }
        }
        return index_gasolinera;
    }

    /* Retorna cert si s'ha pogut afegir la parada correctament i, altrament, retorna fals.
     * S'assigna la peticio mes antiga de la gasolinera donada.
     */
    public boolean afegeixParada(int index_centre, int index_gasolinera) {
        if (rutes[index_centre].calTornarAlCentre()) {
            return false;
        }

        if (rutes[index_centre].getUltimaParada().equals(rutes[index_centre].getCentre())) {
            if (rutes[index_centre].getNumViatges() + 1 > max_num_viatges) {
                return false;
            }
        }

        Parada parada = new Parada(Utils.getCoordinates(gasolineres.get(index_gasolinera)), getIndexGasolinera(index_gasolinera));
        if (rutes[index_centre].getQuilometresRecorreguts() +
                rutes[index_centre].getQuilometresParadaIntermitja(
                        rutes[index_centre].getUltimaParada(), parada, rutes[index_centre].getCentre()) > max_quilometres) {
            return false;
        }
        rutes[index_centre].afegeixParada(parada);
        estat_gasolineres[index_gasolinera].serveixPeticio(index_centre);
        return true;
    }

    /* Retorna cert si s'ha pogut afegir un nou viatge i, altrament, retorna fals.
     * S'assigna la peticio mes antiga de la gasolinera mes propera.
     */
    public boolean afegeixViatge(int index_centre) {
        int index_gasolinera = getIndexGasolineraNoServidaMesPropera(rutes[index_centre].getUltimaParada().getIndex());
        if (index_gasolinera == -1) {
            return false;
        }
        if (rutes[index_centre].calTornarAlCentre()) {
            rutes[index_centre].afegeixParadaAlCentre();
        }
        if (!afegeixParada(index_centre, index_gasolinera)) {
            return false;
        }
        completaViatge(index_centre);
        return true;
    }

    public void esborraUltimViatge(int index_centre) {
        if (rutes[index_centre].getNumViatges() > 0) {
            if (rutes[index_centre].getUltimaParada().equals(rutes[index_centre].getCentre())) {
                rutes[index_centre].eliminaParada();
            }
            while (!rutes[index_centre].getUltimaParada().equals(rutes[index_centre].getCentre())) {
                int index_gasolinera = rutes[index_centre].getUltimaParada().getIndex()-num_centres;
                estat_gasolineres[index_gasolinera].esborraServei(index_centre);
                rutes[index_centre].eliminaParada();
            }
        }
    }

    /* Intenta completar el viatge del camio cisterna del centre donat. El viatge s'ha de poder completar. */
    public void completaViatge(int index_centre) {
        if (!rutes[index_centre].calTornarAlCentre()) {
            int index_gasolinera = getIndexGasolineraNoServidaMesPropera(rutes[index_centre].getUltimaParada().getIndex());
            if (index_gasolinera != -1) {
                afegeixParada(index_centre, index_gasolinera);
            }
        }
    }

    public void esborraRutaCentre(int index_centre) {
        for (int i = 0; i < rutes[index_centre].getParades().size(); ++i) {
            if (!rutes[index_centre].getParada(i).equals(rutes[index_centre].getCentre())) {
                estat_gasolineres[getReverseIndexGasolinera(rutes[index_centre].getParada(i).getIndex())].esborraServei(index_centre);
            }
        }
        rutes[index_centre].buida();
    }

    public void emplenaRutaCentre(int index_centre) {
        while(afegeixViatge(index_centre));
    }

    /* Generadors de distribucions inicials */

    /**
     * Genera una distribucio inicial que consisteix en assignar, a cada peticio de cada gasolinera, el centre de
     * distribucio disponible mes proper. A cada moment, assignem la peticio amb la minima distancia al centre mes
     * proper. Aprofitem el viatge d'un camio per anar a la seguent peticio mes propera.
     */
    public void generaAssignacioInicial1() {
        /* Reestablim l'assignacio a l'assignacio per defecte. */
        creaAssignacioPerDefecte();

        class AssignacioCandidata {
            final int index_centre;
            final int index_gasolinera;
            // Guardem el nombre de parades del centre per distingir les assignacions candidates desactualitzades.
            final int num_parades;

            AssignacioCandidata(int index_centre, int index_gasolinera, int num_parades) {
                this.index_centre = index_centre;
                this.index_gasolinera = index_gasolinera;
                this.num_parades = num_parades;
            }

            AssignacioCandidata(int index_centre) {
                this.index_centre = index_centre;
                this.index_gasolinera = getIndexGasolineraNoServidaMesPropera(index_centre);
                this.num_parades = rutes[index_centre].getNumParades();
            }
        }

        class ComparadorAssignacioCandidata implements Comparator<AssignacioCandidata> {
            public int compare(AssignacioCandidata ac1, AssignacioCandidata ac2) {
                int dist1 = getDistanciaEntreElements(ac1.index_centre, getIndexGasolinera(ac1.index_gasolinera));
                int dist2 = getDistanciaEntreElements(ac2.index_centre, getIndexGasolinera(ac2.index_gasolinera));
                if (dist1 < dist2) {
                    return 1;
                }
                if (dist1 > dist2) {
                    return -1;
                }
                if (ac1.num_parades < ac2.num_parades) {
                    return 1;
                }
                if (ac1.num_parades > ac2.num_parades) {
                    return -1;
                }
                return Integer.compare(ac1.index_centre, ac2.index_centre);
            }
        }

        PriorityQueue<AssignacioCandidata> pq = new PriorityQueue<>(new ComparadorAssignacioCandidata());
        for (int i = 0; i < num_centres; ++i) {
            int index_gasolinera_mes_propera = getIndexGasolineraNoServidaMesPropera(i);
            if (index_gasolinera_mes_propera != -1) {
                pq.add(new AssignacioCandidata(i, index_gasolinera_mes_propera, rutes[i].getNumParades()));
            }
        }

        while (!pq.isEmpty()) {
            AssignacioCandidata actual = pq.remove();

            /* Comprovem que l'assignacio candidata esta actualitzada. */
            if (rutes[actual.index_centre].getNumParades() != actual.num_parades) {
                continue;
            }
            /* Comprovem que la gasolinera de l'assignacio candidata no esta servida. */
            if (!estat_gasolineres[actual.index_gasolinera].estaServida()) {
                if (rutes[actual.index_centre].calTornarAlCentre()) {
                    rutes[actual.index_centre].afegeixParadaAlCentre();
                }
                /* Afegim parada a la gasolinera de l'assignacio candidata */
                if (afegeixParada(actual.index_centre, actual.index_gasolinera)) {
                    /* Si hem pogut afegir una nova parada amb exit, completem el viatge servint la seguent peticio mes
                     * propera, si n'hi ha alguna de possible, i tornem al centre. */
                    completaViatge(actual.index_centre);
                }
                if (rutes[actual.index_centre].haAcabat()) continue;
            }

            /* Busquem una nova assignacio per aquest centre */
            AssignacioCandidata seguent_assignacio = new AssignacioCandidata(actual.index_centre);
            if (seguent_assignacio.index_gasolinera != -1 &&
                    rutes[actual.index_centre].podriaViatjar(
                            new Parada(Utils.getCoordinates(gasolineres.get(seguent_assignacio.index_gasolinera)),
                                    getIndexGasolinera(seguent_assignacio.index_gasolinera)))) {
                pq.add(seguent_assignacio);
            }
        }
    }

    private void emplenaAssignacionsGasolinera(int j) {
    }

    private void intentaAfegirViatgeGasolinera(int j) {
        int centre_proper = getIndexCentreDisponibleMesProper(j);
        if (centre_proper != -1) {
            afegeixViatge(centre_proper);
        }
    }

    private void esborraAssignacionsGasolinera(int i) {

    }

    // TODO(maria): refactoritzar



    private int getIndexCentreDisponibleMesProper(int index) {
        int centre_mes_proper = -1;
        for (int i = 0; i < num_centres; ++i) {
            if (!rutes[i].haAcabat() &&
                    (centre_mes_proper == -1 ||
                            getDistanciaEntreElements(getIndexGasolinera(index),i) < getDistanciaEntreElements(getIndexGasolinera(index), centre_mes_proper))) {
                centre_mes_proper = i;
            }
        }
        return centre_mes_proper;
    }
    /*
    public Boolean AfegeixViatgeGasolinera(int index)
    {
        if (!estat_gasolineres[index].GasolineraSatisfeta()) {
            int index_centre = GetCentreDisponibleMesProper(index);
            if (index_centre != -1) {
                Coordinates coordinates_gasolinera = Coordinates.GetCoordsGasolinera(gasolineres.get(index));
                if (EsPotAfegirViatge(rutes[index_centre], coordinates_gasolinera)) {
                    rutes[index_centre].AfegeixParada(coordinates_gasolinera, num_centres + index);
                    estat_gasolineres[index].CamioArribat(index_centre);
                    return true;
                }
            }
        }
        return false;
    }

    */

    /* Successors */

    public ArrayList<Estat> getSuccessors() {
        GasolineraHeuristic heuristic = new GasolineraHeuristicFunction1();
        System.out.println("Getting successors " + heuristic.getHeuristicValue(this));
        return getSuccessors1();
    }

    public ArrayList<Estat> getSuccessors1() {
        ArrayList<Estat> successors = new ArrayList<>();
        /* Generem un nou successor per cada parella de centres. El successor es l'estat resultant d'esborrar les rutes
         * dels dos centres i de tornar-les a emplenar. */
        for (int i = 0; i < num_centres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                if (i == j) continue;
                Estat successor = new Estat(this);
                successor.esborraRutaCentre(i);
                successor.esborraRutaCentre(j);
                successor.emplenaRutaCentre(i);
                successor.emplenaRutaCentre(j);
                successors.add(successor);
            }
        }
        /* Generem un nou successor per cada parella de gasolineres. El successor es l'estat resultant d'esborrar
         * les assignacions de les dues gasolineres i tornar-les a emplenar amb els centres disponibles mes propers. */
        for (int i = 0; i < num_gasolineres; ++i) {
            for (int j = 0; j < num_gasolineres; ++j) {
                if (i == j) continue;
                //Estat successor = new Estat(this);
                //successor.esborraAssignacionsGasolinera(i);
                //successor.esborraAssignacionsGasolinera(j);
                // successor.emplenaAssignacionsGasolinera(i);
                // successor.emplenaAssignacionsGasolinera(j);
                // successors.add(successor);
            }
        }
        int max_viatges_a_borrar = 3;

        /* Generem un nou successor borrant iterativament l'ultim viatge de cada centre i intentant emplenar gasolineres
        * amb aquest viatges extra */
        Estat iterador = new Estat(this);
        for (int k = 0; k < max_viatges_a_borrar; ++k) {
            for (int i = 0; i < num_centres; ++i) {
                iterador.esborraUltimViatge(i);
                Estat successor = new Estat(iterador);
                for (int j = 0; j < num_gasolineres; ++j) {
                    successor.intentaAfegirViatgeGasolinera(j);
                }
                successors.add(successor);
            }
        }
        return successors;

    }

}
