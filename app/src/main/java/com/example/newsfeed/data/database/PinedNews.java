package com.example.newsfeed.data.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.newsfeed.network.data.Fields;
import com.example.newsfeed.network.data.Result;


import java.util.Objects;
@Entity(tableName = "pinnednews")
public class PinedNews {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "sectionId")
    private String sectionId;
    @ColumnInfo(name = "sectionName")
    private String sectionName;
    @ColumnInfo(name = "webPublicationDate")
    private String webPublicationDate;
    @ColumnInfo(name = "webTitle")
    private String webTitle;
    @ColumnInfo(name = "webUrl")
    private String webUrl;
    @ColumnInfo(name = "apiUrl")
    private String apiUrl;
    @ColumnInfo(name = "fields")
    @TypeConverters(FieldConverter.class)
    private Fields fields;
    @ColumnInfo(name = "isHosted")
    private Boolean isHosted;
    @ColumnInfo(name = "pillarId")
    private String pillarId;
    @ColumnInfo(name = "pillarName")
    private String pillarName;

    public PinedNews() {
    }

    public PinedNews(Result result) {
        id = result.getId();
        type  = result.getType();
        sectionId = result.getSectionId();
        sectionName = result.getSectionName();
        webPublicationDate = result.getWebPublicationDate();
        webTitle = result.getWebTitle();
        webUrl = result.getWebUrl();
        apiUrl = result.getApiUrl();
        fields = result.getFields();
        isHosted = result.getIsHosted();
        pillarId = result.getPillarId();
        pillarName = result.getPillarName();
    }

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
        if (obj==null){
            return false;
        }
        if (!(obj instanceof PinedNews)){
            return false;
        }
        final PinedNews result = (PinedNews) obj;
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
