package com.ailhan.tool.import.util.dataformat

import com.ailhan.tool.import.util.dataformat.impl.CsvJacksonImpl

interface Csv {
    fun <T> fromCsv(csv: String, classOfT: Class<T>): List<T>

    companion object {
        fun get(separator: String = ","): Csv = CsvJacksonImpl(separator)
    }
}
