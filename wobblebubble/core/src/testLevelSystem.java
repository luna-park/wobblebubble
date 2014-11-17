/**
 * Created by znak on 17.11.2014.
 */
public class testLevelSystem {
    public static void main(String[] args) {
        int lvl = 0;
        for (int i = 1; i < 10; i++) {
            long score = (long) Math.pow(1.5, i) * 5000 + 50000;
            System.out.println("level: " + i + " score: " + score);
        }
    }

    private long getScoreByLevel(int level) {
        return (long) Math.pow(1.5, level) * 5000 + 50000;
    }
}
