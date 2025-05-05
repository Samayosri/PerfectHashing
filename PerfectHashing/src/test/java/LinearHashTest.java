import org.example.LinearHashTable;
import org.example.SquareHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class LinearHashTest {

    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
    }

    @Test
    void testWithDifferentSizes() {
        System.out.println("==========================================test with different sizes===========================================");

        int[] sizes = {10, 50, 100, 200, 500};

        for (int size : sizes) {
            ArrayList<String> list = generateRandomStrings(size);
            LinearHashTable hashTable = new LinearHashTable(100);
            long startTime = System.nanoTime();
            for (String str : list) {
                hashTable.insert(str);
            }
            long endTime = System.nanoTime();
            long nanoTime = (endTime - startTime);
            double microTime = ((double) (endTime - startTime)) / 10E3;
            double milliTime = ((double) (endTime - startTime)) / 10E6;
            String  stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Size " + size + "  Normal Insert Time: " + stringTime);
            //batch
            hashTable = new LinearHashTable(100);
            startTime = System.nanoTime();
            hashTable.insert(list);
            endTime = System.nanoTime();
            nanoTime = (endTime - startTime);
            microTime = ((double) (endTime - startTime)) / 10E3;
            milliTime = ((double) (endTime - startTime)) / 10E6;
            stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Size " + size + "  Batch Insert Time: " + stringTime);
        }
    }

    @Test
    void testRehashing() {
        System.out.println("==========================================test rehashing===========================================");

        ArrayList<String> list = generateRandomStrings(150);
        LinearHashTable hashTable = new LinearHashTable(100);
        long startTime = System.nanoTime();
        for (String str : list) {
            hashTable.insert(str);
        }

        long endTime = System.nanoTime();
        double milliTime = ((double) (endTime - startTime)) / 10E6;

        System.out.println("Size is 100 and Insert time: " + milliTime + " ms");
        System.out.println(" Number of rehashings: " + hashTable.getNumberOfRehashing());
    }


    @Test
    void testSearch() {
        System.out.println("==========================================test search===========================================");
        int[] sizes = {10, 50, 100, 200, 500};

        for (int size : sizes) {
            ArrayList<String> list = generateRandomStrings(size);
            LinearHashTable hashTable = new LinearHashTable(100);
            hashTable.insert(list);
            long startTime = System.nanoTime();
            for(String str: list){
                hashTable.search(str);
            }
            long endTime = System.nanoTime();
            long nanoTime = (endTime - startTime);
            double microTime = ((double) (endTime - startTime)) / 10E3;
            double milliTime = ((double) (endTime - startTime)) / 10E6;
            String  stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Search time : " + stringTime);
        }}

    @Test
    void testDelete() {
        System.out.println("==========================================test delete===========================================");
        ArrayList<String> list = generateRandomStrings(200);
        LinearHashTable hashTable = new LinearHashTable(250);
        hashTable.insert(list);
        ArrayList<String> toDelete = new ArrayList<>(list.subList(0, 50));
        long startTime = System.nanoTime();
        for(String str: toDelete){
            hashTable.delete(str);
        }
        long endTime = System.nanoTime();
        long nanoTime = (endTime - startTime);
        double microTime = ((double) (endTime - startTime)) / 10E3;
        double milliTime = ((double) (endTime - startTime)) / 10E6;
        String  stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
        System.out.println("Normal delete time (50 elements): " + stringTime);

        //batch delete
        hashTable = new LinearHashTable(100);
        hashTable.insert(list);
        startTime = System.nanoTime();
        hashTable.delete(toDelete);
        endTime = System.nanoTime();
        nanoTime = (endTime - startTime);
        microTime = ((double) (endTime - startTime)) / 10E3;
        milliTime = ((double) (endTime - startTime)) / 10E6;
        stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
        System.out.println("Batch delete time (50 elements): " + stringTime);
    }

    // Utility method to generate random strings
    private ArrayList<String> generateRandomStrings(int count) {
        return generateRandomStrings(count, "");
    }

    private ArrayList<String> generateRandomStrings(int count, String prefix) {
        ArrayList<String> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder(prefix);
            int length = random.nextInt(10) + 5; // String length between 5 and 14

            for (int j = 0; j < length; j++) {
                char c = (char) (random.nextInt(26) + 'a');
                sb.append(c);
            }

            result.add(sb.toString());
        }

        return result;
    }
}