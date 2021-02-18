package com.example.coronatracker.Models;

public class Country {

    public String name;
    public String imageResourceId;
    public String cases;
    public String deaths;
    public String rating;
    public String notes;

    public Country(String name, String cases, String deaths, String imageResourceId, String notes,
                   String rating){
        this.name = name;
        this.cases = cases;
        this.deaths = deaths;
        this.imageResourceId = imageResourceId;
        this.notes = notes;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}