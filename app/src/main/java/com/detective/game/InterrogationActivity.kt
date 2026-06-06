package com.detective.game
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.detective.game.adapters.SuspectAdapter
import com.detective.game.databinding.ActivityInterrogationBinding
import com.detective.game.models.Suspect
import com.detective.game.utils.GameManager
class InterrogationActivity : AppCompatActivity() {
    private lateinit var b: ActivityInterrogationBinding
    private var selectedSuspect: Suspect? = null
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityInterrogationBinding.inflate(layoutInflater)
        setContentView(b.root)
        val case_ = GameManager.currentCase ?: return finish()
        b.tvTitle.text = "الاستجواب — ${case_.title}"
        val adapter = SuspectAdapter(case_.suspects) { suspect ->
            selectedSuspect = suspect
            b.tvSuspectName.text = suspect.name
            b.tvSuspectRole.text = suspect.role
            b.tvStatement.visibility = View.GONE
            b.contradictionIndicator.visibility = if (GameManager.revealedContradictions.contains(suspect.id)) View.VISIBLE else View.GONE
        }
        b.rvSuspects.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        b.rvSuspects.adapter = adapter
        selectedSuspect = case_.suspects[0]
        b.tvSuspectName.text = case_.suspects[0].name
        b.tvSuspectRole.text = case_.suspects[0].role
        b.btnAsk.setOnClickListener {
            selectedSuspect?.let { b.tvStatement.text = it.statements.firstOrNull()?.text ?: "لا يوجد تصريح"; b.tvStatement.visibility = View.VISIBLE }
        }
        b.btnConfront.setOnClickListener {
            val s = selectedSuspect ?: return@setOnClickListener
            b.tvStatement.text = if (GameManager.revealedContradictions.contains(s.id)) "⚠️ لقد كشفنا تناقضاً في أقوالك!" else "لا يوجد دليل كافٍ للمواجهة الآن."
            b.tvStatement.setTextColor(getColor(if (GameManager.revealedContradictions.contains(s.id)) R.color.gold_accent else R.color.text_secondary))
            b.tvStatement.visibility = View.VISIBLE
        }
        b.btnAccuse.setOnClickListener {
            val s = selectedSuspect ?: return@setOnClickListener run { Toast.makeText(this, "اختر المشتبه به أولاً", Toast.LENGTH_SHORT).show() }
            val result = GameManager.calculateResult(s.id)
            GameManager.saveResult(result)
            startActivity(Intent(this, ResultActivity::class.java).apply {
                putExtra("suspect_name", s.name); putExtra("suspect_role", s.role)
                putExtra("is_correct", result.correctSuspect); putExtra("stars", result.stars)
                putExtra("time_used", result.timeUsed); putExtra("ev_found", result.evidenceFound)
                putExtra("ev_total", result.totalEvidence); putExtra("solution", GameManager.currentCase?.solutionText ?: "")
            })
        }
        b.btnBack.setOnClickListener { finish() }
    }
}
