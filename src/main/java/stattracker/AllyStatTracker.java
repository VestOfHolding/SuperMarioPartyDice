package stattracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.OnlineStatistics;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AllyStatTracker {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    private int allyCount;

    private OnlineStatistics turnsAdded;

    private OnlineStatistics distances;

    private OnlineStatistics coinCounts;

    Map<Integer, OnlineStatistics> landedSpacesStats;

    public AllyStatTracker(int allyCount) {
        this.allyCount = allyCount;
        turnsAdded = new OnlineStatistics();
        distances = new OnlineStatistics();
        coinCounts = new OnlineStatistics();
        landedSpacesStats = new HashMap<>();
    }

    public void addTurnAdded(int turnAdded) {
        turnsAdded.addValue(turnAdded);
    }

    public void addDistance(int distance) {
        distances.addValue(distance);
    }

    public void addCoinCount(int coinCount) {
        coinCounts.addValue(coinCount);
    }

    public void addLandedSpaceCount(Map<Integer, Integer> landedSpaceCount) {
        for (Integer spaceID : landedSpaceCount.keySet()) {
            OnlineStatistics spaceStats = landedSpacesStats.get(spaceID);

            if (spaceStats == null) {
                spaceStats = new OnlineStatistics();
            }
            spaceStats.addValue(landedSpaceCount.get(spaceID));
            landedSpacesStats.put(spaceID, spaceStats);
        }
    }

    public double getAverageTurnAdded() {
        return turnsAdded.getMean();
    }

    public double getAverageDistance() {
        return distances.getMean();
    }

    public double getAverageCoinCount() {
        return coinCounts.getMean();
    }

    public int getAmountOccured() {
        return distances.getCount();
    }

    public String toStatString(int gameCount, int possibleSpaces) {
        String formatResult = DECIMAL_FORMAT.format((((double)getAmountOccured()) / ((double)gameCount)) * 100.0);

        StringBuilder result = new StringBuilder().append(allyCount).append("\t")
                .append(formatResult).append("%\t")
                .append(DECIMAL_FORMAT.format(getAverageTurnAdded())).append("\t")
                .append(DECIMAL_FORMAT.format(turnsAdded.getStandardDeviation())).append("\t")
                .append(DECIMAL_FORMAT.format(getAverageDistance())).append("\t")
                .append(DECIMAL_FORMAT.format(distances.getStandardDeviation())).append("\t")
                .append(DECIMAL_FORMAT.format(getAverageCoinCount())).append("\t")
                .append(DECIMAL_FORMAT.format(coinCounts.getStandardDeviation())).append("\t");

        double spaceCountSum = landedSpacesStats.values().stream()
                .mapToDouble(OnlineStatistics::getCount)
                .sum();

        for (int i = 0; i < possibleSpaces; ++i) {
            OnlineStatistics spaceStats = landedSpacesStats.get(i);

            if (spaceStats == null) {
                result.append("0%\t");
            }
            else {
                result.append(DECIMAL_FORMAT.format((spaceStats.getCount() / spaceCountSum) * 100.0))
                        .append("%\t");
            }
        }

        return result.toString();
    }
}
