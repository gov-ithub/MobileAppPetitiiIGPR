package ro.politiaromana.petitie.mobile.android.tools

import java.io.File

fun main(args: Array<String>) {
    val countyFile = File("../docs/judete.csv")

    println(createAndroidResources(countyFile))

    File("./app/src/main/res/values/counties.xml").bufferedWriter().use { it.write(createAndroidResources(countyFile)) }
}

fun createAndroidResources(fromFile: File): String {

    val items = fromFile.readLines()
            .map { it.split(",").firstOrNull()?.capitalizeWordsFully(*charArrayOf(' ', '-')) }
            .filterNotNull()
            .map(::item)
            .reduce { it1, it2 -> it1 + '\n' + it2 }

    return resourcesFile(stringArray(items))
}

fun item(item: String) = ITEM_START + item + ITEM_END

fun stringArray(content: String) = ARRAY_START + '\n' + content + '\n' + ARRAY_END

fun resourcesFile(body: String) = FILE_START + '\n' + body + '\n' + FILE_END

val FILE_START = """<?xml version="1.0" encoding="utf-8"?>
<resources>
"""

val FILE_END = """
</resources>
"""

val ARRAY_START = """    <string-array name="counties">"""

val ARRAY_END = """    </string-array>"""

val ITEM_START = """        <item>"""

val ITEM_END = "</item>"
