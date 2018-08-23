package a.nakana.myandroidarchitecture.n0.simple.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.common.Const
import a.nakana.myandroidarchitecture.n0.simple.MainActivity0
import a.nakana.myandroidarchitecture.service.entity.Project
import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_project_list0.*

class ProjectListFragment0 : Fragment(), ProjectListViewHolder0.ItemClickListener {
    private var disposables : CompositeDisposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        disposables = CompositeDisposable()
        return inflater.inflate(R.layout.fragment_project_list0, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        project_list0.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        disposables?.add(RxProjectRepository()
                .getProjectList(Const.user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it : List<Project> ->
                    loading_projects0.visibility = View.GONE
                    project_list0.adapter = ProjectListAdapter0(this@ProjectListFragment0.activity!!, this@ProjectListFragment0, it)
                }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables?.dispose()
        disposables = null
    }

    override fun onItemClick(project : Project) {
        (activity as MainActivity0).show(project)
    }
}
