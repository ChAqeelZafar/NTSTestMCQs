package com.aqeel.johnwick.ntstestmcqs.Models;

public class Subject {
    String subjectName, imgUrl, id ;

    public Subject(String subjectName, String imgUrl, String id) {
        this.subjectName = subjectName;
        this.imgUrl = imgUrl;
        this.id = id ;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
