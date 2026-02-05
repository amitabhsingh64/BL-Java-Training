package java_training.methods.level_3.problem_1;

public class problem_1 {
    public static void main(String[] args) {
        int[] heights = new int[11];
        
        for (int i = 0; i < heights.length; i++) {
            heights[i] = 150 + (int)(Math.random() * 101); // 150 to 250
        }
        
        System.out.println("Heights of all players (in cm):");
        for (int i = 0; i < heights.length; i++) {
            System.out.println("Player " + (i+1) + ": " + heights[i] + " cm");
        }
        
        int sum = findSum(heights);
        double mean = findMean(heights);
        int shortest = findShortest(heights);
        int tallest = findTallest(heights);
        
        System.out.println("\nTeam Statistics:");
        System.out.println("Total height of all players: " + sum + " cm");
        System.out.println("Mean height: " + mean + " cm");
        System.out.println("Shortest player: " + shortest + " cm");
        System.out.println("Tallest player: " + tallest + " cm");
    }
    
    public static int findSum(int[] heights) {
        int sum = 0;
        for (int height : heights) {
            sum += height;
        }
        return sum;
    }
    
    public static double findMean(int[] heights) {
        int sum = findSum(heights);
        return (double) sum / heights.length;
    }
    
    public static int findShortest(int[] heights) {
        int shortest = heights[0];
        for (int height : heights) {
            if (height < shortest) {
                shortest = height;
            }
        }
        return shortest;
    }
    
    public static int findTallest(int[] heights) {
        int tallest = heights[0];
        for (int height : heights) {
            if (height > tallest) {
                tallest = height;
            }
        }
        return tallest;
    }
}
