package com.example.milenioapp.ui.ordenes.crearOrden;

public class HygieneItem {
    private long id;
    private String itemName;
    private boolean isChecked;

    public HygieneItem(long id,String itemName, boolean isChecked) {
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
