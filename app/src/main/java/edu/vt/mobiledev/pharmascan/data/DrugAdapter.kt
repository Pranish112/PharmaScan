package edu.vt.mobiledev.pharmascan.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.vt.mobiledev.pharmascan.R

class DrugAdapter : ListAdapter<Drug, DrugAdapter.DrugViewHolder>(DrugDiffCallback()) {

    class DrugViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(drug: Drug) {
            itemView.findViewById<TextView>(R.id.drug_name).text = drug.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drug, parent, false)
        return DrugViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrugViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DrugDiffCallback : DiffUtil.ItemCallback<Drug>() {
    override fun areItemsTheSame(oldItem: Drug, newItem: Drug): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Drug, newItem: Drug): Boolean = oldItem == newItem
}
