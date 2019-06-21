package com.example.newsfeed.network.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import androidx.annotation.NonNull;

import com.example.newsfeed.data.database.FieldConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "results")
public class Result {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    private String type;
    @ColumnInfo(name = "sectionId")
    @SerializedName("sectionId")
    @Expose
    private String sectionId;
    @ColumnInfo(name = "sectionName")
    @SerializedName("sectionName")
    @Expose
    private String sectionName;
    @ColumnInfo(name = "webPublicationDate")
    @SerializedName("webPublicationDate")
    @Expose
    private String webPublicationDate;
    @ColumnInfo(name = "webTitle")
    @SerializedName("webTitle")
    @Expose
    private String webTitle;
    @ColumnInfo(name = "webUrl")
    @SerializedName("webUrl")
    @Expose
    private String webUrl;
    @ColumnInfo(name = "apiUrl")
    @SerializedName("apiUrl")
    @Expose
    private String apiUrl;
    @ColumnInfo(name = "fields")
    @SerializedName("fields")
    @TypeConverters(FieldConverter.class)
    @Expose
    private Fields fields;
    @ColumnInfo(name = "isHosted")
    @SerializedName("isHosted")
    @Expose
    private Boolean isHosted;
    @ColumnInfo(name = "pillarId")
    @SerializedName("pillarId")
    @Expose
    private String pillarId;
    @ColumnInfo(name = "pillarName")
    @SerializedName("pillarName")
    @Expose
    private String pillarName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Boolean getIsHosted() {
        return isHosted;
    }

    public void setIsHosted(Boolean isHosted) {
        this.isHosted = isHosted;
    }

    public String getPillarId() {
        return pillarId;
    }

    public void setPillarId(String pillarId) {
        this.pillarId = pillarId;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

}