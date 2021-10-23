import IA.Gasolina.Distribucion;

import java.util.ArrayList;

public class Ruta {
    // Un camió cisterna sempre comença i acaba al mateix centre de distribució. Per això, obviem incloure el centre de
    // distribució com a una última parada. Assumirem que després de l'última parada de la ruta es retorna a la de la
    // primera posició.
    private int num_viatges;
    private ArrayList<Parada> parades;
    private int quilometres_recorreguts;

    // La primera parada d'una ruta ha de ser un centre.
    public Ruta(Distribucion centre, int index_centre) {
        num_viatges = 0;
        parades = new ArrayList<>();
        parades.add(new Parada(Utils.getCoordinates(centre), index_centre));
        quilometres_recorreguts = 0;
    }

    public Ruta(Ruta ruta) {
        this.num_viatges = ruta.num_viatges;
        this.parades = new ArrayList<>();
        for (int i = 0; i < ruta.parades.size(); ++i) {
            this.parades.add(new Parada(ruta.parades.get(i)));
        }
        this.quilometres_recorreguts = ruta.quilometres_recorreguts;
    }

    public int getNumParades()
    {
        return parades.size();
    }

    public int getNumViatges() { return num_viatges; }

    public ArrayList<Parada> getParades() {
        return parades;
    }

    public Parada getParada(int index)
    {
        return parades.get(index);
    }

    public Parada getCentre()
    {
        return getParada(0);
    }

    public Parada getUltimaParada() { return getParada(parades.size() - 1); }

    public Parada getPenultimaParada() { return getParada(parades.size() - 2); }

    public int getQuilometresRecorreguts()
    {
        return quilometres_recorreguts;
    }

    public boolean esCentre(Parada parada) {
        return parada.equals(getCentre());
    }

    /**
     * Retorna els quilometres que s'afegirien als recorreguts si es fes p1 -> p2 -> p3 enlloc de p1 -> p3.
     */
    public int getQuilometresParadaIntermitja(Parada p1, Parada p2, Parada p3) {
        return Utils.getDistancia(p1, p2) + Utils.getDistancia(p2, p3) - Utils.getDistancia(p1, p3);
    }

    public void afegeixParada(Parada parada) {
        assert esCentre(parada) || (parada.getIndex() >= Estat.getNumCentres());
        // Incrementem el nombre de viatges si l'ultima parada es el centre.
        if (esCentre(getUltimaParada())) {
            ++num_viatges;
        }
        quilometres_recorreguts += getQuilometresParadaIntermitja(getUltimaParada(), parada, getCentre());
        parades.add(parada);
    }

    public void afegeixParadaAlCentre() {
        afegeixParada(getCentre());
    }

    public void eliminaParada() {
        if (parades.size() == 1) return;
        Parada parada_eliminada = parades.remove(parades.size() - 1);
        // Si era la primera parada d'un nou viatge, disminuïm el nombre de viatges.
        if (esCentre(getUltimaParada())) {
            --num_viatges;
        }
        quilometres_recorreguts -= getQuilometresParadaIntermitja(getUltimaParada(), parada_eliminada, getCentre());
    }

    public boolean calTornarAlCentre() {
        if (parades.size() <= 2) {
            return false;
        }
        return !esCentre(getUltimaParada()) && !esCentre(getPenultimaParada());
    }

    public void buida() {
        num_viatges = 0;
        Parada centre = new Parada(getCentre());
        parades = new ArrayList<>();
        parades.add(centre);
        quilometres_recorreguts = 0;
    }

    public boolean haAcabat() {
        return num_viatges == Estat.max_num_viatges;
    }

    public boolean podriaViatjar(Parada parada) {
        return quilometres_recorreguts + getQuilometresParadaIntermitja(getUltimaParada(), parada, getCentre()) <=
                Estat.max_quilometres;
    }

    public void printRuta() {
        System.out.print("quilometres recorreguts: " + quilometres_recorreguts);
        System.out.print(" | " + getNumParades() + " parades:");
        for (Parada parada : parades) {
            if (esCentre(parada)) {
                System.out.print(" centre");
            } else {
                System.out.print(" " + Estat.getReverseIndexGasolinera(parada.getIndex()));
            }
        }
        System.out.println();
    }
}
