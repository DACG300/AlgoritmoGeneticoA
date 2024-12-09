import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
    protected double mutationRate;
    protected int populationSize;
    protected int numGenerations;
    protected double crossoverRate;
    protected double[] trueY;
    protected int bestGeneration = -1;

    public GeneticAlgorithm(double mutationRate, int populationSize, int numGenerations, double crossoverRate, double[] trueY) {
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.numGenerations = numGenerations;
        this.crossoverRate = crossoverRate;
        this.trueY = trueY;
    }

    public int getBestGeneration() {
        return bestGeneration;
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

        double[] adjustedFitness = Arrays.stream(fitness).map(f -> 1 / (f + 1e-6)).toArray();
        double totalAdjustedFitness = Arrays.stream(adjustedFitness).sum();

        double[] probabilities = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            probabilities[i] = adjustedFitness[i] / totalAdjustedFitness;
        }

        Random random = new Random();
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
        int populationSize = population.length;
        double[][] newPopulation = new double[populationSize][population[0].length];
        Random rand = new Random();

        double[][] selectedParents = rouletteWheelSelection(population, fitness);

        for (int i = 0; i < populationSize; i += 2) {
            double[] parent1 = selectedParents[i];
            double[] parent2 = selectedParents[i + 1];

            int crossoverPoint = rand.nextInt(parent1.length);

            for (int j = 0; j < parent1.length; j++) {
                if (j < crossoverPoint) {
                    newPopulation[i][j] = parent1[j];
                    newPopulation[i + 1][j] = parent2[j];
                } else {
                    newPopulation[i][j] = parent2[j];
                    newPopulation[i + 1][j] = parent1[j];
                }
            }
        }

        return newPopulation;
    }

    public void mutate(double[][] population) {
        Random rand = new Random();

        for (int i = 0; i < population.length; i++) {
            if (rand.nextDouble() < mutationRate) {
                int geneToMutate = rand.nextInt(population[i].length);
                double mutationAmount = rand.nextGaussian() * 0.1;

                population[i][geneToMutate] += mutationAmount;
            }
        }
    }

    public double[][] initializePopulation(int numIndividuals, int numGenes) {
        double[][] population = new double[numIndividuals][numGenes];
        Random rand = new Random();
        for (int i = 0; i < numIndividuals; i++) {
            population[i][0] = rand.nextDouble() * 201;
            population[i][1] = rand.nextDouble() * 201;
        }
        return population;
    }

    public double[][] run(double[] X, double[] y) {
        int numGenes = 2;
        double[][] population = initializePopulation(populationSize, numGenes);
        double bestFitness = Double.MAX_VALUE;

        for (int generation = 0; generation < numGenerations; generation++) {
            double[] fitness = new double[population.length];
            for (int i = 0; i < population.length; i++) {
                fitness[i] = evaluateFitness(population[i], X, y);
            }

            population = rouletteWheelSelection(population, fitness);
            population = crossover(population, fitness);
            mutate(population);

            double[] currentBestCoefficients = getBestCoefficients(population, X, y);
            double currentBestFitness = evaluateFitness(currentBestCoefficients, X, y);
            if (currentBestFitness < bestFitness) {
                bestFitness = currentBestFitness;
                bestGeneration = generation;
            }
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