package com.collegedekho.app.entities;

/**
 * Created by girnarsoft on 11/12/15.
 */
public class PsychometricTestQuestion {
    private String serialNumber;
    private String questionText;
    private int checkedId;
    private String answer="0";

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int ckeckId) {
        this.checkedId = ckeckId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
