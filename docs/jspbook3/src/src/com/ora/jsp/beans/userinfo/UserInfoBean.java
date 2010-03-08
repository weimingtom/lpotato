package com.ora.jsp.beans.userinfo;

import java.io.*;
import java.util.*;
import com.ora.jsp.util.*;

/**
 * This class contains information about a user. It's used to show
 * how a bean can be used to capture and validate user input.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 2.0
 */
public class UserInfoBean implements Serializable {
    // Validation constants
    private static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static String[] GENDER_LIST = {"m", "f"};
    private static String[] FOOD_LIST = {"z", "p", "c"};
    private static int MIN_LUCKY_NUMBER = 1;
    private static int MAX_LUCKY_NUMBER = 100;

    // Properties
    private String birthDate;
    private String emailAddr;
    private String[] food;
    private String luckyNumber;
    private String gender;
    private String userName;

    /**
     * Returns the birthDate property value
     */
    public String getBirthDate() {
        return (birthDate == null ? "" : birthDate);
    }

    /**
     * Sets the birthDate property value
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Validates the birthDate property
     *
     * @return true if the property is set to a valid value, false otherwise.
     */
    public boolean isBirthDateValid() {
        boolean isValid = false;
        if (birthDate != null &&
            StringFormat.isValidDate(birthDate, DATE_FORMAT_PATTERN)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Returns the emailAddr property value
     */
    public String getEmailAddr() {
        return (emailAddr == null ? "" : emailAddr);
    }

    /**
     * Sets the emailAddr property value
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    /**
     * Validates the emailAddr property
     *
     * @return true if the property is set to a valid value, false otherwise.
     */
    public boolean isEmailAddrValid() {
        boolean isValid = false;
        if (emailAddr != null &&
                 StringFormat.isValidEmailAddr(emailAddr)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Returns the food property value
     */
    public String[] getFood() {
        return (food == null ? new String[0] : food);
    }

    /**
     * Sets the food property value
     */
    public void setFood(String[] food) {
        this.food = food;
    }

    /**
     * Validates the food property
     *
     * @return true if the property is set to a valid value, false otherwise.
     */
    public boolean isFoodValid() {
        boolean isValid = false;
        if (food == null ||
                 StringFormat.isValidString(food, FOOD_LIST, true)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Returns true if the food property includes the marker for 
     * pizza
     */
    public boolean isPizzaSelected() {
        return isFoodTypeSelected("z");
    }

    /**
     * Returns true if the food property includes the marker for 
     * pasta
     */
    public boolean isPastaSelected() {
        return isFoodTypeSelected("p");
    }

    /**
     * Returns true if the food property includes the marker for 
     * Chinese
     */
    public boolean isChineseSelected() {
        return isFoodTypeSelected("c");
    }

    /**
     * Returns the luckyNumber property value
     */
    public String getLuckyNumber() {
        return (luckyNumber == null ? "" : luckyNumber);
    }

    /**
     * Sets the luckyNumber property value
     */
    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    /**
     * Validates the luckyNumber property
     *
     * @return true if the property is set to a valid value, false otherwise.
     */
    public boolean isLuckyNumberValid() {
        boolean isValid = false;
        if (luckyNumber != null &&
                 StringFormat.isValidInteger(luckyNumber, MIN_LUCKY_NUMBER,
                   MAX_LUCKY_NUMBER)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Returns the gender property value
     */
    public String getGender() {
        return (gender == null ? "" : gender);
    }

    /**
     * Sets the gender property value
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Validates the gender property
     *
     * @return true if the property is set to a valid value, false otherwise.
     */
    public boolean isGenderValid() {
        boolean isValid = false;
        if (gender != null &&
                 StringFormat.isValidString(gender, GENDER_LIST, true)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Returns the userName property value
     */
    public String getUserName() {
        return (userName == null ? "" : userName);
    }

    /**
     * Sets the userName property value
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Validates the gender property
     *
     * @return true if the property is set to a valid value, false otherwise.
     */
    public boolean isUserNameValid() {
        boolean isValid = false;
        if (userName != null) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Returns true if all property values have valid values
     * (they are only set if the value is valid).
     */
    public boolean isValid() {
        return isBirthDateValid() && isEmailAddrValid() &&
            isFoodValid() && isLuckyNumberValid() && 
            isGenderValid() && isUserNameValid();
    }

    /**
     * Returns true if the food property includes the specified food 
     * type
     */
    private boolean isFoodTypeSelected(String foodType) {
        if (food == null) {
            return false;
        }
        boolean selected = false;
        for (int i = 0; i < food.length; i++) {
            if (food[i].equals(foodType)) {
                selected = true;
                break;
            }
        }
        return selected;
    }
}





