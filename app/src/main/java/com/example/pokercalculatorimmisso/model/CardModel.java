package com.example.pokercalculatorimmisso.model;

public class CardModel {
    private String name = "";
    private int id = 0;
    private int resId = 0;

    public CardModel() {
    }

    public CardModel(String name, int id, int resId) {
        this.name = name;
        this.id = id;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
