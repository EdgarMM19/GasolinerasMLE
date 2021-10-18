public class UtilsGasolineres {
    static public int distancia(int x, int y, int x2, int y2)
    {
        return Math.abs(x-x2) + Math.abs(y-y2);
    }
    static public int distancia(Coordinates a, Coordinates b)
    {
        return distancia(a.GetX(), a.GetY(), b.GetX(), b.GetY());
    }

}
