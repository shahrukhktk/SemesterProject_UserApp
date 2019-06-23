package com.example.kustevents;

public class ModelClass
{
    private String mMessage;
    private String mImageUri;
    private String date;

    public ModelClass()
    {

    }

    public ModelClass(String mMessage, String mImageUri, String date) {
        this.mMessage = mMessage;
        this.mImageUri = mImageUri;
        this.date = date;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
