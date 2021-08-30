package crawler.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PublicService {
    @SerializedName("title")
    private String title;

    @SerializedName("Trình tự thực hiện")
    private String sequence;

    @SerializedName("Cách thức thực hiện")
    private List<Perform> howToPerform;

    @SerializedName("Thành phần hồ sơ")
    private List<ElementFile> profileComposition;

    @SerializedName("Cơ quan thực hiện")
    private String implementingAgencies;

    public PublicService(){
        this("", "", new ArrayList<Perform>(), new ArrayList<ElementFile>(), "");
    }

    public PublicService(String title,
                         String sequence,
                         List<Perform> howToPerform,
                         List<ElementFile> profileComposition,
                         String implementingAgencies) {
        this.title = title;
        this.sequence = sequence;
        this.howToPerform = howToPerform;
        this.profileComposition = profileComposition;
        this.implementingAgencies = implementingAgencies;
    }

    public String getSequence() {
        return sequence;
    }

    public List<Perform> getHowToPerform() {
        return howToPerform;
    }

    public List<ElementFile> getProfileComposition() {
        return profileComposition;
    }

    public String getImplementingAgencies() {
        return implementingAgencies;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setHowToPerform(List<Perform> howToPerform) {
        this.howToPerform = howToPerform;
    }

    public void setProfileComposition(List<ElementFile> profileComposition) {
        this.profileComposition = profileComposition;
    }

    public void setImplementingAgencies(String implementingAgencies) {
        this.implementingAgencies = implementingAgencies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
