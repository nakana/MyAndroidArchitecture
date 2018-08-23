package a.nakana.myandroidarchitecture.n2.easymvi.list

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.common.Const
import a.nakana.myandroidarchitecture.common.mvibase.MviView
import a.nakana.myandroidarchitecture.common.mviutil.GithubClientModelFactory
import a.nakana.myandroidarchitecture.common.ui.ErrorDialogFragment
import a.nakana.myandroidarchitecture.n2.easymvi.MainActivity2
import a.nakana.myandroidarchitecture.service.entity.Project
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_project_list0.*

class ProjectListFragment2 : Fragment(), ProjectListViewHolder2.ItemClickListener, MviView<ProjectListIntent2, ProjectListViewState2> {
    private var disposable : Disposable? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_list0, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!, GithubClientModelFactory.getInstance(context!!))[ProjectListViewModel2::class.java]
        viewModel.processIntents(intents())
        disposable = viewModel.states().observeOn(AndroidSchedulers.mainThread()).subscribe(::render)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        disposable = null
    }

    override fun onItemClick(project : Project) {
        (activity as MainActivity2).show(project)
    }


    override fun render(state: ProjectListViewState2) {
        if(state.error != null) { ErrorDialogFragment(state.error.toString()).show(activity?.supportFragmentManager, ""); return }

        project_list0.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        if(!state.isInFright) loading_projects0.visibility = View.GONE
        project_list0.adapter = ProjectListAdapter2(this@ProjectListFragment2.activity!!, this@ProjectListFragment2, state.projects)
    }

    override fun intents(): Observable<ProjectListIntent2> {
        return Observable.just(ProjectListIntent2.InitialIntent(Const.user))
    }
}


