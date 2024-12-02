import java.util.*;

public class RouletteWheelSelection {
    public static int select(double[] fitness) {
        int n = fitness.length;
        double totalFitness = DiscreteMaths.sum(fitness);
        double[] cumulativeProbabilities = new double[n];
        Random rand = new Random();

        cumulativeProbabilities[0] = fitness[0] / totalFitness;
        for (int i = 1; i < n; i++) {
            cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + fitness[i] / totalFitness;
        }

        double r = rand.nextDouble();

        for (int i = 0; i < n; i++) {
            if (r <= cumulativeProbabilities[i]) {
                return i;
            }
        }
        return n - 1;
    }
}
