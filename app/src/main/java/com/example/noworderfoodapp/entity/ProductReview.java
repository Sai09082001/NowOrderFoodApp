package com.example.noworderfoodapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductReview implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("rating")
    private int rating;

    public ProductReview() {
    }

    public ProductReview(int id, String comment, int rating) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }
    public ProductReview(String comment, int rating) {
        this.rating = rating;
        this.comment = comment;
    }
    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
