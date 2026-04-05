package Lr10.timus_tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution1044 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int m = n / 2;
        int maxSum = 9 * m;

        long[] dp = new long[maxSum + 1];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            long[] next = new long[maxSum + 1];
            for (int sum = 0; sum <= 9 * i; sum++) {
                for (int digit = 0; digit <= 9; digit++) {
                    next[sum + digit] += dp[sum];
                }
            }
            dp = next;
        }

        long answer = 0;
        for (int s = 0; s <= maxSum; s++) {
            answer += dp[s] * dp[s];
        }

        System.out.println(answer);
    }
}
