package com.example.practicumhomework_2.search.data.local

import android.content.SharedPreferences
import com.example.practicumhomework_2.player.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchPreferencesImpl(private val preferences: SharedPreferences, private val gson: Gson = Gson()):
    SearchPreferences {

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


    companion object {
        const val KEY ="tracks_key"
    }
}