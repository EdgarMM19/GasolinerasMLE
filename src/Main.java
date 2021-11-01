import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int exp;
        int continuar = 1;

        try {
            while (continuar == 1) {

                String s;
                System.out.println("Indica el nombre de l'experiment que vols fer: 1 - 8");
                s = reader.readLine();
                exp = Integer.parseInt(s);

                if (exp == 1) {
                    System.out.println("Fent experiment 1...");
                    MainExperiment1.main();
                } else if (exp == 2) {
                    System.out.println("Fent experiment 2...");
                    MainExperiment2.main();
                } else if (exp == 3) {
                    System.out.println("Aquest experiment no es pot executar amb aquest programa.");
                } else if (exp == 4) {
                    System.out.println("Fent experiment 4...");
                    MainExperiment4.main();
                } else if (exp == 5) {
                    System.out.println("Fent experiment 5...");
                    MainExperiment5.main();
                } else if (exp == 6) {
                    System.out.println("Fent experiment 6...");
                    MainExperiment6.main();
                } else if (exp == 7) {
                    System.out.println("Fent experiment 7...");
                    MainExperiment7.main();
                } else if (exp == 8) {
                    System.out.println("Fent experiment 8...");
                    MainExperiment8.main();
                }

                System.out.println("Vols tornar a fer algun experiment? (sí = 1, no = 0)");
                s = reader.readLine();
                continuar = Integer.parseInt(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
