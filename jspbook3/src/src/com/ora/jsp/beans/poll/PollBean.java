package com.ora.jsp.beans.poll;

import java.math.*;

/**
 * This class maintains a list of answers in an online poll
 * application. It's only intended as an example.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class PollBean {
    private int total;
    private int answer1;
    private int answer2;
    private int answer3;
    
    /**
     * Increments the counter matching the answer as well
     * as the total number of answers.
     */
    public void setAnswer(AnswerBean answer) {
        total++;
        String answerId = answer.getAnswerId();
        if (answerId.equals("1")) {
            answer1++;
        }
        else if (answerId.equals("2")) {
            answer2++;
        }
        else if (answerId.equals("3")) {
            answer3++;
        }
    }
    
    /**
     * Returns the total number of answers.
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Returns the number of alternative 1 answers.
     */
    public int getAnswer1() {
        return answer1;
    }
    
    /**
     * Returns the number of alternative 2 answers.
     */
    public int getAnswer2() {
        return answer2;
    }
    
    /**
     * Returns the number of alternative 3 answers.
     */
    public int getAnswer3() {
        return answer3;
    }
    
    /**
     * Returns the percentage of alternative 1 answers.
     */
    public int getAnswer1Percent() {
        return getPercent(total, answer1);
    }
    
    /**
     * Returns the percentage of alternative 2 answers.
     */
    public int getAnswer2Percent() {
        return getPercent(total, answer2);
    }
    
    /**
     * Returns the percentage of alternative 3 answers.
     */
    public int getAnswer3Percent() {
        return getPercent(total, answer3);
    }
 
    /**
     * Returns an int representing the rounded percentage of
     * answers.
     */
    private int getPercent(int total, int answer) {
        return (int) Math.round(((double) answer / (double) total) * 100);
    }
}
