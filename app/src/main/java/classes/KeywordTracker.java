package classes;

import java.util.ArrayList;

public class KeywordTracker {
    private ArrayList<String> keywords;

    public KeywordTracker() {
        this.keywords = new ArrayList<String>();
    }

    public void saveToDatabase(User user) {
        //TODO
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
}
