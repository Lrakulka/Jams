package qualification_round_2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SavingTheUniverseAgain {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 1; i <= t; ++i) {
            int maxDamageToHandle = in.nextInt();
            String robotProgram = in.next();
            System.out.println("Case #" + i + ": " + getValue(getHackNumber(maxDamageToHandle, robotProgram)));
        }
    }

    private static Integer getHackNumber(int maxDamageToHandle, String robotProgram) {
        List<Integer> damages = getDamages(robotProgram);
        int damageToReduce = damages.stream().mapToInt(value -> (int) Math.pow(2, value)).sum() - maxDamageToHandle;
        if (damageToReduce <= 0) {
            return 0;
        }

        int hacks = 0;
        for (int i = 0; i < damages.size() && damageToReduce > 0; ++i) {
            int shootDamage = (int) Math.pow(2, damages.get(i));
            int reduceDamage = Math.min(shootDamage - 1, damageToReduce);
            damageToReduce -= reduceDamage;

            int hack;
            if (reduceDamage == 1) {
                hack = 1;
            } else {
                hack = (int) Math.round(Math.log(reduceDamage) / Math.log(2));
            }
            hacks += hack;
            damages.set(i, damages.get(i) - hack); // not necessary, just for debugging
        }
        return damageToReduce <= 0 ? hacks : null;
    }

    private static List<Integer> getDamages(String robotProgram) {
        List<Integer> damages = new ArrayList<>();
        int power = 0;
        for (char c : robotProgram.toCharArray()) {
            if (c == 'C') {
                power++;
            }
            if (c == 'S') {
                damages.add(power);
            }
        }
        return damages;
    }

    private static String getValue(Integer value) {
        return Objects.isNull(value) ? "IMPOSSIBLE" : value.toString();
    }
}
