package com.example.registration;

public class WS_items {
    private String ws_item;
    private int id;
    private int drawable;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public WS_items(String ws_item, int id, int drawable) {
        this.ws_item = ws_item;
        this.id = id;
        this.drawable = drawable;
    }

    public String getWs_item() {
        return ws_item;
    }

    public void setWs_item(String ws_item) {
        this.ws_item = ws_item;
    }

    public int getId() {
        return id;
    }

    public void setId(int position) {
        this.id = position;
    }

    @Override
    public String toString() {
        return "WS_item -> " + ws_item;
    }
}
