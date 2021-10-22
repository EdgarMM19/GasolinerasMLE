public class PercentatgePreu {
    static public int getGuanyat(int n) {
        if (n == 0) {
            return 102;
        }
        // Percentatge guanyat = 100 - 2^n
        return 100 - (1 << n);
    }

    static public int getPerdut(int n) {
        if (n == 0) {
            return 4;
        }
        // Percentatge perdut = (100 - 2^n) - (100 - 2^(n+1)) = 2^n
        return 1 << n;
    }
}
