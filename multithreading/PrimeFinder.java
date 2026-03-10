package multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimeFinder implements Runnable {

    static final int TARGET = 1000;
    static int candidate = 2;
    static List<Integer> primes = new ArrayList<>();

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) return false;
        return true;
    }

    @Override
    public void run() {
        while (true) {
            int n;
            synchronized (PrimeFinder.class) {
                if (primes.size() >= TARGET) break;
                n = candidate++;
            }
            if (isPrime(n)) {
                synchronized (PrimeFinder.class) {
                    if (primes.size() < TARGET) primes.add(n);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new PrimeFinder());
        Thread t2 = new Thread(new PrimeFinder());
        Thread t3 = new Thread(new PrimeFinder());
        Thread t4 = new Thread(new PrimeFinder());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        Collections.sort(primes);
        for (int i = 0; i < primes.size(); i++)
            System.out.println((i + 1) + ". " + primes.get(i));

        System.out.println("1000th prime: " + primes.get(TARGET - 1));
    }
}
