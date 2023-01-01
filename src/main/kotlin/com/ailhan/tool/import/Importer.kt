package com.ailhan.tool.import

import java.io.InputStream

interface Importer {
    fun <T> import(stream: InputStream, classOfT: Class<T>, config: ImportConfig = ImportConfig()): List<T>
}
