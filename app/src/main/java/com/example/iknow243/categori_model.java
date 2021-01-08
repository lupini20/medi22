package com.example.iknow243;

public class categori_model {
    private String id;
    private String name;




    public categori_model(String id, String name ){
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
