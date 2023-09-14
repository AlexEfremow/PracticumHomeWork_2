package com.example.practicumhomework_2.data.local

import android.content.SharedPreferences
import com.example.practicumhomework_2.domain.LocalStorage
import com.example.practicumhomework_2.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences(private val preferences: SharedPreferences, private val gson: Gson = Gson()): LocalStorage {

    val typeToken = object : TypeToken<ArrayList<Track>>() {}.type

    override fun save(track: Track){
        val trackListJson = preferences.getString(KEY, "")
        val trackList = gson.fromJson<ArrayList<Track>>(trackListJson, typeToken) ?: arrayListOf()
        if (trackList.contains(track)) {
            trackList.remove(track)
        }
        trackList.add(track)
        if(trackList.size > 10) {
            trackList.removeFirst()
        }
        val saveTrackList = gson.toJson(trackList)
        preferences.edit().putString(KEY, saveTrackList).apply()
    }

    override fun getTrackList(): List<Track> {
        val trackListJson = preferences.getString(KEY, "")
        return gson.fromJson(trackListJson, typeToken) ?: emptyList()
    }
    override fun clearHistory() {
        preferences.edit().clear().apply()
    }
    override fun getCurrentTheme(): Boolean {
        return preferences.getBoolean(THEME_KEY, false)
    }
    override fun saveTheme(isDarkTheme: Boolean) {
        preferences.edit().putBoolean(THEME_KEY, isDarkTheme).apply()
    }


    companion object {
        const val KEY ="tracks_key"
        const val THEME_KEY ="isDarkMode"
    }
}