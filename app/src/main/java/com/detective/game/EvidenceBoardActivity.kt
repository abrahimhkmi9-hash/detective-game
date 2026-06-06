package com.detective.game
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.detective.game.adapters.EvidenceAdapter
import com.detective.game.adapters.SuspectAdapter
import com.detective.game.databinding.ActivityEvidenceBoardBinding
import com.detective.game.utils.GameManager
class EvidenceBoardActivity : AppCompatActivity() {
    private lateinit var b: ActivityEvidenceBoardBinding
    private var timer: CountDownTimer? = null
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityEvidenceBoardBinding.inflate(layoutInflater)
        setContentView(b.root)
        val case_ = GameManager.currentCase ?: return finish()
        b.tvCaseTitle.text = case_.title
        b.tvLocation.text = "📍 ${case_.location}"
        b.tvDescription.text = case_.description
        val evAdapter = EvidenceAdapter(case_.evidence.toMutableList()) { ev ->
            GameManager.collectEvidence(ev.id)
            ev.isCollected = true
            b.tvEvidenceCount.text = "${GameManager.collectedEvidenceIds.size}/${case_.evidence.size} أدلة"
            case_.suspects.find { s -> s.statements.any { st -> st.isLie && st.contradictsEvId == ev.id } }?.let {
                Toast.makeText(this, "⚠️ تناقض مع ${it.name}!", Toast.LENGTH_LONG).show()
                GameManager.revealContradiction(it.id)
            }
        }
        b.rvEvidence.layoutManager = GridLayoutManager(this, 2)
        b.rvEvidence.adapter = evAdapter
        b.tvEvidenceCount.text = "0/${case_.evidence.size} أدلة"
        b.rvSuspects.layoutManager = LinearLayoutManager(this)
        b.rvSuspects.adapter = SuspectAdapter(case_.suspects) {}
        timer = object : CountDownTimer(case_.timeLimit * 1000L, 1000) {
            override fun onTick(ms: Long) {
                val secs = ms / 1000
                b.tvTimer.text = "%02d:%02d".format(secs / 60, secs % 60)
                if (secs < 60) b.tvTimer.setTextColor(getColor(R.color.red_light))
            }
            override fun onFinish() { goToInterrogation() }
        }.start()
        b.btnInterrogate.setOnClickListener { goToInterrogation() }
        b.btnBack.setOnClickListener { finish() }
    }
    private fun goToInterrogation() { timer?.cancel(); startActivity(Intent(this, InterrogationActivity::class.java)) }
    override fun onDestroy() { super.onDestroy(); timer?.cancel() }
}
