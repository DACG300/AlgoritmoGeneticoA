public class RegresionLineal {
    DataSet dataset = new DataSet();
    DiscreteMaths dsm = new DiscreteMaths();

    double n;
    double sumaY;
    double sumaX;
    double sumaXY;
    double sumaX2;
    double sumaY2;

    public RegresionLineal() {
        n = dataset.getDataSetX().length;

        sumaX = dsm.sum(dataset.getDataSetX());
        sumaY = dsm.sum(dataset.getDataSetY());
        sumaXY = dsm.weightedSum(dataset.getDataSetX(), dataset.getDataSetY());
        sumaX2 = dsm.sum(dsm.power(dataset.getDataSetX(), 2));
        sumaY2 = dsm.sum(dsm.power(dataset.getDataSetY(), 2));
    }

    public double beta0() {
        return (sumaY * sumaX2 - sumaX * sumaXY) / (n * sumaX2 - sumaX * sumaX);
    }

    public double beta1() {
        return (n * sumaXY - sumaX * sumaY) / (n * sumaX2 - sumaX * sumaX);
    }

    public double[] getBetas() {
        return new double[]{beta0(), beta1()};
    }

    public double[] getPredictions(double[] X) {
        double[] predictions = new double[X.length];
        double b0 = beta0();
        double b1 = beta1();

        for (int i = 0; i < X.length; i++) {
            predictions[i] = b0 + b1 * X[i];
        }
        return predictions;
    }


    public double calculateR2(double[] actual, double[] predicted) {
        if (actual.length != predicted.length) {
            throw new IllegalArgumentException("Los arreglos deben tener el mismo tamaño.");
        }

        double sumSquaresTotal = 0.0;
        double sumSquaresResidual = 0.0;
        double meanActual = 0.0;

        for (double value : actual) {
            meanActual += value;
        }
        meanActual /= actual.length;

        for (int i = 0; i < actual.length; i++) {
            sumSquaresTotal += Math.pow(actual[i] - meanActual, 2);
            sumSquaresResidual += Math.pow(actual[i] - predicted[i], 2);
        }

        return 1 - (sumSquaresResidual / sumSquaresTotal);
    }
}