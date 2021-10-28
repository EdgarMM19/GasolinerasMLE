import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class MainProva {

    public MainProva(){}

    public static void main(String[] args)
    {
        for (int i = 400; i <= 8000; i += 400) {
            ArrayList<Integer> dades = new ArrayList<>();
            for (int j = 0; j < 10; ++j) {
                int seed = ThreadLocalRandom.current().nextInt(0,  10000);;
                Gasolineras s = new Gasolineras(i, seed);
                CentrosDistribucion c = new CentrosDistribucion(500, 1, seed);
                Estat estat = new Estat(c, s);
                dades.add(estat.generaAssignacioInicial1());
            }
            double mitja = Utils.getMitja(dades);
            double var = Utils.getVariancia(dades);
            double minim = Collections.min(dades);
            double max = Collections.max(dades);
            System.out.println(i + " " + mitja + " " + var + " " + minim + " " + max);
        }

    }
}
