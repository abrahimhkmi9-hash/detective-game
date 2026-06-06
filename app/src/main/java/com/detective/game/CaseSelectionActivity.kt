package com.detective.game
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.detective.game.adapters.CaseAdapter
import com.detective.game.databinding.ActivityCaseSelectionBinding
import com.detective.game.utils.GameManager
import com.detective.game.utils.JsonLoader
class CaseSelectionActivity : AppCompatActivity() {
    private lateinit var b: ActivityCaseSelectionBinding
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityCaseSelectionBinding.inflate(layoutInflater)
        setContentView(b.root)
        val cases = JsonLoader.loadAll(this)
        b.rvCases.layoutManager = LinearLayoutManager(this)
        b.rvCases.adapter = CaseAdapter(cases) { case_ ->
            GameManager.startCase(case_)
            startActivity(Intent(this, EvidenceBoardActivity::class.java))
        }
        b.btnBack.setOnClickListener { finish() }
    }
}
