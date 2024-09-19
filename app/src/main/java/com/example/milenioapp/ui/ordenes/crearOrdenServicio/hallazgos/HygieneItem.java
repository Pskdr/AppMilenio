package com.example.milenioapp.ui.ordenes.crearOrdenServicio.hallazgos;

public class HygieneItem {
    private long id;
    private long idHigiene;
    private String itemName;
    private String s; //S - N - NA

    public HygieneItem(long id,long idHigiene,String itemName, String s) {
        this.id = id;
        this.idHigiene = idHigiene;
        this.itemName = itemName;
        this.s = s;
    }


    public long getIdHigiene() {
        return idHigiene;
    }

    public void setIdHigiene(long idHigiene) {
        this.idHigiene = idHigiene;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
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

}
