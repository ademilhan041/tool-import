package com.ailhan.tool.import.builder

import com.ailhan.tool.import.ImportFormat
import com.ailhan.tool.import.Importer
import com.ailhan.tool.import.format.csv.CsvImporter
import com.ailhan.tool.import.format.excel.ExcelImporter

class ImporterBuilder {
    private lateinit var _format: ImportFormat

    fun excel(): ImporterBuilder {
        this._format = ImportFormat.EXCEL
        return this
    }

    fun csv(): ImporterBuilder {
        this._format = ImportFormat.CSV
        return this
    }

    fun build(): Importer {
        if (!::_format.isInitialized) throw IllegalStateException("EXPORT_MODULE_BUILD_FAILED")

        return when (_format) {
            ImportFormat.EXCEL -> ExcelImporter()
            ImportFormat.CSV -> CsvImporter()
        }
    }
}
