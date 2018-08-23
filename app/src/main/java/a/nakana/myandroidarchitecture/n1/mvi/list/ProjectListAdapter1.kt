package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.service.entity.Project
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ProjectListAdapter1(
        private val context: Context,
        private val itemClickListener: ProjectListViewHolder1.ItemClickListener,
        private val itemList: List<Project>) : RecyclerView.Adapter<ProjectListViewHolder1>(){

    override fun onBindViewHolder(holder: ProjectListViewHolder1, position: Int) {
        holder.name.text = itemList[position].name
        holder.languages.text = "プログラミング言語: ${itemList[position].language}"
        holder.project_watchers.text = "${itemList[position].watchers}"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder1 {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.project_list_item0, parent, false)
        return ProjectListViewHolder1(view).apply {
            view.setOnClickListener {
                itemClickListener.onItemClick(itemList[adapterPosition])
            }
        }
    }
}
