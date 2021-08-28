package com.example.dichvucong_7sin.models.data;

import org.springframework.data.mongodb.core.mapping.Field;

public class Formula {
    @Field(value = "Hình thức nộp")
    private String hinhThucNop;

    @Field(value = "Thời hạn giải quyết")
    private String thoiHanGiaiQuyet;

    @Field(value = "Phí, lệ phí")
    private String lePhi;

    @Field(value = "Mô tả")
    private String moTa;

    public String getHinhThucNop() {
        return hinhThucNop;
    }

    public String getThoiHanGiaiQuyet() {
        return thoiHanGiaiQuyet;
    }

    public String getLePhi() {
        return lePhi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setHinhThucNop(String hinhThucNop) {
        this.hinhThucNop = hinhThucNop;
    }

    public void setThoiHanGiaiQuyet(String thoiHanGiaiQuyet) {
        this.thoiHanGiaiQuyet = thoiHanGiaiQuyet;
    }

    public void setLePhi(String lePhi) {
        this.lePhi = lePhi;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "{" +
                "hinhThucNop='" + hinhThucNop + '\'' +
                ", thoiHanGiaiQuyet='" + thoiHanGiaiQuyet + '\'' +
                ", lePhi='" + lePhi + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
