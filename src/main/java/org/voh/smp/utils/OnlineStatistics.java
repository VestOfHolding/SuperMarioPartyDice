package org.voh.smp.utils;

import com.tdunning.math.stats.TDigest;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class is able to track a few fun statistics over time
 * without needing the whole set of results using Welford's Online algorithm:
 * https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Welford's_Online_algorithm
 */
@Getter
@NoArgsConstructor
public class OnlineStatistics {
    private double mean;
    private double sum;
    private long count;

    private final TDigest tdigest = TDigest.createDigest(500);

    public void addValue(double value) {
        tdigest.add(value);

        count++;
        double nextMean = mean + (value - mean) / count;
        sum += (value - mean) * (value - nextMean);
        mean = nextMean;
    }

    public double getVariance() {
        return count > 0 ? sum / count : 0.0;
    }

    public double getStandardDeviation() {
        return 0 < count ? Math.sqrt(getVariance()) : 0.0;
    }

    public double getMin() {
        return Double.POSITIVE_INFINITY == tdigest.getMin() ? 0 : tdigest.getMin();
    }

    public double getFirstQuartile() {
        return tdigest.quantile(0.25);
    }

    public double getThirdQuartile() {
        return tdigest.quantile(0.75);
    }

    public double getMax() {
        return tdigest.getMax();
    }

    public int getCount() {
        return (int)count;
    }
}
