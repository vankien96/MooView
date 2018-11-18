package com.vankien96.mooview.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Admin on 19/12/2017.
 */

@Entity(tableName = "movie")
public class MovieEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "releasedate")
    private String releaseDate;

    @ColumnInfo(name = "posterpath")
    private String posterPath;

    public MovieEntity() {

    }

    public MovieEntity(String id, String title, String releaseDate, String posterPath) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
