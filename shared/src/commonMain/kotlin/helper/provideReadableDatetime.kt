package helper

import kotlinx.datetime.LocalDateTime

fun provideReadableDateTime(
    dateAsString: String
): String {
    //DateTimeFormatters are not accessible
//    val dateTime = LocalDateTime.parse(dateAsString.substring(0, dateAsString.length - 1))
//    return "${dateTime.year}-${dateTime.monthNumber}-${dateTime.dayOfMonth}"
    //Resolve  java.lang.NoClassDefFoundError (LocalDateTime.kt:65)
    return ""
}