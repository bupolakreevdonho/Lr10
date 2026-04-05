package Lr10.timus_tasks;

import java.util.*;

public class Solution1673 {
    static long minN = Long.MAX_VALUE;
    static List<Long> primes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLong()) return;
        long K = sc.nextLong();

        if (K == 1) {
            System.out.println(1);
            return;
        }

        // Находим все возможные простые p, такие что (p-1) является делителем K
        for (long d = 1; d * d <= K; d++) {
            if (K % d == 0) {
                if (isPrime(d + 1)) primes.add(d + 1);
                if (d * d != K && isPrime(K / d + 1)) primes.add(K / d + 1);
            }
        }

        Collections.sort(primes, Collections.reverseOrder());

        solve(K, 1, 0);

        if (minN == Long.MAX_VALUE) {
            System.out.println(0);
        } else {
            System.out.println(minN);
        }
    }

    static boolean isPrime(long n) {
        if (n < 2) return false;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static void solve(long remainingK, long currentN, int primeIdx) {
        if (remainingK == 1) {
            minN = Math.min(minN, currentN);
            return;
        }
        if (primeIdx >= primes.size()) return;

        for (int i = primeIdx; i < primes.size(); i++) {
            long p = primes.get(i);

            // Если p-1 не делит остаток K, мы не можем использовать это простое число
            if (remainingK % (p - 1) == 0) {
                long nextK = remainingK / (p - 1);
                long nextN = currentN * p;

                // Пробуем включить p в разных степенях
                while (true) {
                    if (nextN >= minN) break;
                    solve(nextK, nextN, i + 1);

                    if (nextK % p == 0) {
                        nextK /= p;
                        nextN *= p;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}