import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;


public class PeriodeUtil {

    /**
     * 1. Sort the intervals based on increasing order of starting time.
     * 2. Push the first interval on to a stack.
     * 3. For each interval do the following
     * a. If the current interval does not overlap with the stack top, push it.
     * b. If the current interval overlaps with stack top and ending time of current interval is more than that of stack top, update stack top with the ending time of current interval.
     * 4. At the end stack contains the merged intervals.
     * <p>
     * source: https://www.geeksforgeeks.org/merging-intervals/
     *
     * @param periodes te mergen periodes, begindatum verplicht, lege einddatum = oneindig
     * @return de gemergde periodes
     */
    public static List<Periode> mergeIntervals(List<Periode> periodes) {

        if (periodes.stream().anyMatch(p -> p.getBeginDatum() == null)) {
            throw new IllegalArgumentException("Alle periodes moeten een begindatum hebben.");
        }

        if (periodes.isEmpty()) {
            return Collections.emptyList();
        }

        Stack<Periode> stack = new Stack<>();
        periodes.sort(Comparator.comparing(Periode::getBeginDatum));
        stack.push(periodes.get(0));

        for (int i = 1; i < periodes.size(); i++) {
            Periode top = stack.peek();
            Periode current = periodes.get(i);
            if (!overlappend(top, current)) {
                stack.push(current);
            } else if (topEinddatumBeforeCurrentEinddatum(top.getEindDatum(), current.getEindDatum())) {
                top.setEindDatum(current.getEindDatum());
                stack.pop();
                stack.push(top);
            }
        }

        return stack;
    }

    private static boolean topEinddatumBeforeCurrentEinddatum(LocalDate topEinddatum, LocalDate currentEinddatum) {
        if (topEinddatum == null) {
            return false;
        }
        if (currentEinddatum == null) {
            return true;
        }
        return topEinddatum.isBefore(currentEinddatum);
    }

    private static boolean overlappend(Periode p1, Periode p2) {
        if (p1.getEindDatum() == null || p2.getBeginDatum().isBefore(p1.getEindDatum())) {
            return true;
        }
        if (p1.getEindDatum().isEqual(p2.getBeginDatum())) {
            return true;
        }
        return ChronoUnit.DAYS.between(p1.getEindDatum(), p2.getBeginDatum()) == 1;
    }
}
