package com.example.nerdlauncher

import android.content.pm.ActivityInfo
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(
    private val activities: List<Pair<ResolveInfo, String>>,
    private val onItemClick: (activityInfo: ActivityInfo) -> Unit
) :
    RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {

    class ActivityHolder(itemView: View, val onItemClick: (activityInfo: ActivityInfo) -> Unit) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val nameTextView = itemView as TextView
        private lateinit var resolveInfo: ResolveInfo

        init {
            nameTextView.setOnClickListener(this)
        }

        fun bind(pair: Pair<ResolveInfo, String>) {
            this.resolveInfo = pair.first
            nameTextView.text = pair.second
        }

        override fun onClick(view: View?) {
            val activityInfo = resolveInfo.activityInfo
            onItemClick(activityInfo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ActivityHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        val resolveInfo = activities[position]
        holder.bind(resolveInfo)
    }

    override fun getItemCount(): Int = activities.size
}
