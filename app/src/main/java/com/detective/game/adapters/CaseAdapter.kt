package com.detective.game.adapters
import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup; import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.detective.game.R; import com.detective.game.models.GameCase; import com.detective.game.utils.GameManager
class CaseAdapter(private val cases: List<GameCase>, private val onClick: (GameCase) -> Unit) : RecyclerView.Adapter<CaseAdapter.VH>() {
    inner class VH(v: View) : RecyclerView.ViewHolder(v) { val tvTitle: TextView = v.findViewById(R.id.tvCaseTitle); val tvDesc: TextView = v.findViewById(R.id.tvCaseDesc); val tvDiff: TextView = v.findViewById(R.id.tvDifficulty); val tvStatus: TextView = v.findViewById(R.id.tvStatus) }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_case, p, false))
    override fun onBindViewHolder(h: VH, i: Int) {
        val c = cases[i]; h.tvTitle.text = c.title; h.tvDesc.text = c.description
        h.tvDiff.text = "★".repeat(c.difficulty) + "☆".repeat(3 - c.difficulty)
        val r = GameManager.getResult(c.id)
        h.tvStatus.text = when { r == null -> "🔒 غير محلولة"; r.solved -> "✅ محلولة ${"★".repeat(r.stars)}"; else -> "❌ فشلت" }
        h.itemView.setOnClickListener { onClick(c) }
    }
    override fun getItemCount() = cases.size
}
