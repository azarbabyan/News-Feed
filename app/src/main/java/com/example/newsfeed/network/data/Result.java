package com.example.newsfeed.network.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.newsfeed.data.database.FieldConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id.hashCode();
        hash = 53 * hash + this.sectionName.hashCode();
        return hash;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Result)) {
            return false;
        }
        final Result result = (Result) obj;
        if (!Objects.equals(this.id, result.id)) {
            return false;
        }
        if (!Objects.equals(this.type, result.type)) {
            return false;
        }
        if (!Objects.equals(this.sectionId, result.sectionId)) {
            return false;
        }
        if (!Objects.equals(this.sectionName, result.sectionName)) {
            return false;
        }
        if (!Objects.equals(this.webPublicationDate, result.webPublicationDate)) {
            return false;
        }
        if (!Objects.equals(this.webTitle, result.webTitle)) {
            return false;
        }
        if (!Objects.equals(this.webUrl, result.webUrl)) {
            return false;
        }
        if (!Objects.equals(this.apiUrl, result.apiUrl)) {
            return false;
        }
        if (!Objects.equals(this.fields, result.fields)) {
            return false;
        }
        if (!Objects.equals(this.isHosted, result.isHosted)) {
            return false;
        }
        if (!Objects.equals(this.pillarName, result.pillarName)) {
            return false;
        }
        return Objects.equals(this.pillarId, result.pillarId);
    }
}
