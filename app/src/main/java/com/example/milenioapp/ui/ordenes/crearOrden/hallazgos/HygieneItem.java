package com.example.milenioapp.ui.ordenes.crearOrden.hallazgos;

public class HygieneItem {
    private long id;
    private long idHigiene;
    private String itemName;
    private String isChecked; //S - N - NA

    public HygieneItem(long id,long idHigiene,String itemName, String isChecked) {
        this.id = id;
        this.idHigiene = idHigiene;
        this.itemName = itemName;
        this.isChecked = isChecked;
    }

    public long getIdHigiene() {
        return idHigiene;
    }

    public void setIdHigiene(long idHigiene) {
        this.idHigiene = idHigiene;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
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
