package uni.kn.master.nlp.emotions;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.ErasureUtils;

public class EmotionAnnotation implements CoreAnnotation<Emotion> {
    public Class<Emotion> getType() {
        return ErasureUtils.uncheckedCast(Emotion.class);
    }
}