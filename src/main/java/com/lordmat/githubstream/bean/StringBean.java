package com.lordmat.githubstream.bean;

/**
 * This class is wrapper for String for restAPI, JSON format would be:
 * <p>
 * <code> {"data": "my_data"} </code>
 * </p>
 *
 * @author mat
 */
public class StringBean {

    String data;

    public StringBean() {
    }

    public StringBean(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
}
