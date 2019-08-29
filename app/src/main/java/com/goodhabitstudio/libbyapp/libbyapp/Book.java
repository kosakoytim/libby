package com.goodhabitstudio.libbyapp.libbyapp;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by dondi-mac on 16/11/17.
 */

public class Book {

    public Book() {
    }
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String title;
    private String author;

    public String getDisplay_reading_time() {
        return display_reading_time;
    }

    public void setDisplay_reading_time(String display_reading_time) {
        this.display_reading_time = display_reading_time;
    }

    public String getDisplay_reading_days() {
        return display_reading_days;
    }

    public void setDisplay_reading_days(String display_reading_days) {
        this.display_reading_days = display_reading_days;
    }

    public int getTimes_read_a_day() {
        return times_read_a_day;
    }

    public void setTimes_read_a_day(int times_read_a_day) {
        this.times_read_a_day = times_read_a_day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getProgress_page() {
        return progress_page;
    }

    public void setProgress_page(int progress_page) {
        this.progress_page = progress_page;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    private String display_reading_time;
    private String display_reading_days;
    private int times_read_a_day;
    private String status;
    private int pages;
    private int progress_page;
    private String start_date;

}
