package uni.kn.master.nlp.emotions;

import java.util.Arrays;

public class Emotion {

    private double[] emotions;
    private double[] normalized;

    public Emotion(double[] emotions) {
        this.emotions = emotions;
        this.normalized = Arrays.stream(this.emotions).map(this::normalize).toArray();
    }

    public double[] getEmotions() {
        return emotions;
    }

    public double[] getNormalizedEmotions() {
        return normalized;
    }

    private double normalize(double x) {
        int rangeMax = 1, rangeMin = 0;
        double min = Arrays.stream(this.emotions).min().getAsDouble();
        double max = Arrays.stream(this.emotions).max().getAsDouble();
        return ((x - min)
                / (max - min))
                * (rangeMax - rangeMin) + rangeMin;
    }

    @Override
    public String toString() {
        return "Emotion{" +
                "normalized emotions=" + Arrays.toString(this.normalized) +
                '}';
    }
}
