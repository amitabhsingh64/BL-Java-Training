package Streams;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Streams {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,-2};
        Arrays.stream(arr).filter(n-> n>3).sorted().forEach(System.out::println);

        String s= "Amitabh";
        s.chars().mapToObj(n->(char)n).forEach(System.out::println);

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
    }
}
