import java.util.Objects;

public class Parada {
    private Coordinates coordinates;
    private Integer index;

    public Parada(Coordinates coordinates, Integer index) {
        this.coordinates = coordinates;
        this.index = index;
    }

    public Parada(Parada parada) {
        this.coordinates = new Coordinates(parada.coordinates);
        this.index = parada.index;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parada parada = (Parada) o;
        return coordinates.equals(parada.coordinates) && Objects.equals(index, parada.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, index);
    }
}
