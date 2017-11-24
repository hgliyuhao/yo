package com.example.lenovo.yot;

/**
 * Created by lenovo on 2017/8/3.
 */
public class List_others {
    public int  position;
    public int idResource;
    public String id;
    public String info;


    public List_others(int position, int idResource, String id,String info){

        this.position = position;
        this.idResource = idResource;
        this.id =id;
        this.info = info;

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

    public String getinfo() {
        return info;
    }
    public void setinfo(String info) {
        this.info = info;
    }


}
