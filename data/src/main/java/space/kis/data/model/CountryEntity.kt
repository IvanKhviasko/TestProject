package space.kis.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val capital: String,
    val area: Long,
    val population: Long,
    var flag: String
)