package id.shaderboi.koffie.util

sealed class StringDisplay {
    class String(val string: kotlin.String): StringDisplay()
    class StringRes(@androidx.annotation.StringRes val idRes: Int): StringDisplay()
}
