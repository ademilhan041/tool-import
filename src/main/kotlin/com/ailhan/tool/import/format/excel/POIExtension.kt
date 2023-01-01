package com.ailhan.tool.import.format.excel

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import java.time.LocalTime
import kotlin.math.floor

fun Cell.cellValueToString(): String {
    return when (cellType) {
        CellType.STRING -> {
            if (stringCellValue.contains("\n") || stringCellValue.contains("\t")) "\"$stringCellValue\""
            else stringCellValue
        }

        CellType.NUMERIC -> {
            if (DateUtil.isCellDateFormatted(this)) {
                if (localDateTimeCellValue.toLocalTime() == LocalTime.MIDNIGHT) localDateTimeCellValue.toLocalDate()
                    .toString()
                else localDateTimeCellValue.toString()
            } else {
                if ((numericCellValue == floor(numericCellValue))) numericCellValue.toInt().toString()
                else numericCellValue.toString()
            }
        }

        CellType.BOOLEAN -> booleanCellValue.toString()
        CellType.BLANK -> ""
        else -> ""
    }
}
