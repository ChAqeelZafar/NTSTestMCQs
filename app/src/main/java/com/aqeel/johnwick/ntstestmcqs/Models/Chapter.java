package com.aqeel.johnwick.ntstestmcqs.Models;

public class Chapter {
    String chapterName, subjectId, chapterId ;

    public Chapter(String chapterName, String subjectId, String chapterId) {
        this.chapterName = chapterName;
        this.subjectId = subjectId;
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
