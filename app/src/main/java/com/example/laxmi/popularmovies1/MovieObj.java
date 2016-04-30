package com.example.laxmi.popularmovies1;

import java.io.Serializable;

/**
 * Created by laxmi on 30/4/16.
 */
public class MovieObj implements Serializable{
    String image;
    String id;
    String release_date;
    String synopsis;
    String user_rating;
    String title;

    public MovieObj() {}
}