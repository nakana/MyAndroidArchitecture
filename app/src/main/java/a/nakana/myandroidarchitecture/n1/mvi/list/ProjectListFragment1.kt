package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.common.Const
import a.nakana.myandroidarchitecture.n1.mvi.MainActivity1
import a.nakana.myandroidarchitecture.common.mvibase.MviView
import a.nakana.myandroidarchitecture.common.mviutil.GithubClientModelFactory
import a.nakana.myandroidarchitecture.common.ui.ErrorDialogFragment
import a.nakana.myandroidarchitecture.service.entity.Project
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_project_list0.*

class ProjectListFragment1 : Fragment(), ProjectListViewHolder1.ItemClickListener, MviView<ProjectListIntent1, ProjectListViewState1> {
    private var disposable : Disposable? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_list0, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!, GithubClientModelFactory.getInstance(context!!))[ProjectListViewModel1::class.java]
        viewModel.processIntents(intents())
        disposable = viewModel.states().subscribe(::render)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        disposable = null
    }

    override fun onItemClick(project : Project) {
        (activity as MainActivity1).show(project)
    }


    override fun render(state: ProjectListViewState1) {
        project_list0.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        if(state.error != null) { ErrorDialogFragment(state.error.toString()).show(activity?.supportFragmentManager, ""); return }
        if(!state.isInFright) loading_projects0.visibility = View.GONE
        project_list0.adapter = ProjectListAdapter1(this@ProjectListFragment1.activity!!, this@ProjectListFragment1, state.projects)
    }

    override fun intents(): Observable<ProjectListIntent1> {
        return Observable.just(ProjectListIntent1.InitialIntent(Const.user))
    }
}


