package com.detective.game
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.detective.game.databinding.ActivityResultBinding
class ResultActivity : AppCompatActivity() {
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        val b = ActivityResultBinding.inflate(layoutInflater)
        setContentView(b.root)
        val correct = intent.getBooleanExtra("is_correct", false)
        val stars = intent.getIntExtra("stars", 0)
        val time = intent.getIntExtra("time_used", 0)
        b.tvResult.text = if (correct) "تم حل القضية!" else "فشلت في حل القضية"
        b.tvResult.setTextColor(getColor(if (correct) R.color.gold_accent else R.color.red_light))
        b.tvSuspectAccused.text = "المتهم: ${intent.getStringExtra("suspect_name")} (${intent.getStringExtra("suspect_role")})"
        b.tvStars.text = "★".repeat(stars) + "☆".repeat(3 - stars)
        b.tvTime.text = "الوقت: %02d:%02d".format(time / 60, time % 60)
        b.tvEvidence.text = "الأدلة: ${intent.getIntExtra("ev_found",0)} / ${intent.getIntExtra("ev_total",0)}"
        b.tvSolution.text = intent.getStringExtra("solution") ?: ""
        b.btnNextCase.setOnClickListener { startActivity(Intent(this, CaseSelectionActivity::class.java)); finish() }
        b.btnReplay.setOnClickListener { finish() }
        b.btnHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)) }
    }
}
