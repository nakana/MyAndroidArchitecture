package a.nakana.myandroidarchitecture.n3.aac.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.common.Const
import a.nakana.myandroidarchitecture.n3.aac.MainActivity3
import a.nakana.myandroidarchitecture.service.entity.Project
import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_project_list3.*

class ProjectListFragment3 : Fragment(), ProjectListViewHolder3.ItemClickListener {
    private var disposables : CompositeDisposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        disposables = CompositeDisposable()
        return inflater.inflate(R.layout.fragment_project_list3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)[ProjectListViewModel3::class.java]
        project_list3.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        disposables?.add(viewModel
                .getProjectList(Const.user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it : List<Project> ->
                    loading_projects3.visibility = View.GONE
                    project_list3.adapter = ProjectListAdapter3(this@ProjectListFragment3.activity!!, this@ProjectListFragment3, it)
                }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables?.dispose()
        disposables = null
    }

    override fun onItemClick(project : Project) {
        (activity as MainActivity3).show(project)
    }
}
