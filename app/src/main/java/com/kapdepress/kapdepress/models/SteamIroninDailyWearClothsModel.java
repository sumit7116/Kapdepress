package com.kapdepress.kapdepress.models;

public class SteamIroninDailyWearClothsModel {
    int id;
    String clothImg;
    String clothName;
    String clothPrice;
    String clothQuantity;

    public SteamIroninDailyWearClothsModel() {
    }

    public SteamIroninDailyWearClothsModel(int id, String clothImg, String clothName, String clothPrice, String clothQuantity) {
        this.id = id;
        this.clothImg = clothImg;
        this.clothName = clothName;
        this.clothPrice = clothPrice;
        this.clothQuantity = clothQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClothImg() {
        return clothImg;
    }

    public void setClothImg(String clothImg) {
        this.clothImg = clothImg;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public String getClothPrice() {
        return clothPrice;
    }

    public void setClothPrice(String clothPrice) {
        this.clothPrice = clothPrice;
    }

    public String getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(String clothQuantity) {
        this.clothQuantity = clothQuantity;
    }
}
