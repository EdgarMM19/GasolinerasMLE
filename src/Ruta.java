import java.util.ArrayList;
import java.util.Objects;

public class Ruta {
    // Un camió cisterna sempre comença i acaba al mateix centre de distribució. Per això, obviem incloure el centre de
    // distribució com a una última parada. Assumirem que després de l'última parada de la ruta es retorna a la de la
    // primera posició.
    private int num_viatges;
    private int num_parades;
    private ArrayList<Coordinates> parades;
    private ArrayList<Integer> id_parades;
    private int quilometres_recorreguts;

    public Ruta(Coordinates coord, int id_principi) {
        num_parades = 0;
        num_viatges = 0;
        parades = new ArrayList<>(0);
        parades.add(coord);
        id_parades = new ArrayList<>(0);
        id_parades.add(id_principi);
    }

    public int GetNumParades()
    {
        return num_parades;
    }

    public int GetNumViatges() { return num_viatges; }

    public int GetQuilometresRecorreguts()
    {
        return quilometres_recorreguts;
    }

    public Coordinates GetCoordinates(int index)
    {
        return parades.get(index);
    }

    public int GetId(int index) { return id_parades.get(index); }

    public Boolean EsPotAfegirParadaSenseTornarAlCentreDeDistribucio()
    {
        if (parades.size() <= 2)
        {
            return true;
        }
        return parades.get(parades.size() - 1).EqualsCoordinates(parades.get(0)) ||
                parades.get(parades.size() - 2).EqualsCoordinates(parades.get(0));
    }

    public int QuilometresViatgeITornadaAlCentre(Coordinates coords_nova_parada)
    {
        Coordinates coords_primera_parada = parades.get(0);
        Coordinates coords_ultima_parada = parades.get(parades.size()-1);
        return quilometres_recorreguts - Utils.GetDistancia(coords_ultima_parada, coords_primera_parada) +
                Utils.GetDistancia(coords_ultima_parada, coords_nova_parada) +
                Utils.GetDistancia(coords_nova_parada, coords_primera_parada);
    }
    public void AfegeixParada(Coordinates coords_nova_parada, int id) {
        Coordinates coords_primera_parada = parades.get(0);
        Coordinates coords_ultima_parada = parades.get(parades.size()-1);
        // Incrementem el nombre de viatges quan afegim una nova parada i l'anterior és el centre de distribució.
        if (coords_primera_parada.EqualsCoordinates(coords_ultima_parada)) {
            ++num_viatges;
        }
        parades.add(coords_nova_parada);
        id_parades.add(id);
        quilometres_recorreguts -= Utils.GetDistancia(coords_ultima_parada, coords_primera_parada);
        quilometres_recorreguts += Utils.GetDistancia(coords_ultima_parada, coords_nova_parada);
        quilometres_recorreguts += Utils.GetDistancia(coords_nova_parada, coords_primera_parada);
        ++num_parades;
    }

    public void AfegeixParadaAlCentreDeDistribucio() {
        AfegeixParada(parades.get(0), id_parades.get(0));
    }

    public void EliminaParada() {
        if (parades.size() == 1) return;
        Coordinates ultim = parades.remove(parades.size()-1);
        num_parades--;
        // Si començava un viatge s'ha d'eliminar.
        if (parades.get(parades.size()-1).EqualsCoordinates(parades.get(0))){
            num_viatges--;
        }
        // TODO(maria): abstreure actualitzacio quilometres
        quilometres_recorreguts -= Utils.GetDistancia(ultim, parades.get(parades.size()-1));
        quilometres_recorreguts -= Utils.GetDistancia(ultim, parades.get(0));
        quilometres_recorreguts += Utils.GetDistancia(parades.get(parades.size()-1), parades.get(0));
        id_parades.remove(id_parades.size()-1);
    }
}
