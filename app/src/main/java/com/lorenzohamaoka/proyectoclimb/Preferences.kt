package com.lorenzohamaoka.proyectoclimb

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Preferences (context: Context) {
    val PREFS_NAME = "com.lorenzohamaoka.proyectoclimb"
    val SHARED_DISTANCE = "shared_distance"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME,
        MODE_PRIVATE)
    // Creamos la propiedad name que será persistente,
    // además modificamos su getter y setter para que
    // almacene en SharedPreferences.
    var distance: Int
        get() = prefs.getInt(SHARED_DISTANCE, 50)
        set(value) = prefs.edit().putInt(SHARED_DISTANCE, value).apply()

    // Eliminamos las preferencias.
    fun deletePrefs() {
        prefs.edit().apply {
            remove(SHARED_DISTANCE)
            commit()
        }
    }
}