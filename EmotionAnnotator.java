package uni.kn.master.nlp.emotions;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.ArraySet;
import uni.kn.master.utils.Constants;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EmotionAnnotator implements Annotator {

    public static final String LEXICON_DEPECHEMOOD = Constants.emotionLexicon;
    private Map<String, double[]> lexiconMap;

    public EmotionAnnotator(String name, Properties properties) {
        this.lexiconMap = loadEmotionLexicon();
    }

    @Override
    public void annotate(Annotation annotation) {
        List<double[]> documentEmotions = new ArrayList<>();
        for (CoreLabel token : annotation.get(CoreAnnotations.TokensAnnotation.class)) {
            double[] emotions = lexiconMap.getOrDefault(token.word(), null);
            if (emotions != null) {
                documentEmotions.add(emotions);
            }
        }
        annotation.set(EmotionAnnotation.class, calculateEmotionAvg(documentEmotions));
    }

    private Emotion calculateEmotionAvg(List<double[]> documentEmotions) {
        double[] a = new double[8];
        for (double[] documentEmotion : documentEmotions) {
            for (int j = 0; j < 8; j++) {
                a[j] += documentEmotion[j];
            }
        }
        return new Emotion(a);
    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
        return Collections.singleton(EmotionAnnotation.class);
    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requires() {
        return Collections.unmodifiableSet(new ArraySet<>(Arrays.asList(
                CoreAnnotations.TextAnnotation.class,
                CoreAnnotations.TokensAnnotation.class
        )));
    }

    private Map<String, double[]> loadEmotionLexicon() {
        Map<String, double[]> result = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(LEXICON_DEPECHEMOOD))) {

            CSVParser parser = new CSVParserBuilder().withSeparator('\t').withIgnoreQuotations(true).build();
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();

            for (String[] record : csvReader) {
                result.put(record[0], Arrays.stream(Arrays.copyOfRange(record, 1, record.length-1)).mapToDouble(Double::parseDouble).toArray());
            }
        } catch (IOException ignored) {}
        return result;
    }
}
