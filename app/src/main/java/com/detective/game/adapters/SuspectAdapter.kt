package com.detective.game.adapters
import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup; import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.detective.game.R; import com.detective.game.models.Suspect; import com.detective.game.utils.GameManager
class SuspectAdapter(private val suspects: List<Suspect>, private val onClick: (Suspect) -> Unit) : RecyclerView.Adapter<SuspectAdapter.VH>() {
    private var selectedPos = 0
    inner class VH(v: View) : RecyclerView.ViewHolder(v) { val tvName: TextView = v.findViewById(R.id.tvSuspectName); val tvRole: TextView = v.findViewById(R.id.tvSuspectRole); val tvBadge: TextView = v.findViewById(R.id.tvContradictionBadge) }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_suspect, p, false))
    override fun onBindViewHolder(h: VH, i: Int) {
        val s = suspects[i]; h.tvName.text = s.name; h.tvRole.text = s.role
        h.tvBadge.visibility = if (GameManager.revealedContradictions.contains(s.id)) View.VISIBLE else View.GONE
        h.itemView.setBackgroundColor(if (selectedPos == i) 0x331FC8963E.toInt() else 0x00000000)
        h.itemView.setOnClickListener { val old = selectedPos; selectedPos = i; notifyItemChanged(old); notifyItemChanged(i); onClick(s) }
    }
    override fun getItemCount() = suspects.size
}
