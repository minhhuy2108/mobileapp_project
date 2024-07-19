package com.example.newsproject.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newsproject.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {
    // Converts a Source object to a String representation for storing in the database.
    @TypeConverter
    fun sourceToString(source: Source): String{
        // Combines the id and name of the Source into a single comma-separated String.
        return "${source.id},${source.name}"
    }
    // Converts a String representation back into a Source object.
    @TypeConverter
    fun stringToSource(source: String): Source{
        // Splits the comma-separated String into a list and creates a Source object.
        return source.split(',').let { sourceArray ->
            Source(id = sourceArray[0], name = sourceArray[1])
        }
    }
}