package com.example.newsfeed.data.database;


import androidx.room.TypeConverter;

import com.example.newsfeed.network.data.Fields;

public class FieldConverter {
    @TypeConverter
    public Fields fromUrl(String url) {
        return url == null ? null : new Fields(url);
    }

    @TypeConverter
    public String fromFields(Fields fields) {
        return fields == null ? null : fields.getThumbnail();
    }
}
