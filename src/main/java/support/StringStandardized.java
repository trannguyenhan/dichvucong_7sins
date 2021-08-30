package support;

public class StringStandardized {
    public static String standardized(String sample){
        sample = sample.replace("<p>", "");
        sample = sample.replace("<br>", "\n"); // done orderOfExecution

        return sample;
    }
}
