package a.nakana.myandroidarchitecture.n1.mvi.detail

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.common.Const
import a.nakana.myandroidarchitecture.common.mvibase.MviView
import a.nakana.myandroidarchitecture.common.mviutil.GithubClientModelFactory
import a.nakana.myandroidarchitecture.common.ui.ErrorDialogFragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_project_details0.*

class ProjectDetailFragment1 : Fragment(), MviView<ProjectDetailIntent1, ProjectDetailViewState1> {
    private var disposable : Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_details0, container, false)
    }

    override fun intents(): Observable<ProjectDetailIntent1> {
        return Observable.just(ProjectDetailIntent1.InitialIntent(Const.user, arguments?.getString(KEY_PROJECT_ID) ?: ""))
    }

    override fun render(state: ProjectDetailViewState1) {
        if(state.isInFright) return
        if(state.error != null) { ErrorDialogFragment(state.error.toString()).show(activity?.supportFragmentManager, ""); return }
        name0.text = state.name
        project_desc0.text = state.description
        languages0.text = "プログラミング言語: ${state.language}"
        project_watchers0.text = "Watchers: ${state.watchers}"
        project_open_issues0.text = "Open Issues: ${state.open_issues}"
        project_created_at0.text = "作った時間: ${state.created_at.toString()}"
        project_updated_at0.text = "最後にアップデータされた時間: ${state.updated_at.toString()}"
        clone_url0.text = "CloneするURL: ${state.clone_url}"

        loading_project0.visibility = View.GONE
        projectdetail0.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewModelProviders.of(this, GithubClientModelFactory.getInstance(context!!))[ProjectDetailViewModel1::class.java].run {
            processIntents(intents())
            disposable = states().subscribe(::render)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        disposable = null
    }

    companion object {
        private val KEY_PROJECT_ID = "project_id"
        fun forProject(projectID: String): ProjectDetailFragment1 {
            val fragment = ProjectDetailFragment1()
            val args = Bundle()

            args.putString(KEY_PROJECT_ID, projectID)
            fragment.arguments = args

            return fragment
        }
    }
}
