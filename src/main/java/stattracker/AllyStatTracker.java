package stattracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AllyStatTracker {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    private int allyCount;

    private List<Integer> turnsAdded;

    private List<Integer> distances;

    private List<Integer> coinCounts;

    //Yeah? Yeah. At the very least this should go here.
    // I could just keep this as a map of totals, but I'm keeping track
    // of the individual counts for the other variables, so let's do that here too.
    // And this feels marginally better than a list of maps.
    Map<Integer, List<Integer>> landedSpacesCounts;

    public AllyStatTracker(int allyCount) {
        this.allyCount = allyCount;
        turnsAdded = new ArrayList<>();
        distances = new ArrayList<>();
        coinCounts = new ArrayList<>();
        landedSpacesCounts = new HashMap<>();
    }

    public void addTurnAdded(int turnAdded) {
        turnsAdded.add(turnAdded);
    }

    public void addDistance(int distance) {
        distances.add(distance);
    }

    public void addCoinCount(int coinCount) {
        coinCounts.add(coinCount);
    }

    public void addLandedSpaceCount(Map<Integer, Integer> landedSpaceCount) {
        for (Integer spaceID : landedSpaceCount.keySet()) {
            List<Integer> spaceCountList = landedSpacesCounts.get(spaceID);

            if (CollectionUtils.isEmpty(spaceCountList)) {
                spaceCountList = new ArrayList<>();
            }
            spaceCountList.add(landedSpaceCount.get(spaceID));
            landedSpacesCounts.put(spaceID, spaceCountList);
        }
    }

    public double getAverageTurnAdded() {
        return turnsAdded.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
    }

    public double getAverageDistance() {
        return distances.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
    }

    public double getAverageCoinCount() {
        return coinCounts.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
    }

    public int getAmountOccured() {
        return distances.size();
    }

    public String toStatString(int gameCount, int possibleSpaces) {
        double divisionResult = ((double)getAmountOccured()) / ((double)gameCount);
        String formatResult = DECIMAL_FORMAT.format(divisionResult * 100.0);

        StringBuilder result = new StringBuilder().append(allyCount).append("\t")
                .append(formatResult).append("%\t")
                .append(DECIMAL_FORMAT.format(getAverageTurnAdded())).append('\t')
                .append(DECIMAL_FORMAT.format(getAverageDistance())).append('\t')
                .append(DECIMAL_FORMAT.format(getAverageCoinCount())).append('\t');

        double spaceCountSum = landedSpacesCounts.values().stream()
                .mapToDouble(list -> list.stream().mapToDouble(Integer::doubleValue).sum())
                .sum();

        for (int i = 0; i < possibleSpaces; ++i) {
            List<Integer> spaceCounts = landedSpacesCounts.get(i);

            if (CollectionUtils.isEmpty(spaceCounts)) {
                result.append("0%\t");
            }
            else {
                double spaceSum = spaceCounts.stream().mapToDouble(Integer::doubleValue).sum();
                result.append(DECIMAL_FORMAT.format((spaceSum / spaceCountSum) * 100.0))
                        .append("%\t");
            }
        }

        return result.toString();
    }
}
