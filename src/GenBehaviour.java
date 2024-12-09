import java.util.Arrays;
import jade.core.behaviours.OneShotBehaviour;

public class GenBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        DataSet dataset = new DataSet();
        double[] X = dataset.getDataSetX();
        double[] Y = dataset.getDataSetY();

        if (X.length != Y.length) {
            System.err.println("Los datos de entrada deben tener el mismo tamaño.");
            return;
        }

        RegresionLineal regLin = new RegresionLineal();

        double[] betasRegLin = regLin.getBetas();
        System.out.println("Betas de la regresión lineal: " + Arrays.toString(betasRegLin));

        double[] predictionsRegLin = regLin.getPredictions(X);
        System.out.println("Predicciones de la regresión lineal: " + Arrays.toString(predictionsRegLin));

        GeneticAlgorithm ga = new GeneticAlgorithm(0.01, 100, 100, 0.95, Y);

        double[][] population = ga.run(X, Y);
        double[] bestCoefficients = ga.getBestCoefficients(population, X, Y);
        int bestGeneration = ga.getBestGeneration();

        System.out.println("\nMejor Generación Encontrada: " + (bestGeneration + 1));


        System.out.println("\nMejores coeficientes encontrados por el GA: " + Arrays.toString(bestCoefficients));

        double[] predictionsGA = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            predictionsGA[i] = bestCoefficients[0] + bestCoefficients[1] * X[i];
        }

        System.out.println("Predicciones del algoritmo genético: " + Arrays.toString(predictionsGA));

        double r2RegLin = regLin.calculateR2(Y, predictionsRegLin);
        System.out.println("\nCorrelación cuadrada (R²) de la regresión lineal: " + r2RegLin);

        double r2GA = regLin.calculateR2(Y, predictionsGA);
        System.out.println("Correlación cuadrada (R²) del GA: " + r2GA);
    }


}
