package com.detective.game
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.detective.game.databinding.ActivityMainBinding
import com.detective.game.utils.GameManager
class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.tvLevel.text = "المستوى ${GameManager.playerLevel()}"
        b.tvSolved.text = "القضايا المحلولة: ${GameManager.totalSolved()}"
        b.btnNewCase.setOnClickListener { startActivity(Intent(this, CaseSelectionActivity::class.java)) }
        b.btnArchive.setOnClickListener { startActivity(Intent(this, CaseSelectionActivity::class.java)) }
    }
    override fun onResume() {
        super.onResume()
        b.tvLevel.text = "المستوى ${GameManager.playerLevel()}"
        b.tvSolved.text = "القضايا المحلولة: ${GameManager.totalSolved()}"
    }
}
