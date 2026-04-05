package Lr10.timus_tasks;

import java.util.Scanner;

public class Solution2001 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a1 = sc.nextInt();
        int b1 = sc.nextInt();
        int a2 = sc.nextInt();
        int b2 = sc.nextInt();
        int a3 = sc.nextInt();
        int b3 = sc.nextInt();

        int first = a1 - a3;
        int second = b1 - b2;

        System.out.println(first + " " + second);
    }
}
