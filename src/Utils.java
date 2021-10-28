import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;

import java.util.ArrayList;

public class Utils {
    static public int getDistancia(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    static public int getDistancia(Coordinates a, Coordinates b)
    {
        return getDistancia(a.getX(), a.getY(), b.getX(), b.getY());
    }
    static public int getDistancia(Distribucion a, Distribucion b)
    {
        return getDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int getDistancia(Gasolinera a, Gasolinera b)
    {
        return getDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int getDistancia(Gasolinera a, Distribucion b)
    {
        return getDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int getDistancia(Distribucion a, Gasolinera b)
    {
        return getDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int getDistancia(Parada a, Parada b)
    {
        return getDistancia(a.getCoordinates(), b.getCoordinates());
    }

    static public Coordinates getCoordinates(Gasolinera g)
    {
        return new Coordinates(g.getCoordX(), g.getCoordY());
    }

    static public Coordinates getCoordinates(Distribucion d)
    {
        return new Coordinates(d.getCoordX(), d.getCoordY());
    }

    static public double getMitja(ArrayList<Integer> mostra) {
        int n = mostra.size();
        double suma = 0.;
        for (Integer x : mostra)
        {
            suma += x;
        }
        return suma/n;
    }
    static public double getVariancia(ArrayList<Integer> mostra) {
        int n = mostra.size();
        double mitja = getMitja(mostra);
        double suma = 0.;
        for (Integer x : mostra)
        {
            suma += (x-mitja)*(x-mitja);
        }
        return suma/n;
    }
}
