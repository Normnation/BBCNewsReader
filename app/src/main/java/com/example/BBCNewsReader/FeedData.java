package com.example.BBCNewsReader;


public class FeedData {

    int id;
    String DateVar;
    String descriptionVar;
    String linkVar;
    String titleVar;



    public String getDescriptionVar() {
        return descriptionVar;
    }

    public void setDescriptionVar(String descriptionVar) {
        this.descriptionVar = descriptionVar;
    }

    public String getLinkVar() {
        return linkVar;
    }

    public void setLinkVar(String linkVar) {
        this.linkVar = linkVar;
    }


    public String getDateVar() {
        return DateVar;
    }

    public void setDateVar(String dateVar) {
        DateVar = dateVar;
    }


    public FeedData() {


    }


    public void setTitleVar(String titleVar) {
        this.titleVar = titleVar;

    }

    public String getTitleVar() {
        return this.titleVar;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}