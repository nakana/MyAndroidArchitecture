package a.nakana.myandroidarchitecture.n3.aac.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.service.entity.Project
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ProjectListViewHolder3(view: View) : RecyclerView.ViewHolder(view){
    interface ItemClickListener { fun onItemClick(project : Project) }
    val name = view.findViewById<TextView>(R.id.name3)
    val languages = view.findViewById<TextView>(R.id.languages3)
    val project_watchers = view.findViewById<TextView>(R.id.project_watchers3)
}
