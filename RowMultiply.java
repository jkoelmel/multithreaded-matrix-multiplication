package Matrix_Multi_Koelmel_Threaded;


public class RowMultiply implements Runnable{
    private int[][] mat1;
    private int[][] mat2;
    private int[][] result;
    private int row;

    public RowMultiply(int[][] result, int [][] mat1, int[][] mat2, int row) {
        this.result = result;
        this.mat1 = mat1;
        this.mat2 = mat2;
        this.row = row;
    }

    @Override
    public void run() {
        for(int i = 0; i < mat2[0].length; i++) {
            result[row][i] = 0;
            for(int j = 0; j < mat1[row].length; j++) {
                result[row][i] += mat1[row][j] * mat2[j][i];
            }
        }
    }
}
