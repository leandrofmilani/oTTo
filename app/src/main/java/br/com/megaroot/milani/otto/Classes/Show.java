package br.com.megaroot.milani.otto.Classes;

/**
 * Created by milani on 22/10/15.
 */
public class Show {
    private String band, thumbnailUrl, place, city, date, code, description;
    private int country;

    public Show() {
    }

    //Constructor for scheduler
    public Show(String name, String thumbnailUrl, String date, String city, String place) {
        this.band = name;
        this.thumbnailUrl = thumbnailUrl;
        this.date = date;
        this.city = city;
        this.place = place;
    }

    //Constructor for DB
    public Show(String name, String thumbnailUrl, String date, String city, String place, int country, String code) {
        this.band = name;
        this.thumbnailUrl = thumbnailUrl;
        this.country = country;
        this.code = code;
        this.date = date;
        this.city = city;
        this.place = place;
    }

    //Contructor for homescreen shows
    public Show(String name, String description, String thumbnailUrl) {
        this.band = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String name) {
        this.band = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}