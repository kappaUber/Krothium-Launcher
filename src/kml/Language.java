package kml;

/**
 * @author DarkLBP
 * website https://krothium.com
 */
public class Language {
    private static String[] langData = new String[0];
    public static void loadLang(String lang){
        String data = Utils.readURL(Language.class.getResource("/kml/lang/" + lang + ".txt"));
        if (data != null){
            langData = data.split("\n");
        } else {
            data = Utils.readURL(Language.class.getResource("/kml/lang/en-us.txt"));
            if (langData != null){
                langData = data.split("\n");
            }
        }
    }
    public static String get(int line) {
        if (langData.length == 0){
            return null;
        } else if (line <= langData.length) {
            return langData[line - 1];
        }
        return null;
    }
}
