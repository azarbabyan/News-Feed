package com.example.newsfeed.network.data;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Fields {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public Fields(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.thumbnail.hashCode();
        return hash;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==null){
            return false;
        }
        if (!(obj instanceof Fields)){
            return false;
        }
        final Fields fields = (Fields) obj;
        return Objects.equals(this.thumbnail, fields.thumbnail);
    }
}