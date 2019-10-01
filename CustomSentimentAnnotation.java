package uni.kn.master.nlp.sentiment;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.ErasureUtils;

public class CustomSentimentAnnotation implements CoreAnnotation<Sentiment> {
    public Class<Sentiment> getType() {
        return ErasureUtils.uncheckedCast(Sentiment.class);
    }
}