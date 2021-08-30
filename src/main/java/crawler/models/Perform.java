package crawler.models;

import com.google.gson.annotations.SerializedName;

/**
* Description: how to perform
*/
public class Perform {
    @SerializedName("Hình thức nộp")
    private String submissionForm; // Trực tiếp, gián tiếp, dịch vụ bưu chính

    @SerializedName("Thời hạn giải quyết")
    private String dayDuration;

    @SerializedName("Phí, lệ phí")
    private String price;

    @SerializedName("Mô tả")
    private String description;

    public Perform(){
        this("", "", "", "");
    }

    public Perform(String submissionForm, String dayDuration, String price, String description) {
        this.submissionForm = submissionForm;
        this.dayDuration = dayDuration;
        this.price = price;
        this.description = description;
    }

    public String getSubmissionForm() {
        return submissionForm;
    }

    public String getDayDuration() {
        return dayDuration;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setSubmissionForm(String submissionForm) {
        this.submissionForm = submissionForm;
    }

    public void setDayDuration(String dayDuration) {
        this.dayDuration = dayDuration;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
