package a.nakana.myandroidarchitecture.n2.easymvi.detail

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_project_details0.*

class ProjectDetailFragment2 : Fragment(), MviView<ProjectDetailIntent2, ProjectDetailViewState2> {
    private var disposable : Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_details0, container, false)
    }

    override fun intents(): Observable<ProjectDetailIntent2> {
        return Observable.just(ProjectDetailIntent2.InitialIntent(Const.user, arguments?.getString(KEY_PROJECT_NAME) ?: ""))
    }

    override fun render(state: ProjectDetailViewState2) {
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

        ViewModelProviders.of(this, GithubClientModelFactory.getInstance(context!!))[ProjectDetailViewModel2::class.java].run {
            processIntents(intents())
            disposable = states().observeOn(AndroidSchedulers.mainThread()).subscribe(::render)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        disposable = null
    }

    companion object {
        private val KEY_PROJECT_NAME = "project_name"
        fun forProject(projectID: String): ProjectDetailFragment2 {
            val fragment = ProjectDetailFragment2()
            val args = Bundle()

            args.putString(KEY_PROJECT_NAME, projectID)
            fragment.arguments = args

            return fragment
        }
    }
}
