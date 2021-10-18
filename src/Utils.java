import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;

public class Utils {
    static public int GetDistancia(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    static public int GetDistancia(Coordinates a, Coordinates b)
    {
        return GetDistancia(a.GetX(), a.GetY(), b.GetX(), b.GetY());
    }
    static public int GetDistancia(Distribucion a, Distribucion b)
    {
        return GetDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int GetDistancia(Gasolinera a, Gasolinera b)
    {
        return GetDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int GetDistancia(Gasolinera a, Distribucion b)
    {
        return GetDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
    static public int GetDistancia(Distribucion a, Gasolinera b)
    {
        return GetDistancia(a.getCoordX(), a.getCoordY(), b.getCoordX(), b.getCoordY());
    }
}
