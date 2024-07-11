package com.example.milenioapp.ui.ordenes.crearOrden.hallazgos;

public class HygieneItem {
    private long id;
    private String itemName;
    private String isChecked; //S - N - NA

    public HygieneItem(long id,String itemName, String isChecked) {
        this.id = id;
        this.itemName = itemName;
        this.isChecked = isChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String isChecked() {
        return isChecked;
    }

    public void setChecked(String checked) {
        isChecked = checked;
    }
}
