package com.ailhan.tool.import.format.csv

import com.ailhan.tool.import.HasImportFormat
import com.ailhan.tool.import.ImportConfig
import com.ailhan.tool.import.ImportFormat
import com.ailhan.tool.import.Importer
import com.ailhan.tool.import.util.dataformat.Csv
import com.ailhan.tool.import.util.string
import java.io.InputStream

class CsvImporter : Importer, HasImportFormat {
    override fun <T> import(stream: InputStream, classOfT: Class<T>, config: ImportConfig): List<T> {
        return Csv.get(config.separator).fromCsv(stream.string(), classOfT)
    }

    override fun format() = ImportFormat.CSV
}
