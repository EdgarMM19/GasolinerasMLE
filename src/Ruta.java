import java.util.ArrayList;

public class Ruta {
    // Un camió cisterna sempre comença i acaba al mateix centre de distribució. Per això, obviem incloure el centre de
    // distribució com a una última parada. Assumirem que després de l'última parada de la ruta es retorna a la de la
    // primera posició.
    private int num_viatges;
    private int num_parades;
    private ArrayList<Coordinates> parades;
    private int quilometres_recorreguts;

    public Ruta(int x, int y) {
        num_parades = 0;
        num_viatges = 0;
        parades = new ArrayList<Coordinates>(0);
        parades.add(new Coordinates(x, y));
    }

    public int GetNumParades()
    {
        return num_parades;
    }

    public int GetQuilometresRecorreguts()
    {
        return quilometres_recorreguts;
    }

    public Coordinates GetCoordinates(int index)
    {
        return parades.get(index);
    }

    public Boolean EsPotAfegirParadaSenseTornarAlCentreDeDistribucio()
    {
        if (parades.size() <= 2)
        {
            return true;
        }
        return parades.get(parades.size() - 1).EqualsCoordinates(parades.get(0)) ||
                parades.get(parades.size() - 2).EqualsCoordinates(parades.get(0));
    }

    public void AfegeixParada(int x, int y) {
        Coordinates coords_primera_parada = parades.get(0);
        Coordinates coords_ultima_parada = parades.get(parades.size()-1);
        Coordinates coords_nova_parada = new Coordinates(x, y);
        // Incrementem el número de viatges quan afegim una nova parada i l'anterior és el centre de distribució.
        if (coords_primera_parada.EqualsCoordinates(coords_ultima_parada)) {
            ++num_viatges;
        }
        parades.add(coords_nova_parada);
        quilometres_recorreguts -= Utils.GetDistancia(coords_ultima_parada, coords_primera_parada);
        quilometres_recorreguts += Utils.GetDistancia(coords_ultima_parada, coords_nova_parada);
        quilometres_recorreguts += Utils.GetDistancia(coords_nova_parada, coords_primera_parada);
        ++num_parades;
    }
}
