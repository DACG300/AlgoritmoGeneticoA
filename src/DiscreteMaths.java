public class DiscreteMaths {

    public static double sum(double[] numbers) {
        double total = 0.0;
        for (double num : numbers) {
            total += num;
        }
        return total;
    }

    public static double weightedSum(double[] array1, double[] array2) {
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Los arreglos deben tener el mismo tamaño.");
        }
        double total = 0.0;
        for (int i = 0; i < array1.length; i++) {
            total += array1[i] * array2[i];
        }
        return total;
    }


    public static double[] power(double[] numbers, int exponent) {
        double[] powered = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            powered[i] = Math.pow(numbers[i], exponent);
        }
        return powered;
    }

}