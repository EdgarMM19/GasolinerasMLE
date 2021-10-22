public class PercentatgePreu {
    static public double getGuanyat(int n) {
        if (n == 0) {
            return 1.02;
        }
        // Percentatge guanyat = 100 - 2^n
        return (double)(100 - (1 << n))/100;
    }

    static public double getPerdut(int n) {
        if (n == 0) {
            return 0.04;
        }
        // Percentatge perdut = (100 - 2^n) - (100 - 2^(n+1)) = 2^n
        return (double)(1 << n)/100;
    }
}
