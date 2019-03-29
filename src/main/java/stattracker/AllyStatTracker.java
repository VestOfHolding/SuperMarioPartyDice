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

    private OnlineStatistics starCounts;

    Map<Integer, OnlineStatistics> landedSpacesStats;

    public AllyStatTracker(int allyCount) {
        this.allyCount = allyCount;
        turnsAdded = new OnlineStatistics();
        distances = new OnlineStatistics();
        coinCounts = new OnlineStatistics();
        starCounts = new OnlineStatistics();
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

    public void addStarCount(int starCount) {
        starCounts.addValue(starCount);
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

    public double getAverageStarCount() {
        return starCounts.getMean();
    }

    public int getAmountOccured() {
        return distances.getCount();
    }

    public String toStatString(int gameCount, int possibleSpaces) {
        String formatResult = DECIMAL_FORMAT.format((((double)getAmountOccured()) / ((double)gameCount)) * 100.0);

        StringBuilder result = new StringBuilder().append(allyCount).append("\t")
                .append(formatResult).append("%\t")
                .append(DECIMAL_FORMAT.format(turnsAdded.getMin())).append("\t")
                .append(DECIMAL_FORMAT.format(turnsAdded.getFirstQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(getAverageTurnAdded())).append("\t")
                .append(DECIMAL_FORMAT.format(turnsAdded.getThirdQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(turnsAdded.getMax())).append("\t")
                .append(DECIMAL_FORMAT.format(turnsAdded.getStandardDeviation())).append("\t")
                .append(DECIMAL_FORMAT.format(distances.getMin())).append("\t")
                .append(DECIMAL_FORMAT.format(distances.getFirstQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(getAverageDistance())).append("\t")
                .append(DECIMAL_FORMAT.format(distances.getThirdQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(distances.getMax())).append("\t")
                .append(DECIMAL_FORMAT.format(distances.getStandardDeviation())).append("\t")
                .append(DECIMAL_FORMAT.format(coinCounts.getMin())).append("\t")
                .append(DECIMAL_FORMAT.format(coinCounts.getFirstQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(getAverageCoinCount())).append("\t")
                .append(DECIMAL_FORMAT.format(coinCounts.getThirdQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(coinCounts.getMax())).append("\t")
                .append(DECIMAL_FORMAT.format(coinCounts.getStandardDeviation())).append("\t")
                .append(DECIMAL_FORMAT.format(starCounts.getMin())).append("\t")
                .append(DECIMAL_FORMAT.format(starCounts.getFirstQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(getAverageStarCount())).append("\t")
                .append(DECIMAL_FORMAT.format(starCounts.getThirdQuartile())).append("\t")
                .append(DECIMAL_FORMAT.format(starCounts.getMax())).append("\t")
                .append(DECIMAL_FORMAT.format(starCounts.getStandardDeviation())).append("\t");

//        double spaceCountSum = landedSpacesStats.values().stream()
//                .mapToDouble(OnlineStatistics::getCount)
//                .sum();

//        for (int i = 0; i < possibleSpaces; ++i) {
//            OnlineStatistics spaceStats = landedSpacesStats.get(i);
//
//            if (spaceStats == null) {
//                result.append("0%\t");
//            }
//            else {
//                result.append(DECIMAL_FORMAT.format((spaceStats.getCount() / spaceCountSum) * 100.0))
//                        .append("%\t");
//            }
//        }

        return result.toString();
    }
}
