package com.ora.jsp.beans.poll;

/**
 * This class represents an answer in an online poll application. 
 * It's only intended as an example.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class AnswerBean {
    static private String VALID_IDS = "1 2 3";
    private String answerId;
 
    /**
     * Returns the answerId property value.
     */
    public String getAnswerId() {
        return answerId;
    }
    
    /**
     * Sets the answerId property value.
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
 
    /**
     * Returns true if the answerId matches one of
     * the allowed alternatives.
     */
    public boolean isValid() {
        boolean isValid = false;
        if (answerId != null) {
            isValid = VALID_IDS.indexOf(answerId) != -1;
        }
        return isValid;
    }
}
