package edu.vt.mobiledev.pharmascan.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.vt.mobiledev.pharmascan.R
import java.text.SimpleDateFormat
import java.util.*

// Adapter to show all the reports in an recycler view
class ReportAdapter(private var reports: List<Report>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

        // Viewholder for each report item
        class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val drugNameText: TextView = itemView.findViewById(R.id.text_drug_name)
            val counterfeitText: TextView = itemView.findViewById(R.id.text_counterfeit)
            val timestampText: TextView = itemView.findViewById(R.id.text_timestamp)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
            return ReportViewHolder(view)
        }

        override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
            val report = reports[position]

            // Binds the drug name
            holder.drugNameText.text = report.drugName

            // Binds the authentic status with color coding
            holder.counterfeitText.text = if (report.isCounterfeit) "Counterfeit" else "Authentic"
            holder.counterfeitText.setTextColor(
                if (report.isCounterfeit)
                    holder.counterfeitText.context.getColor(android.R.color.holo_red_dark)
                else
                    holder.counterfeitText.context.getColor(android.R.color.holo_green_dark)
            )

            // Binding formatted time stamps
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            holder.timestampText.text = sdf.format(Date(report.timestamp))
        }

        override fun getItemCount(): Int = reports.size

        // Updates the displayed list of reports
        fun updateReports(newReports: List<Report>) {
            reports = newReports
            notifyDataSetChanged()
        }

        /**
         * Returns  report at the specified position.
         * Used for swipe-to-delete or item interaction.
         */
        fun getReportAtPosition(position: Int): Report {
            return reports[position]
        }
}
