package space.kis.data.mapper

import space.kis.data.model.CountryDTO
import space.kis.data.model.CountryEntity
import space.kis.domain.model.Country

internal fun CountryDTO.toDomainModel(): Country {
    return Country(
        id = id,
        name = name,
        capital = capital,
        area = area,
        population = population,
        flag = flag
    )
}

internal fun CountryEntity.toDomainModel(): Country {
    return Country(
        id = id,
        name = name,
        capital = capital,
        area = area,
        population = population,
        flag = flag
    )
}

internal fun Country.toDataModelEntity(): CountryEntity {
    return CountryEntity(
        id = id,
        name = name,
        capital = capital,
        area = area,
        population = population,
        flag = flag
    )
}

internal fun List<CountryDTO>.toDomainModelsCountryRemote(): List<Country> {
    return map { it.toDomainModel() }
}

internal fun List<CountryEntity>.toDomainModelsCountryLocal(): List<Country> {
    return map { it.toDomainModel() }
}