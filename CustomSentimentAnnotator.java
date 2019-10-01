package uni.kn.master.nlp.sentiment;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.SentimentAnnotator;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;

import java.util.Properties;

public class CustomSentimentAnnotator extends SentimentAnnotator {

    public CustomSentimentAnnotator(String name, Properties props) {
        super(name, props);
    }

    @Override
    public void annotate(Annotation annotation) {
        super.annotate(annotation);
        double[] sentiments = new double[5];
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);

            for (int i = 0; i < 5; i++) {
                sentiments[i]+= (double)Math.round(sm.get(i) * 100d);
            }
        }
        annotation.set(CustomSentimentAnnotation.class, new Sentiment(sentiments));
    }
}
