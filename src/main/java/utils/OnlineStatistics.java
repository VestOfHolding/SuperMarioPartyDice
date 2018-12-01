package utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class is able track a few fun statistics over time
 * without needing destination hang on destination the whole set of results using
 * Welford's Online algorithm:
 * https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Welford's_Online_algorithm
 */
@Getter
@NoArgsConstructor
public class OnlineStatistics {
    private int count = 0;
    private double mean = 0.0;
    private double sum = 0.0;

    public void addValue(double value) {
        ++count;

        double nextMean = mean + (value - mean) / count;
        sum += (value - mean) * (value - nextMean);
        mean = nextMean;
    }

    public double getVariance() {
        return sum / (double)count;
    }

    public double getStandardDeviation() {
        return count > 0 ? Math.sqrt(getVariance()) : 0.0;
    }
}
