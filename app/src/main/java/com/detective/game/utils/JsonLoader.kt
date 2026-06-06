package com.detective.game.utils
import android.content.Context
import com.detective.game.models.GameCase
import com.google.gson.Gson
object JsonLoader {
    fun loadCase(ctx: Context, id: Int): GameCase? = try {
        val json = ctx.assets.open("cases/case_00$id.json").bufferedReader().readText()
        Gson().fromJson(json, GameCase::class.java)
    } catch (e: Exception) { null }
    fun loadAll(ctx: Context) = (1..3).mapNotNull { loadCase(ctx, it) }
}
