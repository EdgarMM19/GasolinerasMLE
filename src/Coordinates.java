public class Coordinates {
    private int x;
    private int y;
    public Coordinates(int _x, int _y)
    {
        x = _x;
        y = _y;
    }
    public void SetX(int _x)
    {
        x = _x;
    }
    public void SetY(int _y)
    {
        y = _y;
    }
    public int GetX()
    {
        return x;
    }
    public int GetY()
    {
        return y;
    }
    public Boolean EqualsCoordinates(Coordinates coordinates)
    {
        return coordinates.GetX() == GetX() && coordinates.GetY() == GetY();
    }
}
