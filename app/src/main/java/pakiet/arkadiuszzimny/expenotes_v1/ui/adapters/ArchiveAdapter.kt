package pakiet.arkadiuszzimny.expenotes_v1.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pakiet.arkadiuszzimny.expenotes_v1.R
import pakiet.arkadiuszzimny.expenotes_v1.data.db.entities.GoalItem

class ArchiveAdapter(private val dataArrayList: List<GoalItem>) : RecyclerView.Adapter<ArchiveAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.archive_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stateGoal.text = dataArrayList[position].state.toString()
        holder.amountGoal.text = dataArrayList[position].goal.toString()
    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stateGoal: TextView
        val amountGoal: TextView

        init {
            stateGoal = itemView.findViewById(R.id.stateGoal)
            amountGoal = itemView.findViewById(R.id.amountGoal)
        }
    }


}