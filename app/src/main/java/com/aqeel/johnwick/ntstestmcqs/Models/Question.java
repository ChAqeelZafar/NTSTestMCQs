package com.aqeel.johnwick.ntstestmcqs.Models;

import java.io.Serializable;

public class Question implements Serializable {
    String statement, option1, option2, option3, option4 , correct ;

    public Question(String statement, String option1, String option2, String option3, String option4, String correct) {
        this.statement = statement;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correct = correct;
    }

    public String getStatement() {
        return statement;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getCorrect() {
        return correct;
    }
}

