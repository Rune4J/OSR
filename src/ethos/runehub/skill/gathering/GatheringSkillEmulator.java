package ethos.runehub.skill.gathering;

public class GatheringSkillEmulator {

    public static void main(String[] args) {
        for (int i = 1; i < 99; i++) {
            System.out.printf("Level %d: %.2f%%\n", i, getActionSuccessChance(16, 107, i) * 100);
        }

        double percentAtStartingLevel = 2.34;  // 5% chance at level 1
        double percentAtLevel99 = 2.73; // 20% chance at level 99
// iron is 48/150
        double[] results = calculateLowHigh(percentAtStartingLevel, percentAtLevel99);

        System.out.printf("Low: %.0f, High: %.0f\n", results[0], results[1]);

    }

    public static int calculateGrowth(int index, int stage, int diseased, int watered, int offset) {
        // Base value from index and stage
        int baseValue = (index << 3) + stage;

        // Adjust diseased bitshift using the offset to control the contribution
        int diseasedShift = diseased == 1 ? (diseased << (7 - offset)) : 0;

        // Watered contribution remains the same
        int wateredShift = watered == 1 ? (watered << 6) : 0;

        // Return the calculated result
        return baseValue + diseasedShift + wateredShift;
    }

    private static void getBits(int index, int stage, int diseased, int watered) {
        int result = (0 | (index << 3) + stage) + (diseased == 1 ? diseased << 7 : 0) + (watered == 1 ? watered << 6 : 0);  // Shifts 1 left by 3 positions, which is 8
        System.out.println(result);  // Outputs: 8
    }

   private static int getIndex(int startingIndex, int stage, int diseased, int watered, int offset) {
        int val = startingIndex;

        if (diseased == 1) {
            val += offset;
        }

        if (watered == 1) {
            val += offset;
        }

        return val;
   }


    private static double getActionSuccessChance(int min, int max, int level) {
        double brOne = Math.floor(min * (99 - level) / 98.0);
        double brTwo = Math.floor(max * (level - 1) / 98.0);
        double top = 1 + brOne + brTwo;
        return top / 256;
    }

    public static double[] calculateLowHigh(double percentAtLevel1, double percentAtLevel99) {
        // Convert percentages to fractions (e.g., 20% -> 0.2)
        double chance1 = percentAtLevel1 / 100.0;
        double chance99 = percentAtLevel99 / 100.0;

        // Calculate "low" (min) for level 1:
        double low = (chance1 * 256) - 1;

        // Calculate "high" (max) for level 99:
        double high = (chance99 * 256) - 1;

        // Ensure low and high are rounded appropriately
        low = Math.max(0, Math.round(low));
        high = Math.max(0, Math.round(high));

        // Return the results as an array: [low, high]
        return new double[]{low, high};
    }



}
