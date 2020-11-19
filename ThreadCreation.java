package Matrix_Multi_Koelmel_Threaded;

import java.util.ArrayList;
import java.util.List;

public class ThreadCreation {

    public static void multiply(int[][] mat1, int[][] mat2, int[][] result) {
        List<Thread> threads = new ArrayList<>();
        int rows = mat1.length;

        for (int i = 0; i < rows; i++) {
            RowMultiply task = new RowMultiply(result, mat1, mat2, i);
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
            if (threads.size() % 50 == 0) {
                waitForThreads(threads);
            }
        }
    }

    private static void waitForThreads(List<Thread> threads) {
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        threads.clear();
    }

}
