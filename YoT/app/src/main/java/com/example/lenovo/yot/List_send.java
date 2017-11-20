package com.example.lenovo.yot;

/**
 * Created by lenovo on 2017/5/21.
 */
public class List_send {
    public int  position;
    public int idResource;
    public String id;
    public String time;
    public double distance;
    public int relationship;

    public List_send(int position, int idResource, String id,  String time,double distance, int relationship){

        this.position = position;
        this.idResource = idResource;
        this.id =id;
        this.time = time;
        this.distance = distance;
        this.relationship = relationship;
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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public void  setdistance(double distance){
        this.distance = distance;
    }
    public double getDistance() {
        return distance;
    }
    public void setrelationship(int relationship){
       this.relationship  = relationship;
    }
    public int  getrelationship() {
        return relationship;
    }
}
