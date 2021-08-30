package crawler.models;

import com.google.gson.annotations.SerializedName;

/**
 * Description: profile composition
 */
public class ElementFile {
    @SerializedName("Tên giấy tờ")
    private String name;

    @SerializedName("Mẫu đơn, tờ khai")
    private String declaration;

    @SerializedName("Số lượng - bản chính")
    private String count;

    public ElementFile(){
        this("", "", "0");
    }

    public ElementFile(String name, String declaration, String count) {
        this.name = name;
        this.declaration = declaration;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
