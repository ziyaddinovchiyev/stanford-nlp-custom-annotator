# Custom annotators for Stanford CoreNLP - Java

Emotion Annotator - based on DepecheMood++ lexicon (https://ieeexplore.ieee.org/document/8798675)

Sentiment Annotator - generating document level sentiment out of existing sentence level sentiment

Download the lexicon from here:
https://github.com/marcoguerini/DepecheMood/releases/tag/v2.0

Add these lines to your main method:

    Annotation document = PipelineNLP.getInstance().annotate("your string");
    document.get(CustomSentimentAnnotation.class).getLevel();
    document.get(EmotionAnnotation.class).getNormalizedEmotions();
