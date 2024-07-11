import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snake {

    private static final Logger LOGGER = Logger.getLogger(Snake.class.getName());
    private static final String SNAKE_FILE_PATH = "data/snake.txt";
    private static final String ELONGATION_FILE_PATH = "data/elongation.txt";

    public static void main(String[] args) {
        int snakeRows = 213863;
        int snakeColumns = 4;
        int xyCoordinatesRows = 2000;
        int xyCoordinatesColumns = 2;
        int squaredDisplacementNumber = 1000;

        double[][] snakeData = new double[snakeRows][snakeColumns];
        double[][] xyCoordinates = new double[xyCoordinatesRows][xyCoordinatesColumns];
        double[] squaredDisplacements = new double[squaredDisplacementNumber];
        double[] elongations = new double[squaredDisplacementNumber];

        try {
            readMatrix(snakeData, snakeRows, snakeColumns);
            displayMatrix(snakeData, snakeRows, snakeColumns);

            extractCoordinates(snakeData, xyCoordinates, snakeRows, xyCoordinatesRows, xyCoordinatesColumns);
            displayMatrix(xyCoordinates, xyCoordinatesRows, xyCoordinatesColumns);

            calculateAllMSD(squaredDisplacements, xyCoordinates, xyCoordinatesRows);
            displayArray(squaredDisplacements);

            double meanSquaredDisplacement = calculateMeanSquaredDisplacement(squaredDisplacements);
            LOGGER.info("Mean Squared Displacement = " + round(meanSquaredDisplacement) + " micrometers");

            writeArrayToFile(squaredDisplacements);

            readArray(elongations, squaredDisplacementNumber);
            double contourLength = calculateMeanContourLength(elongations);
            System.out.println("Contour Length = " + round(contourLength) + " micrometers");

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An I/O error occurred", e);
        }
    }

    private static void readMatrix(double[][] matrix, int rows, int columns) throws IOException {
        try (Scanner scanner = new Scanner(new File(Snake.SNAKE_FILE_PATH))) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = scanner.nextDouble();
                }
            }
        }
    }

    private static void displayMatrix(double[][] matrix, int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static void extractCoordinates(double[][] inputMatrix, double[][] outputMatrix, int inputRows, int outputRows, int outputColumns) {
        int j = 0;
        for (int i = 1; i < inputRows && j < outputRows; i++) {
            if (inputMatrix[i][1] == 0) {
                outputMatrix[j][0] = inputMatrix[i - 1][2];
                outputMatrix[j][1] = inputMatrix[i - 1][3];
                j++;
                if (i != inputRows - 1 && j < outputRows) {
                    outputMatrix[j][0] = inputMatrix[i][2];
                    outputMatrix[j][1] = inputMatrix[i][3];
                    j++;
                }
            }
        }
    }

    private static void calculateAllMSD(double[] squaredDisplacements, double[][] coordinates, int rows) {
        int j = 0;
        for (int i = 1; i < rows; i += 2) {
            double deltaX = coordinates[i][0] - coordinates[i - 1][0];
            double deltaY = coordinates[i][1] - coordinates[i - 1][1];
            squaredDisplacements[j++] = (deltaX * deltaX + deltaY * deltaY) * 0.16 * 0.16;
        }
    }

    private static void displayArray(double[] array) {
        for (double v : array) {
            System.out.println(v);
        }
    }

    private static double calculateMeanSquaredDisplacement(double[] squaredDisplacements) {
        double sum = 0;
        for (double displacement : squaredDisplacements) {
            sum += displacement;
        }
        return sum / squaredDisplacements.length;
    }

    private static void writeArrayToFile(double[] array) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("squared_displacements.txt"))) {
            for (double value : array) {
                writer.write(Double.toString(value));
                writer.newLine();
            }
        }
    }

    private static void readArray(double[] array, int length) throws IOException {
        try (Scanner scanner = new Scanner(new File(Snake.ELONGATION_FILE_PATH))) {
            for (int i = 0; i < length; i++) {
                array[i] = scanner.nextDouble();
            }
        }
    }

    private static double calculateMeanContourLength(double[] elongations) {
        double sum = 0;
        for (double elongation : elongations) {
            sum += elongation;
        }
        return (sum * 0.16) / elongations.length;
    }

    private static double round(double value) {

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(5, RoundingMode.DOWN);
        return bd.doubleValue();
    }
}
