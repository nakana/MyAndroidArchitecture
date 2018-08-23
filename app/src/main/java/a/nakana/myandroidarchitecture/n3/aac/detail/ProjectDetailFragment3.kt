package a.nakana.myandroidarchitecture.n3.aac.detail

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.common.Const
import a.nakana.myandroidarchitecture.service.entity.Project
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_project_details0.*

class ProjectDetailFragment3 : Fragment() {
    private var disposables : CompositeDisposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        disposables = CompositeDisposable()
        return inflater.inflate(R.layout.fragment_project_details0, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)[ProjectDetailViewModel3::class.java]

        disposables?.add(viewModel
                .getDetail(Const.user, arguments?.getString(KEY_PROJECT_NAME) ?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it : Project ->
                    name0.text = it.name
                    project_desc0.text = it.description
                    languages0.text = "プログラミング言語: ${it.language}"
                    project_watchers0.text = "Watchers: ${it.watchers}"
                    project_open_issues0.text = "Open Issues: ${it.open_issues}"
                    project_created_at0.text = "作った時間: ${it.created_at.toString()}"
                    project_updated_at0.text = "最後にアップデータされた時間: ${it.updated_at.toString()}"
                    clone_url0.text = "CloneするURL: ${it.clone_url}"

                    loading_project0.visibility = View.GONE
                    projectdetail0.visibility = View.VISIBLE
                }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables?.dispose()
        disposables = null
    }

    companion object {
        private val KEY_PROJECT_NAME = "project_name"
        fun forProject(projectname: String): ProjectDetailFragment3 {
            val fragment = ProjectDetailFragment3()
            val args = Bundle()

            args.putString(KEY_PROJECT_NAME, projectname)
            fragment.arguments = args

            return fragment
        }
    }
}
