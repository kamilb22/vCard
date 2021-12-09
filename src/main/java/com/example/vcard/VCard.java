package com.example.vcard;

public class VCard {

    public String FN;
    public String TEL;
    public String EMAIL;
    public String URL;

    @Override
    public String toString() {
        return "VCard{" +
                "FN='" + FN + '\'' +
                ", TEL='" + TEL + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
