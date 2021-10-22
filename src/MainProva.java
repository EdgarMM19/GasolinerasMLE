import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;

public class MainProva {

    public MainProva(){}

    public static void main(String[] args)
    {
        Gasolineras s = new Gasolineras(100, 1234);
        CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
        Estat estat = new Estat(c, s);
        estat.creaAssignacioPerDefecte();

        estat.generaAssignacioInicial1();

        GasolineraHeuristic heuristic = new GasolineraHeuristicFunction1();
        System.out.println("Heuristic value: " + heuristic.getHeuristicValue(estat));
        for (Ruta ruta : estat.getRutes()) {
            System.out.println(ruta.getNumViatges() + " " + ruta.getNumParades() + " " + ruta.getQuilometresRecorreguts());
        }
    }
}
