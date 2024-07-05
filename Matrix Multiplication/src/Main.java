import java.util.Random;
import java.util.Scanner;

public class Main {
    static int dimension;
    static int[][] firstMatrix ;
    static int[][] secondMatrix ;
    static int[][] resultMatrix;

    public static class multiply extends Thread{
        int row ;
        multiply(int row){
            this.row = row;
        }

        @Override
        public void run()
        {
            for (int j = 0; j < dimension; j++){
                for (int i = 0 ; i < dimension ; i++){
                    int result;
                    result = firstMatrix[row][i] * secondMatrix[i][j];
                    resultMatrix[row][j] += result;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner out = new Scanner(System.in);
        System.out.print("How many size (n x n) do you want to create two arrays? : ");
        dimension = Integer.parseInt(out.nextLine());
        long startTime = System.currentTimeMillis();
        firstMatrix = new int[dimension][dimension];
        secondMatrix = new int[dimension][dimension];
        resultMatrix = new int[dimension][dimension];
        createMatrix(dimension);
        printMatrixs(firstMatrix, secondMatrix);
        multiplicationMatrix();
        printMatrixs(resultMatrix);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Süre ===> " + duration / 1000.0);
    }
    private static void multiplicationMatrix() {
        Thread[] threads = new Thread[dimension];
        for (int i = 0; i < dimension; i++){
            threads[i] = new multiply(i);
            threads[i].start();
        }

        //bütün threadleri bekleyelim
        for (int j = 0 ; j < dimension ; j++){
            try {
                threads[j].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void printMatrixs(int[][]matrix) {
        System.out.println("\nThe Result Matris Bellow;");
        for (int i = 0; i < dimension ; i++){
            for (int j = 0; j < dimension; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

    }
    private static void printMatrixs(int[][]first, int[][] second) {
        for (int i = 0; i < dimension ; i++){
            for (int j = 0; j < dimension; j++) {
                System.out.print(first[i][j] + "\t");
            }
            System.out.print("            ");
            for (int j = 0; j < dimension; j++) {
                System.out.print(second[i][j] + "\t");
            }
            System.out.println();
        }

    }
    private static void createMatrix(int dimension) {
        for (int i = 0; i < dimension ; i++){
            for (int j = 0; j < dimension; j++) {
                Random num = new Random();
                firstMatrix[i][j] = num.nextInt(100);
                secondMatrix[i][j] = num.nextInt(100);
            }
        }
    }
}