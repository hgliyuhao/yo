package com.example.lenovo.yot;

/**
 * Created by lenovo on 2017/9/18.
 */
public class List_yowall {
    public int  position;
    public String photo;
    public String message;
    public String id;
    public String time;
    public double distance;
    public int relationship;
    public String location;
    boolean isYo;
    boolean isLove;
    boolean isHoping;
    int yo;
    int love;
    int hoping;

    public List_yowall(int position, String photo,String message, String id,  String time,double distance, String location,boolean isYo,boolean isLove,boolean isHoping,int yo,int love,int hoping){

        this.position = position;
        this.message = message;
        this.photo = photo;
        this.id =id;
        this.time = time;
        this.distance = distance;
        this.location = location;
        this.isYo = isYo;
        this.isLove = isLove;
        this.isHoping = isHoping;
        this.yo = yo;
        this.love = love;
        this.hoping = hoping;

    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;

    }
    public String getphoto() {
        return photo;
    }
    public void setphoto(String photo) {
        this.photo = photo;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getisYo() {
        return isYo;
    }
    public void setisYo(boolean isYo) {
        this.isYo = isYo;
    }

    public boolean getisLove() {
        return isLove;
    }
    public void setisLove(boolean isLove) {
        this.isLove = isLove;
    }

    public boolean getisHoping() {
        return isHoping;
    }
    public void setisHoping(boolean isHoping) {
        this.isHoping = isHoping;
    }

    public int getyo() {
        return yo;
    }
    public void setyo(int yo) {
        this.yo = yo;
    }

    public int getlove() {
        return love;
    }
    public void setlove(int love) {
        this.love = love;
    }

    public int getHoping() {
        return hoping;
    }
    public void setHoping(int hoping) {
        this.hoping = hoping;
    }
}

