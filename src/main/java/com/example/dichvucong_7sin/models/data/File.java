package com.example.dichvucong_7sin.models.data;

import org.springframework.data.mongodb.core.mapping.Field;

public class File {
    @Field(value = "Tên giấy tờ")
    private String tenGiayTo;

    @Field(value = "Mẫu đơn, tờ khai")
    private String toKhai;

    @Field(value = "Số lượng - bản chính")
    private String soLuong;

    public String getTenGiayTo() {
        return tenGiayTo;
    }

    public String getToKhai() {
        return toKhai;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setTenGiayTo(String tenGiayTo) {
        this.tenGiayTo = tenGiayTo;
    }

    public void setToKhai(String toKhai) {
        this.toKhai = toKhai;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "{" +
                "tenGiayTo='" + tenGiayTo + '\'' +
                ", toKhai='" + toKhai + '\'' +
                ", soLuong='" + soLuong + '\'' +
                '}';
    }
}
