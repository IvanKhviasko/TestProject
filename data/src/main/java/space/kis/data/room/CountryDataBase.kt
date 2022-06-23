package space.kis.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import space.kis.data.model.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
internal abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}