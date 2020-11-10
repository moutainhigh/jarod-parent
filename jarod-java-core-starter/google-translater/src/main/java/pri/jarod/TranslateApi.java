package pri.jarod;

/**
 * @author Jarod.Kong
 * @date 2020/9/15 14:55
 */
public enum TranslateApi {
    /**
     *
     */
    INSTANCE;
    /**
     *
     */
    private GoogleApi googleApi;

    private TranslateApi(){
        googleApi();
    }

    public GoogleApi googleApi() {
        if (googleApi == null) {
            googleApi = new GoogleApi();
        }
        return googleApi;
    }

    public String translateZhToEn(String word) throws Exception {
        return googleApi.translate(word, Language.ZH, Language.EN);
    }

    public String translateEnToZh(String word) throws Exception {
        return googleApi.translate(word, Language.EN, Language.ZH);
    }

    public static class Language {
        public static final String ZH = "zh";
        public static final String EN = "en";
    }
}
