package code_jam_1b_2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Fail
public class Transmutation {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int m = in.nextInt();
            List<Formula> formulas = new ArrayList<>();
            for (int formula = 0; formula < m; ++formula) {
                formulas.add(new Formula(in.nextInt() - 1, in.nextInt() - 1, formula));
            }
            List<Long> metals = new ArrayList<>();
            for (int formula = 0; formula < m; ++formula) {
                metals.add(in.nextLong());
            }
            System.out.println("Case #" + i + ": " + getMaxLeadAmount(metals, formulas));
        }
    }

    private static long getMaxLeadAmount(List<Long> metals, List<Formula> formulas) {
        final Formula leadFormula = formulas.get(0);
        long leadAmount = Math.min(metals.get(leadFormula.x), metals.get(leadFormula.y));
        metals.set(leadFormula.x, metals.get(leadFormula.x) - leadAmount);
        metals.set(leadFormula.y, metals.get(leadFormula.y) - leadAmount);
        leadAmount += metals.get(0);
        metals.set(0, 0L);

        boolean isPossibleToProduce = true;
        while (isPossibleToProduce) {
            if (metals.get(leadFormula.x) == 0) {
                isPossibleToProduce = produceGetMetal(leadFormula.x, metals, new ArrayList<>(formulas),
                        new ArrayList<>(Collections.singletonList(leadFormula)));
            } else {
                metals.set(leadFormula.x, metals.get(leadFormula.x) - 1);
            }

            if (metals.get(leadFormula.y) == 0 && isPossibleToProduce) {
                isPossibleToProduce = produceGetMetal(leadFormula.y, metals, new ArrayList<>(formulas),
                        new ArrayList<>(Collections.singletonList(leadFormula)));
            } else {
                metals.set(leadFormula.y, metals.get(leadFormula.y) - 1);
            }
            if (isPossibleToProduce) {
                leadAmount++;
            }
        }

        return leadAmount;
    }

    private static boolean produceGetMetal(int metal, List<Long> metals, List<Formula> formulas, List<Formula> usedFormulas) {
        if (metals.get(metal) > 0) {
            metals.set(metal, metals.get(metal) - 1);
            return true;
        }
        Formula metalFormula = formulas.get(metal);
        if (!usedFormulas.contains(metalFormula)) {
            usedFormulas.add(metalFormula);
            return produceGetMetal(metalFormula.x, metals, formulas, usedFormulas)
                    && produceGetMetal(metalFormula.y, metals, formulas, usedFormulas);
        }
        return false;
    }

    private static class Formula {
        int x;
        int y;
        int met;

        public Formula(int x, int y, int met) {
            this.x = x;
            this.y = y;
            this.met = met;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Formula formula = (Formula) o;

            if (x != formula.x) return false;
            if (y != formula.y) return false;
            return met == formula.met;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + met;
            return result;
        }
    }
}
