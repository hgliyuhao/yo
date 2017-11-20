package com.example.lenovo.yot;

/**
 * Created by lenovo on 2017/7/31.
 */
public class List_set {
    public int  position;
    public int idResource;
    public String id;
    public String time;
    public double distance;
    public int relationship;

    public List_set(int position, int idResource, String id){

        this.position = position;
        this.idResource = idResource;
        this.id =id;

    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;

    }
    public int getidResource() {
        return idResource;
    }
    public void setBackgroundResource(int idResource) {
        this.idResource = idResource;
    }
    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }


}
