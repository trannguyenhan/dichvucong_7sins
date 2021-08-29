package support;

public class NormalizeText {
    public static String run(String str){
        while(str.contains("</p>")){
            str = str.replace("</p>", "<br />");
        }

        return str;
    }
}
