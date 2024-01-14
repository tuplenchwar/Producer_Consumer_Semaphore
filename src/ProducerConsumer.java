/**
 * Author: Tanvi Uplenchwar
 * SCU ID :: 07700010781
 * Email :: tuplenchwar@scu.edu
 */

import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * Main class to invoke producer consumer code.
 **/

    public class ProducerConsumer {

    public static void main(String[] args) {

        int bufferSize = 0;
        int consumerCnt = 0;
        int maxInt = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the buffer size: ");
            bufferSize = scanner.nextInt();
            System.out.println("Enter the number of consumers: ");
            consumerCnt = scanner.nextInt();
            System.out.print("Enter the maximum integers to produce: ");
            maxInt = scanner.nextInt();
            scanner.close();
        }
        catch (Exception ex){
            System.out.println("Please enter valid Integer value");
            return;
        }
        // Circular buffer
        CircularQueue buffer = new CircularQueue(bufferSize);

        // Semaphores for synchronization
        Semaphore mutex = new Semaphore(1); // Controls access to the buffer
        Semaphore empty = new Semaphore(bufferSize); // Counts empty slots
        Semaphore full = new Semaphore(0); // Counts filled slots

        // Create and start producer thread
        Thread producerThread = new Thread(new Producer(buffer, maxInt, mutex, empty, full));
        producerThread.start();

        // Create and start consumer threads
        Thread[] consumerThreads = new Thread[consumerCnt];
        Consumer consumer = new Consumer(buffer, mutex, empty, full, maxInt);
        for (int i = 0; i < consumerCnt; i++) {
            consumerThreads[i] = new Thread(consumer, "Consumer" + i);
            consumerThreads[i].start();
        }

        // Wait for the producer to finish
        try {
            producerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for consumer threads to finish
        for (Thread consumerThread : consumerThreads) {
            try {
                consumerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("All threads have finished.");
    }
}