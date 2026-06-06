package com.detective.game.adapters
import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup; import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.detective.game.R; import com.detective.game.models.Evidence
class EvidenceAdapter(private val items: MutableList<Evidence>, private val onCollect: (Evidence) -> Unit) : RecyclerView.Adapter<EvidenceAdapter.VH>() {
    inner class VH(v: View) : RecyclerView.ViewHolder(v) { val tvTitle: TextView = v.findViewById(R.id.tvEvidenceTitle); val tvDesc: TextView = v.findViewById(R.id.tvEvidenceDesc); val tvIcon: TextView = v.findViewById(R.id.tvEvidenceIcon) }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_evidence, p, false))
    override fun onBindViewHolder(h: VH, i: Int) {
        val ev = items[i]; h.tvTitle.text = ev.title; h.tvDesc.text = ev.description; h.tvIcon.text = ev.icon
        h.itemView.alpha = if (ev.isCollected) 1f else 0.5f
        h.itemView.setOnClickListener { if (!ev.isCollected) { onCollect(ev); notifyItemChanged(i) } }
    }
    override fun getItemCount() = items.size
}
