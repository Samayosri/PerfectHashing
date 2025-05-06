import org.example.LinearHashTable;
import org.example.SquareHash;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * A simplified utility to measure approximate memory usage by objects in Java
 * using Runtime API and garbage collection.
 */

public class MemoryUsage {

    /**
     * Measures approximate memory usage of an object by checking memory before and after GC
     * @param runnable A runnable that creates and holds the object to measure
     * @return Approximate memory usage in bytes
     */
    public static long measureMemoryUsage(Runnable runnable) {
        // Run GC to clean up before measurement
        System.gc();
        System.gc();

        // Wait for GC to complete
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Get memory before object creation
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // Create and hold the object
        runnable.run();

        // Force GC again to clean up temporary objects created during the creation
        System.gc();
        System.gc();

        // Wait for GC to complete
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Get memory after object creation
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // Return the difference
        return memoryAfter - memoryBefore;
    }

    /**
     * Format bytes into a readable string
     */
    public static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        }
    }

    /**
     * Helper method to measure hash table memory usage by size
     */
    public static void compareHashTables(int size) {
        System.out.println("\n=== Testing with size n = " + size + " ===");

        // Measure SquareHash
        final SquareHash[] squareHolder = new SquareHash[1];
        long squareMemory = measureMemoryUsage(() -> {
            SquareHash hash = new SquareHash(size);
            // Insert some data (half the capacity)
            for (int i = 0; i < size/2; i++) {
                hash.insert("key" + i);
            }
            squareHolder[0] = hash;
        });

        // Measure LinearHashTable
        final LinearHashTable[] linearHolder = new LinearHashTable[1];
        long linearMemory = measureMemoryUsage(() -> {
            LinearHashTable hash = new LinearHashTable(size);
            // Insert same data
            for (int i = 0; i < size/2; i++) {
                hash.insert("key" + i);
            }
            linearHolder[0] = hash;
        });

        // Print results
        System.out.println("SquareHash memory: " + formatBytes(squareMemory));
        System.out.println("LinearHashTable memory: " + formatBytes(linearMemory));
        System.out.println("Ratio (Square/Linear): " + String.format("%.2f", (double)squareMemory / linearMemory));

        // Calculate theoretical memory usage based on table size
        long squareTableSize = squareHolder[0].getTableSize();
        long linearTableSize = linearHolder[0].getTableSize();

        System.out.println("\nActual internal table sizes:");
        System.out.println("SquareHash table size: " + squareTableSize + " entries");
        System.out.println("LinearHashTable table size: " + linearTableSize + " entries");
        System.out.println("Table size ratio: " + String.format("%.2f", (double)squareTableSize / linearTableSize));
    }

    /**
     * Test and compare hash tables at various sizes
     */
    @Test
     void spaceCompare () {
        // Compare at different sizes to see growth pattern

        compareHashTables(50);
        compareHashTables(100);
        compareHashTables(200);
        compareHashTables(400);
        compareHashTables(800);
        compareHashTables(1600);



        // Summary table
        System.out.println("\n=== Memory Usage Growth Summary ===");
        System.out.println("Size  | SquareHash   | LinearHashTable | Ratio");
        System.out.println("------|--------------|----------------|------");

        int[] sizes = { 50, 100, 200, 400,800,1600};
        for (int size : sizes) {
            // Measure SquareHash
            final SquareHash[] squareHolder = new SquareHash[1];
            long squareMemory = measureMemoryUsage(() -> {
                SquareHash hash = new SquareHash(size);
                for (int i = 0; i < size/2; i++) {
                    hash.insert("key" + i);
                }
                squareHolder[0] = hash;
            });

            // Measure LinearHashTable
            final LinearHashTable[] linearHolder = new LinearHashTable[1];
            long linearMemory = measureMemoryUsage(() -> {
                LinearHashTable hash = new LinearHashTable(size);
                for (int i = 0; i < size/2; i++) {
                    hash.insert("key" + i);
                }
                linearHolder[0] = hash;
            });

            double ratio = (double)squareMemory / linearMemory;
            System.out.printf("%-5d | %-12s | %-14s | %.2f\n",
                    size,
                    formatBytes(squareMemory),
                    formatBytes(linearMemory),
                    ratio);
        }
    }
}