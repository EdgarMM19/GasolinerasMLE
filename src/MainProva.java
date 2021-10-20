import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import com.sun.tools.javac.Main;

public class MainProva {

    public MainProva(){}

    public static void main(String[] args)
    {
        Gasolineras s = new Gasolineras(100, 1234);
        CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
        Estat estat = new Estat(c, s);
        estat.CreaEstatPropers();
        System.out.println(estat.AvaluaFuncioHeuristicaBeneficis());
        for (Ruta ruta : estat.GetRutes())
        {
            System.out.println(ruta.GetNumViatges() + " " + ruta.GetNumParades() + " " + ruta.GetQuilometresRecorreguts());
        }
    }
}
