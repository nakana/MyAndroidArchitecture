package a.nakana.myandroidarchitecture.n2.easymvi.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.service.entity.Project
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ProjectListViewHolder2(view: View) : RecyclerView.ViewHolder(view){
    interface ItemClickListener { fun onItemClick(project : Project) }
    val name = view.findViewById<TextView>(R.id.name0)
    val languages = view.findViewById<TextView>(R.id.languages0)
    val project_watchers = view.findViewById<TextView>(R.id.project_watchers0)
}
