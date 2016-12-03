package ro.politiaromana.petitie.mobile.android.tools

/**
 * <pre>
 * capitalizeWords(null)        = null
 * capitalizeWords("")          = ""
 * capitalizeWords("i am FINE") = "I Am FINE"
 * capitalizeWords(null, *)            = null
 * capitalizeWords("", *)              = ""
 * capitalizeWords(*, new char[0])     = *
 * capitalizeWords("i am fine", null)  = "I Am Fine"
 * capitalizeWords("i aM.fine", {'.'}) = "I aM.Fine"
 * </pre>
 */
fun String.capitalizeWords(vararg delimiters: Char): String? {
    val delimLen = if (delimiters.isEmpty()) -1 else delimiters.size
    if (this.isEmpty() || delimLen == 0) {
        return this
    }
    val buffer = this.toCharArray()
    var capitalizeNext = true
    for (i in buffer.indices) {
        val ch = buffer[i]
        if (isDelimiter(ch, delimiters)) {
            capitalizeNext = true
        } else if (capitalizeNext) {
            buffer[i] = Character.toTitleCase(ch)
            capitalizeNext = false
        }
    }

    return String(buffer)
}

/**
 * <pre>
 * capitalizeWordsFully(null, *)            = null
 * capitalizeWordsFully("", *)              = ""
 * capitalizeWordsFully(*, null)            = *
 * capitalizeWordsFully(*, new char[0])     = *
 * capitalizeWordsFully("i aM.fine", {'.'}) = "I am.Fine"
 * </pre>
 */
fun String.capitalizeWordsFully(vararg delimiters: Char): String? {
    var str = this
    val delimLen = if (delimiters.isEmpty()) -1 else delimiters.size
    if (str.isEmpty() || delimLen == 0) {
        return str
    }
    str = str.toLowerCase()
    return str.capitalizeWords(*delimiters)
}

/**
 * Is the character a delimiter
 */
private fun isDelimiter(ch: Char, delimiters: CharArray): Boolean {
    if (delimiters.isEmpty()) {
        return Character.isWhitespace(ch)
    }
    return delimiters.contains(ch)
}
