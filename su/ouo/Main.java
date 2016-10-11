package su.ouo;



public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Main launch = new Main();

    }

    public Main() {

        String[] spamWords = {"fuck", "cunt", "bitch"};
        //private String[] keywords = new String[]{":(", "=(", ":|"};

        TextAnalyzer[] woof = {
                new TooLongTextAnalyzer(20),
                new SpamAnalyzer(spamWords),
                new NegativeTextAnalyzer(),
        };

        String comment = "woof, sinyor, fuck";

        System.out.println(checkLabels(woof, comment));
    }
//        String a = "as";
//        double b = 16;
//        System.out.println(b=sqrt(b*-1));


//    public static double sqrt(double x){
//        String gripe = "Expected non-negative number, got " + x;
//        if (x<0) {throw new IllegalArgumentException(gripe);}
//        return Math.sqrt(x);
//    }

    interface TextAnalyzer {
        Label processText(String text);
    }

    abstract class KeywordAnalyzer implements TextAnalyzer {
        protected abstract String[] getKeywords();
        protected abstract Label getLabel();


        public Label processText(String text) {
            String[] keywords = getKeywords();
            for (String keyword:keywords) {
                if(text.contains(keyword)){
                    return getLabel();
                }
            }
            return Label.OK;
        }
    }

    class TooLongTextAnalyzer implements TextAnalyzer{
        private int maxLength;

        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public Label processText(String text) {
            return text.length() > maxLength ? Label.TOO_LONG : Label.OK;
        }
    }


    class SpamAnalyzer extends KeywordAnalyzer implements TextAnalyzer{
        private String[] keywords;// = new String[]{":(", "=(", ":|"};

        public SpamAnalyzer(String[] keywords) {
            this.keywords=keywords;
        }

        @Override
        protected String[] getKeywords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.SPAM;
        }
    }

    class NegativeTextAnalyzer extends KeywordAnalyzer implements TextAnalyzer{
        private String[] keywords = new String[]{":(", "=(", ":|"};

        @Override
        protected String[] getKeywords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }
    }


    public Label checkLabels(TextAnalyzer[] analyzers, String text) {
        //put some code here
        for (TextAnalyzer analyzer:analyzers) {
            if(analyzer.processText(text)!=Label.OK){
                return analyzer.processText(text);
            }
        }
        return Label.OK;
    }





}