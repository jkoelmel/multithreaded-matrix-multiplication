package Matrix_Multi_Koelmel_Threaded;

import java.util.*;

public class MatrixMultiply {

    static boolean debug = false; // for quick checking
    static int n= 16;       // size of matrices
    static int baseSize= 1; // adjust base case size for recursive methods
    static int repeat= 1;   // number of repetitions for same set of matrices

    // Non-recursive conventional matrix multiplication
    static int[][] multiply1(int A[][], int B[][])
    {
        int n = A.length;
        int C[][] = new int[n][n];

        // implement the conventional matrix multiplication
        if(n < 512){
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        } else {
            ThreadCreation.multiply(A, B, C);
        }
        return C;

    }


    // Simple divide and conquer matrix multiplication
    public static int[][] Multiply2(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int [n][n];

        // if the input matrix size is <= base case size,
        // call multiply1() directly
        if (n <= baseSize) {
            C = multiply1(A, B);
        } else {

            // Split A to 4 n/2 X n/2 matrices: A11, A12, A21, A22
			int[][] A11 = new int[n/2][n/2];
			int[][] A12 = new int[n/2][n/2];
			int[][] A21 = new int[n/2][n/2];
			int[][] A22 = new int[n/2][n/2];
			// Split B to 4 n/2 X n/2 matrices: B11, B12, B21, B22
			int[][] B11 = new int[n/2][n/2];
			int[][] B12 = new int[n/2][n/2];
			int[][] B21 = new int[n/2][n/2];
			int[][] B22 = new int[n/2][n/2];

        //fill new matrices
        for(int i = 0; i < n; i++) {
            if(i < n/2) {
                System.arraycopy(A[i], 0, A11[i], 0, n/2);
                System.arraycopy(A[i], n/2, A12[i], 0, n/2);
                System.arraycopy(B[i], 0, B11[i], 0, n/2);
                System.arraycopy(B[i], n/2, B12[i], 0, n/2);
            } else {
                System.arraycopy(A[i], 0, A21[i - n/2], 0, n/2);
                System.arraycopy(A[i], n/2, A22[i - n/2], 0, n/2);
                System.arraycopy(B[i], 0, B21[i - n/2], 0, n/2);
                System.arraycopy(B[i], n/2, B22[i - n/2], 0, n/2);
            }
        }

            //Recursive calls for sub-matrices
            // A11 * B11
            int[][] M1 = Multiply2(A11, B11);
            // A12 * B21
            int[][] M2 = Multiply2(A12, B21);
            // A11 * B12
            int[][] M3 = Multiply2(A11, B12);
            // A12 * B22
            int[][] M4 = Multiply2(A12, B22);
            // A21 * B11
            int[][] M5 = Multiply2(A21, B11);
            // A22 * B21
            int[][] M6 = Multiply2(A22, B21);
            // A21 * B12
            int[][] M7 = Multiply2(A21, B12);
            // A22 * B22
            int[][] M8 = Multiply2(A22, B22); 
                
            // Compute results to get C11, C12, C21, C22
            // C11 = A11 * B11 + A12 * B21
            int[][] C11 = addMatrices(M1, M2);
            // C12 = A11 * B12 + A12 * B22
            int[][] C12 = addMatrices(M3, M4);
            // C21 = A21 * B11 + A22 * B21
            int[][] C21 = addMatrices(M5, M6);
            // C22 = A21 * B12 + A22 * B22
            int[][] C22 = addMatrices(M7, M8);
            
            // Grouping the results obtained in a result matrix C
            for(int i = 0; i < n; i++) {
                if(i < n/2) {
                    System.arraycopy(C11[i], 0, C[i], 0, n/2);
                    System.arraycopy(C12[i], 0, C[i], n/2, n/2);
                } else {
                    System.arraycopy(C21[i - n/2], 0, C[i], 0, n/2);
                    System.arraycopy(C22[i - n/2], 0, C[i], n/2, n/2);
                }
            }
        }

        return C;
    }


    // Strassen Matrix Multiplication
    public static int[][] Multiply3(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        // if the input matrix size is <= base case size,
        // call multiply1() directly
        if (n <= baseSize) {
            C = multiply1(A, B);
        } else {
            // Split A to 4 n/2 X n/2 matrices: a, b, c, d
            int[][] a = new int[n/2][n/2];
            int[][] b = new int[n/2][n/2];
            int[][] c = new int[n/2][n/2];
            int[][] d = new int[n/2][n/2];
            // Split B to 4 n/2 X n/2 matrices: e, f, g, h
            int[][] e = new int[n/2][n/2];
            int[][] f = new int[n/2][n/2];
            int[][] g = new int[n/2][n/2];
            int[][] h = new int[n/2][n/2];
            //fill new matrices

            for(int i = 0; i < n; i++) {
                if(i < n/2) {
                    System.arraycopy(A[i], 0, a[i], 0, n/2);
                    System.arraycopy(A[i], n/2, b[i], 0, n/2);
                    System.arraycopy(B[i], 0, e[i], 0, n/2);
                    System.arraycopy(B[i], n/2, f[i], 0, n/2);
                } else {
                    System.arraycopy(A[i], 0, c[i - n/2], 0, n/2);
                    System.arraycopy(A[i], n/2, d[i - n/2], 0, n/2);
                    System.arraycopy(B[i], 0, g[i - n/2], 0, n/2);
                    System.arraycopy(B[i], n/2, h[i - n/2], 0, n/2);
                }
            }

        // Compute 7 n/2 X n/2 matrix multiplication recursively
            //	 p1 = (a + d)(e + h)
            int[][] p1 = Multiply3(addMatrices(a, d), addMatrices(e, h));
            //	 p2 = (c + d)e
            int[][] p2 = Multiply3(addMatrices(c, d), e);
            //	 p3 = a(f - h)
            int[][] p3 = Multiply3(a, subMatrices(f, h));
            //	 p4 = d(g - e)
            int[][] p4 = Multiply3(d, subMatrices(g, e));
            //	 p5 = (a + b)h
            int[][] p5 = Multiply3(addMatrices(a, b), h);
            //	 p6 = (c - a) (e + f)
            int[][] p6 = Multiply3(subMatrices(c, a), addMatrices(e, f));
            //	 p7 = (b - d) (g + h)
            int[][] p7 = Multiply3(subMatrices(b, d), addMatrices(g, h));
    
        // Combine results to get C11, C12, C21, C22
            //	 C11 = p1 + p4 - p5 + p7
            int[][] C11 = addMatrices(subMatrices(addMatrices(p1, p4), p5), p7);  
            //	 C12 = p3 + p5
            int[][] C12 = addMatrices(p3, p5);
            //	 C21 = p2 + p4
            int[][] C21 = addMatrices(p2, p4);
            //	 C22 = p1 - p2 + p3 + p6
            int[][] C22 = addMatrices(addMatrices(subMatrices(p1, p2), p3), p6);
             // Grouping the results obtained in a result matrix C

            for(int i = 0; i < n; i++) {
                if(i < n/2) {
                    System.arraycopy(C11[i], 0, C[i], 0, n/2);
                    System.arraycopy(C12[i], 0, C[i], n/2, n/2);
                } else {
                    System.arraycopy(C21[i - n/2], 0, C[i], 0, n/2);
                    System.arraycopy(C22[i - n/2], 0, C[i], n/2, n/2);
                }
            }
        }

        return C;
    }

    //=======================================
    //     Do not change anything below
    //=======================================

    // Helper methods

    // Function to print a matrix M
    static void printMatrix(int M[][])
    {
        int n = M.length;
        for (int i = 0; i < n; i++)  {
            for (int j = 0; j < n; j++)
                System.out.print(M[i][j] + " ");
            System.out.println();
        }
    }

    // Adding 2 matrices C=A+B
    static int[][] addMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    // Subtracting 2 matrices C=A-B
    static int[][] subMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C= new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    // Generate a matrix with random data of size n x n
    static int[][] generateRandomMatrix(int n) {
        int[][] A = new int[n][n];
        int max = 100;
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = rand.nextInt(max);
            }
        }
        return A;
    }


    //======================================================================
    // Run Test cases : main()  ->  runTest()
    // These tests are driven by class data values:
    //    boolean debug = true; // for quick checking
    //    int n=16;       	    // size of matrices
    //    int baseSize=1; 	    // adjust base case for recursive method
    //    int repeat=1;   	    // number of repetitions for same matrices
    //======================================================================


    private static void runTest() {
        int[][] A = null;
        int[][] B = null;
        int[][] C = null;
        long startTime=0;
        long endTime=0;
        long avg1 = 0;
        long avg2 = 0;
        long avg3 = 0;

        System.out.println("Matrix size: " + n);
        System.out.println("Recursive base case size: " + baseSize);

        A = generateRandomMatrix(n);
        B = generateRandomMatrix(n);
        C = null;
        for (int j=1; j <= repeat; j++) {

            if (debug) {
                System.out.println("\nMatrix A:");
                printMatrix(A);
                System.out.println("\nMatrix B:");
                printMatrix(B);
                System.out.println("\nResults: ");
            }

            startTime= System.currentTimeMillis();
            C = multiply1(A, B);
            endTime= System.currentTimeMillis();
            avg1 += endTime-startTime;
            if (debug && (C !=null))
            { printMatrix(C); System.out.println();
            }

            startTime= System.currentTimeMillis();
            C = Multiply2(A, B);
            endTime= System.currentTimeMillis();
            avg2 += endTime-startTime;
            if (debug && (C !=null))
            { printMatrix(C); System.out.println();
            }

            startTime= System.currentTimeMillis();
            C = Multiply3(A, B);
            endTime= System.currentTimeMillis();
            avg3 += endTime-startTime;
            if (debug && (C !=null))
            { printMatrix(C); System.out.println();
            }

        }
        System.out.println("Number of loops: " + repeat);
        System.out.println("\nNon-recursive method average time = \t "+ (avg1/repeat));
        System.out.println("Divide and conquer method average time = "+ (avg2/repeat));
        System.out.println("Strassen method average time = \t\t "+(avg3/repeat));
        System.out.println("\n=======================================================\n");
    }

    // Main Test Driver - call runTest() methods
    public static void main(String[] args)
    {

        if (debug) {  	// debug true
            runTest(); 	// quick test using default 16 X 16 matrices
        } else {		// debug = false
            int number=11; 	// number of runs, see n values below
            repeat=5;	// run 5 times each method for same set of data
            for (int i=1; i<=number; i++) {
                n = (int) Math.pow(2,i); // n = 2, 4, 8, ..., 1024, 2048

                if (n >= 256) {
                    // for n >= 256, change base case size: 4, 8, 16
                    baseSize=4;
                    runTest();
                    baseSize=8;
                    runTest();
                    baseSize=16;
                    runTest();
                } else {
                    // for n < 256, base case is 1
                    runTest();
                }
            }
        }
    }
}
