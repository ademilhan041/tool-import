package com.ailhan.tool.import.format.excel

import com.ailhan.tool.import.HasImportFormat
import com.ailhan.tool.import.ImportConfig
import com.ailhan.tool.import.ImportFormat
import com.ailhan.tool.import.Importer
import com.ailhan.tool.import.util.dataformat.Csv
import com.ailhan.tool.import.util.removeLast
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class ExcelImporter : Importer, HasImportFormat {
    override fun <T> import(stream: InputStream, classOfT: Class<T>, config: ImportConfig): List<T> {
        val workbook = WorkbookFactory.create(stream)
        workbook.missingCellPolicy = Row.MissingCellPolicy.CREATE_NULL_AS_BLANK

        val sheet: Sheet = workbook.getSheetAt(0)
        val header = createHeader(sheet.getRow(0))
        val body = createBody(prepareDataRows(sheet))
        val csv = toCsv(header, body, config)

        return Csv.get(separator = config.separator).fromCsv(csv, classOfT)
    }

    override fun format() = ImportFormat.EXCEL

    private fun createHeader(row: Row): MutableList<String> {
        val header = mutableListOf<String>()
        for (colIndex in row.firstCellNum until row.lastCellNum) {
            val cellValue = row.getCell(colIndex).cellValueToString()
            if (cellValue.isNotBlank())
                header.add(cellValue)
        }

        return header
    }

    private fun createBody(dataRows: MutableList<Row>): MutableList<List<String>> {
        val valueList = mutableListOf<List<String>>()
        for (row in dataRows) {
            val rowValue = mutableListOf<String>()
            for (colIndex in row.firstCellNum until row.lastCellNum) {
                rowValue.add(row.getCell(colIndex).cellValueToString())
            }

            valueList.add(rowValue)
        }

        return valueList
    }

    private fun toCsv(
        header: MutableList<String>,
        body: MutableList<List<String>>,
        config: ImportConfig
    ): String {
        val headerStr = header.joinToString(config.separator) + "\n"
        var bodyStr = ""
        body
            .filter { it.first().isNotBlank() }
            .map { it.take(header.size) }
            .forEach { bodyStr += it.joinToString(config.separator) + "\n" }
        bodyStr.removeLast()
        return headerStr + bodyStr
    }

    private fun prepareDataRows(sheet: Sheet): MutableList<Row> {
        val rowDataList = mutableListOf<Row>()

        val rowBeginForData = sheet.firstRowNum + 1
        val rowUntilForData = sheet.lastRowNum + 1
        for (rowIndex in rowBeginForData until rowUntilForData)
            rowDataList.add(sheet.getRow(rowIndex))

        return rowDataList
    }
}
