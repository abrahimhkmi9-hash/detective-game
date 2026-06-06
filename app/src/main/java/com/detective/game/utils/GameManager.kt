package com.detective.game.utils
import android.content.Context
import android.content.SharedPreferences
import com.detective.game.models.GameCase
import com.detective.game.models.GameResult
import com.google.gson.Gson
object GameManager {
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()
    var currentCase: GameCase? = null
    var startTime: Long = 0L
    val collectedEvidenceIds = mutableSetOf<Int>()
    val revealedContradictions = mutableSetOf<Int>()
    fun init(ctx: Context) { prefs = ctx.getSharedPreferences("detective_prefs", Context.MODE_PRIVATE) }
    fun startCase(c: GameCase) { currentCase = c; startTime = System.currentTimeMillis(); collectedEvidenceIds.clear(); revealedContradictions.clear() }
    fun collectEvidence(id: Int) { collectedEvidenceIds.add(id) }
    fun revealContradiction(id: Int) { revealedContradictions.add(id) }
    fun getElapsedSeconds() = ((System.currentTimeMillis() - startTime) / 1000).toInt()
    fun calculateResult(guessedId: Int): GameResult {
        val c = currentCase ?: return GameResult(0,false,0,0,false,0,0,0)
        val t = getElapsedSeconds()
        val correct = guessedId == c.guiltyId
        val ev = collectedEvidenceIds.size
        val stars = if (!correct) 0 else minOf(3, 1 + (if (t <= c.timeLimit*0.4) 2 else if (t <= c.timeLimit*0.7) 1 else 0) + (if (ev >= c.evidence.size) 1 else 0))
        return GameResult(c.id, correct, t, c.timeLimit, correct, ev, c.evidence.size, stars)
    }
    fun saveResult(r: GameResult) { val old = getResult(r.caseId); if (old == null || r.stars > old.stars) prefs.edit().putString("result_${r.caseId}", gson.toJson(r)).apply() }
    fun getResult(caseId: Int): GameResult? { val j = prefs.getString("result_$caseId", null) ?: return null; return gson.fromJson(j, GameResult::class.java) }
    fun totalSolved() = (1..3).count { getResult(it)?.solved == true }
    fun playerLevel() = (totalSolved() / 2) + 1
}
