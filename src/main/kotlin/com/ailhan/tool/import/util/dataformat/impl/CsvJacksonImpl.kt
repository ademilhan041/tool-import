package com.ailhan.tool.import.util.dataformat.impl

import com.ailhan.tool.import.util.dataformat.Csv
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema

class CsvJacksonImpl(private val separator: String) : Csv {
    private val mapper = CsvMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)

    }

    override fun <T> fromCsv(csv: String, classOfT: Class<T>): List<T> {
        val bootstrapSchema = CsvSchema
            .emptySchema()
            .withHeader()
            .withColumnSeparator(separator.toCharArray().first())
        return mapper.readerFor(classOfT)
            .with(bootstrapSchema)
            .readValues<Any>(csv)
            .readAll() as List<T>
    }
}
