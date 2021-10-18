import java.util.ArrayList;

public class Ruta {
    // L'ultima parada es sempre la inicial.
    private ArrayList<Coordinates> coords_parades;
    private int num_parades;
    private int kilometres;
    private int num_viatges;

    public Ruta(int x, int y)
    {
        num_parades = 0;
        num_viatges = 0;
        coords_parades = new ArrayList<Coordinates>(0);
        coords_parades.add(new Coordinates(x,y));
    }

    public int GetNumParades()
    {
        return num_parades;
    }

    public int GetKilometres()
    {
        return kilometres;
    }

    public Coordinates GetCoordinates(int posicio)
    {
        return coords_parades.get(posicio);
    }

    public Boolean EsPotAfegirParadaSensePassarPelCentreDeDistribucio()
    {
        if (coords_parades.size() < 3)
        {
            return true;
        }
        return coords_parades.get(coords_parades.size() - 1).EqualCoordinates(coords_parades.get(0)) || coords_parades.get(coords_parades.size() - 2).EqualCoordinates(coords_parades.get(0));
    }

    public void AfegeixParada(int x, int y)
    {
        Coordinates coords_ultima_parada = coords_parades.get(coords_parades.size()-1);
        Coordinates coords_primera_parada = coords_parades.get(0);
        Coordinates coords_nova_parada = new Coordinates(x,y);
        if (coords_primera_parada.EqualCoordinates(coords_nova_parada))
        {
            num_viatges++;
        }
        coords_parades.add(coords_nova_parada);
        kilometres -= UtilsGasolineres.distancia(coords_ultima_parada, coords_primera_parada);
        kilometres += UtilsGasolineres.distancia(coords_ultima_parada, coords_nova_parada);
        kilometres += UtilsGasolineres.distancia(coords_nova_parada, coords_primera_parada);
        num_parades++;
    }
}
