package models;

import models.data.File;
import models.data.Formula;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "data")
public class Data {
    @Id
    private String id;

    @Field(value = "title")
    private String title;

    @Field(value = "Trình tự thực hiện")
    private String TrinhTuThucHien;

    @Field(value = "Cách thức thực hiện")
    private List<Formula> cachThucThucHien;

    @Field(value = "Thành phần hồ sơ")
    private List<File> thanhPhanHoSo;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTrinhTuThucHien() {
        return TrinhTuThucHien;
    }

    public List<Formula> getCachThucThucHien() {
        return cachThucThucHien;
    }

    public List<File> getThanhPhanHoSo() {
        return thanhPhanHoSo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTrinhTuThucHien(String trinhTuThucHien) {
        TrinhTuThucHien = trinhTuThucHien;
    }

    public void setCachThucThucHien(List<Formula> cachThucThucHien) {
        this.cachThucThucHien = cachThucThucHien;
    }

    public void setThanhPhanHoSo(List<File> thanhPhanHoSo) {
        this.thanhPhanHoSo = thanhPhanHoSo;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", TrinhTuThucHien='" + TrinhTuThucHien + '\'' +
                ", cachThucThucHien=" + cachThucThucHien +
                ", thanhPhanHoSo=" + thanhPhanHoSo +
                '}';
    }
}
