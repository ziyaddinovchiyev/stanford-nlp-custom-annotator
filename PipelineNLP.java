package uni.kn.master.nlp;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class PipelineNLP {

    private static PipelineNLP instance;
    private StanfordCoreNLP pipeline;

    public static PipelineNLP getInstance() {
        if (instance == null) {
            synchronized (PipelineNLP.class) {
                if (instance == null)
                    instance = new PipelineNLP();
            }
        }
        return instance;
    }

    private PipelineNLP() {
        Properties props = new Properties();
        props.put("customAnnotatorClass.emotion", "uni.kn.master.nlp.emotions.EmotionAnnotator");
        props.put("customAnnotatorClass.customSentiment", "uni.kn.master.nlp.sentiment.CustomSentimentAnnotator");
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment, emotion, customSentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public Annotation annotate(String text) {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        return document;
    }
}
