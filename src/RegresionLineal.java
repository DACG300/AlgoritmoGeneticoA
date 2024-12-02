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

    public double r() {
        double numerador = n * sumaXY - sumaX * sumaY;
        double denominador = Math.sqrt((n * sumaX2 - sumaX * sumaX) * (n * sumaY2 - sumaY * sumaY));
        return numerador / denominador;
    }

    public double r2() {
        return Math.pow(r(), 2);
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
}