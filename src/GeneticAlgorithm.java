import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
    private double mutationRate;
    private int populationSize;
    private int numGenerations;
    private double crossoverRate;
    private double[] trueY;

    public GeneticAlgorithm(double mutationRate, int populationSize, int numGenerations, double crossoverRate, double[] trueY) {
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.numGenerations = numGenerations;
        this.crossoverRate = crossoverRate;
        this.trueY = trueY;
    }

    public double evaluateFitness(double[] coefficients, double[] X, double[] y) {
        double error = 0.0;
        for (int i = 0; i < X.length; i++) {
            double prediction = coefficients[0] + coefficients[1] * X[i];
            error += Math.pow(y[i] - prediction, 2);
        }
        return error / X.length;
    }

    public double[][] rouletteWheelSelection(double[][] population, double[] fitness) {
        int populationSize = population.length;
        double[][] selectedPopulation = new double[populationSize][population[0].length];

        // Invert fitness if smaller fitness is better
        double[] adjustedFitness = Arrays.stream(fitness).map(f -> 1 / (f + 1e-6)).toArray(); // Prevent division by zero
        double totalAdjustedFitness = Arrays.stream(adjustedFitness).sum();

        // Calculate probabilities
        double[] probabilities = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            probabilities[i] = adjustedFitness[i] / totalAdjustedFitness;
        }

        Random random = new Random();

        // Selection process
        for (int i = 0; i < populationSize; i++) {
            double randValue = random.nextDouble();
            double cumulativeProbability = 0.0;

            for (int j = 0; j < populationSize; j++) {
                cumulativeProbability += probabilities[j];
                if (randValue <= cumulativeProbability) {
                    selectedPopulation[i] = population[j].clone();
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    public double[][] crossover(double[][] population, double[] fitness) {
        // Placeholder for crossover logic
        return population;
    }

    public void mutate(double[][] population) {
        // Placeholder for mutation logic
    }

    public double[][] initializePopulation(int numIndividuals, int numGenes) {
        double[][] population = new double[numIndividuals][numGenes];
        Random rand = new Random();
        for (int i = 0; i < numIndividuals; i++) {
            population[i][0] = rand.nextDouble() * 201; // Random value between 0 and 200
            population[i][1] = rand.nextDouble() * 201; // Random value between 0 and 200
        }
        return population;
    }

    public double[][] run(double[] X, double[] y) {
        int numGenes = 2;
        double[][] population = initializePopulation(populationSize, numGenes);

        for (int generation = 0; generation < numGenerations; generation++) {
            // Evaluate fitness
            double[] fitness = new double[population.length];
            for (int i = 0; i < population.length; i++) {
                fitness[i] = evaluateFitness(population[i], X, y);
            }

            // Roulette Wheel Selection
            population = rouletteWheelSelection(population, fitness);

            // Crossover and Mutation
            population = crossover(population, fitness);
            mutate(population);

            // Track the best fitness for logging/debugging
            double bestFitness = Arrays.stream(fitness).min().getAsDouble();
        }

        return population;
    }

    public double[] getBestCoefficients(double[][] population, double[] X, double[] y) {
        double[] bestCoefficients = null;
        double bestFitness = Double.MAX_VALUE;

        for (double[] coefficients : population) {
            double fitness = evaluateFitness(coefficients, X, y);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestCoefficients = coefficients;
            }
        }
        return bestCoefficients;
    }
}
