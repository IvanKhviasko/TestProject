package space.kis.domain.model

data class Country(
    val id: Long = 0,
    val name: String,
    val capital: String,
    val area: Long,
    val population: Long,
    var flag: String
)