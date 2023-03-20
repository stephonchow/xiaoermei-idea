package com.blogfirsttry.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Struct;
class QuickCommentClass {
    public String[] qcContent = new String[]{"1","2","3","4","5"};
    public int[] qcNum = new int[]{0,0,0,0,0};
    public QuickCommentClass() {
    }

    public String[] getQcContent() {
        return qcContent;
    }

    public void setQcContent(String[] qcContent) {
        this.qcContent = qcContent;
    }

    public int[] getQcNum() {
        return qcNum;
    }

    public void setQcNum(int[] qcNum) {
        this.qcNum = qcNum;
    }

}
