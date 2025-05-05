import org.example.LinearHashTable;
import org.example.SquareHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class Comparison {

        private Random random;

        @BeforeEach
        void setUp() {
            random = new Random();
        }

        @Test
        void testWithDifferentSizes() {
            System.out.println("==========================================test with different sizes===========================================");

            int[] sizes = {10, 50, 100, 200, 500};
            double  milliTime=0,microTime=0;
            long nanoTime=0;
            System.out.println("=============For N^2============");
            for (int size : sizes) {
                ArrayList<String> list = generateRandomStrings(size);
                SquareHash hashTable = new SquareHash(100);
                long startTime = System.nanoTime();
                for (String str : list) {
                    hashTable.insert(str);
                }
                long endTime = System.nanoTime();
                nanoTime += (endTime - startTime);
                microTime += ((double) (endTime - startTime)) / 10E3;
                milliTime += ((double) (endTime - startTime)) / 10E6;
                String stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
                System.out.println("Size " + size + "  Normal Insert Time: " + stringTime);
            }

            nanoTime/=sizes.length;microTime/= sizes.length;milliTime/=sizes.length;
            String stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println(" mean Normal Insert Time:(Squared) " + stringTime);
            System.out.println("=============For N============");
            for (int size : sizes) {
                ArrayList<String> list = generateRandomStrings(size);
                LinearHashTable hashTable = new LinearHashTable(100);
                long startTime = System.nanoTime();
                for (String str : list) {
                    hashTable.insert(str);
                }
                long endTime = System.nanoTime();
                nanoTime += (endTime - startTime);
                microTime += ((double) (endTime - startTime)) / 10E3;
                milliTime += ((double) (endTime - startTime)) / 10E6;
                stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
                System.out.println("Size " + size + "  Normal Insert Time: " + stringTime);
            }

            nanoTime/=sizes.length;microTime/= sizes.length;milliTime/=sizes.length;
            stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println(" mean Normal Insert Time: (LINEAR)" + stringTime);

            //batch
            System.out.println("=============For N^2============");
            milliTime=0;microTime=0;milliTime=0;
            for (int size : sizes) {

                ArrayList<String> list = generateRandomStrings(size);
                SquareHash hashTable2 = new SquareHash(100);
                long startTime = System.nanoTime();
                hashTable2.insert(list);
                long endTime = System.nanoTime();
                nanoTime = (endTime - startTime);
                microTime = ((double) (endTime - startTime)) / 10E3;
                milliTime = ((double) (endTime - startTime)) / 10E6;
                String stringTime2 = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
                System.out.println("Size " + size + "  Normal Insert Time: " + stringTime2);
            }
            nanoTime/=sizes.length;microTime/= sizes.length;milliTime/=sizes.length;
            String stringTime2 = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println(" mean Batch Insert Time: " + stringTime2);
            System.out.println("=============For N============");
            for (int size : sizes) {

                ArrayList<String> list = generateRandomStrings(size);
                LinearHashTable hashTable2 = new LinearHashTable(100);
                long startTime = System.nanoTime();
                hashTable2.insert(list);
                long endTime = System.nanoTime();
                nanoTime = (endTime - startTime);
                microTime = ((double) (endTime - startTime)) / 10E3;
                milliTime = ((double) (endTime - startTime)) / 10E6;
                 stringTime2 = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
                System.out.println("Size " + size + "  Normal Insert Time: " + stringTime2);
            }
            nanoTime/=sizes.length;microTime/= sizes.length;milliTime/=sizes.length;
             stringTime2 = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println(" mean Batch Insert Time: " + stringTime2);
        }

        @Test
        void compareHashing() {
            System.out.println("==========================================compare rehashing===========================================");

            ArrayList<String> list = generateRandomStrings(1000);
            SquareHash hashTable = new SquareHash(100);
            long startTime = System.nanoTime();
            for (String str : list) {
                hashTable.insert(str);
            }

            long endTime = System.nanoTime();
            double milliTime = ((double) (endTime - startTime)) / 10E6;
            System.out.println("==========For N^2=========");
            System.out.println("Insert time: (size 1000) " + milliTime + " ms");
            System.out.println(" Number of rehashings: " + hashTable.getNumberOfRehashing());
            System.out.println("==========For N=========");
            LinearHashTable hashTable2 = new LinearHashTable(100);
            startTime = System.nanoTime();
            for (String str : list) {
                hashTable2.insert(str);
            }

             endTime = System.nanoTime();
             milliTime = ((double) (endTime - startTime)) / 10E6;
            System.out.println("Insert time: (size 1000) " + milliTime + " ms");
            System.out.println(" Number of rehashings: " + hashTable2.getNumberOfRehashing());

        }


//

    @Test
    void testSearch() {
        System.out.println("==========================================test search===========================================");
        int[] sizes = {10, 50, 100, 200, 500};
        double totalMilliTimeSquare = 0, totalMilliTimeLinear = 0;

        for (int size : sizes) {
            ArrayList<String> list = generateRandomStrings(size);

            System.out.println("===========For N^2=========");
            // Test with SquareHash (N²)
            SquareHash squareHashTable = new SquareHash(100);
            squareHashTable.insert(list);
            long startTime = System.nanoTime();
            for(String str: list){
                squareHashTable.search(str);
            }
            long endTime = System.nanoTime();
            long nanoTime = (endTime - startTime);
            double microTime = ((double) (endTime - startTime)) / 10E3;
            double milliTime = ((double) (endTime - startTime)) / 10E6;
            totalMilliTimeSquare += milliTime;
            String stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Size " + size + " - N² Search time: " + stringTime);

            // Test with LinearHashTable (N)
            System.out.println("===========For N=========");
            LinearHashTable linearHashTable = new LinearHashTable(100);
            linearHashTable.insert(list);
            startTime = System.nanoTime();
            for(String str: list){
                linearHashTable.search(str);
            }
            endTime = System.nanoTime();
            nanoTime = (endTime - startTime);
            microTime = ((double) (endTime - startTime)) / 10E3;
            milliTime = ((double) (endTime - startTime)) / 10E6;
            totalMilliTimeLinear += milliTime;
            stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Size " + size + " - Linear Search time: " + stringTime);

        }

        // Calculate and print mean times
        double meanMilliTimeSquare = totalMilliTimeSquare / sizes.length;
        double meanMilliTimeLinear = totalMilliTimeLinear / sizes.length;

        System.out.println("Mean N² Search time: " + meanMilliTimeSquare + " ms");
        System.out.println("Mean Linear Search time: " + meanMilliTimeLinear + " ms");
        System.out.println("Performance ratio (Linear/N²): " + (meanMilliTimeLinear / meanMilliTimeSquare));
    }

        @Test
        void testDelete() {
            System.out.println("==========================================test delete===========================================");
            System.out.println("=========for N^2===========");
            ArrayList<String> list = generateRandomStrings(200);
            SquareHash hashTable = new SquareHash(250);
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
            hashTable = new SquareHash(100);
            hashTable.insert(list);
            startTime = System.nanoTime();
            hashTable.delete(toDelete);
            endTime = System.nanoTime();
            nanoTime = (endTime - startTime);
            microTime = ((double) (endTime - startTime)) / 10E3;
            milliTime = ((double) (endTime - startTime)) / 10E6;
            stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Batch delete time (50 elements): " + stringTime);


            System.out.println("============for N============");
            LinearHashTable hashTable2 = new LinearHashTable(250);
            hashTable2.insert(list);
             startTime = System.nanoTime();
            for(String str: toDelete){
                hashTable2.delete(str);
            }
             endTime = System.nanoTime();
             nanoTime = (endTime - startTime);
             microTime = ((double) (endTime - startTime)) / 10E3;
             milliTime = ((double) (endTime - startTime)) / 10E6;
             stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";
            System.out.println("Normal delete time (50 elements): " + stringTime);
            hashTable2 = new LinearHashTable(100);
            hashTable2.insert(list);
            startTime = System.nanoTime();
            hashTable2.delete(toDelete);
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

