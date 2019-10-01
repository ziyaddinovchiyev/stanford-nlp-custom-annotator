package uni.kn.master.nlp.sentiment;

import java.util.Arrays;

public class Sentiment {

    private double[] sentiments;
    private int sentimentLevel;

    public Sentiment(double[] sentiments) {
        this.sentiments = sentiments;
        this.sentimentLevel = getLevel();
    }

    @Override
    public String toString() {
        return "Sentiment{" +
                "level=" + this.sentimentLevel +
                "sentiments=" + Arrays.toString(sentiments) +
                '}';
    }

    public int getLevel() {
        int level = 0;
        for (int i = 1; i < sentiments.length; i++) {
            if (sentiments[i] > sentiments[level])
                level = i;
        }
        return level;
    }
}
