package com.ajscanlan.tapadooprogrammingtest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alexander Scanlan on 03/11/2015
 */
public class Book implements Parcelable{

    //STATE
    private int id;
    private int price; //in minor unit (cents/pence)
    private String author;
    private String title;
    private String ISBN;
    private String currencyCode;
    private String description;

    //CONSTRUCTORS
    public Book(int id, int price, String author, String title, String ISBN, String currencyCode/* String description*/) {
        this.id = id;
        this.price = price;
        this.author = author;
        this.title = title;
        this.ISBN = ISBN;
        this.currencyCode = currencyCode;
        this.description = description;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getDescription(){
        return this.description;
    }

    //SETTERS
    public void setDescription(String s){
        this.description = s;
    }

    //OVERRIDE toString
    @Override
    public String toString() {
        return this.getTitle();
    }


    //PARCEABLE METHODS
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
