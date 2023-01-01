package com.ailhan.tool.import.util

import java.io.InputStream
import java.nio.charset.Charset

fun String.removeLast() = substring(0, length - 1)

fun InputStream.string(charset: Charset = Charset.forName("UTF-8")) = bufferedReader(charset).use { it.readText() }