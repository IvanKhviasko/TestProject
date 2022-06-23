package space.kis.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import space.kis.data.model.CountryEntity

@Dao
internal interface CountryDao {

    @Query("SELECT * FROM countryentity")
    suspend fun getAll(): List<CountryEntity>

    @Query("SELECT * FROM CountryEntity WHERE id LIKE (:id)")
    suspend fun loadCountryById(id: Long): CountryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)

    @Query("SELECT * FROM countryentity")
    fun subscribeChanges(): Flow<List<CountryEntity>>

    @Delete
    suspend fun delete(country: CountryEntity)

}