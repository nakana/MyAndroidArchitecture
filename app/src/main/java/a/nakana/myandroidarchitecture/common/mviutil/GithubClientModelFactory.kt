package a.nakana.myandroidarchitecture.common.mviutil

import a.nakana.myandroidarchitecture.n1.mvi.detail.ProjectDetailActionProcessorHolder1
import a.nakana.myandroidarchitecture.n1.mvi.detail.ProjectDetailViewModel1
import a.nakana.myandroidarchitecture.n1.mvi.list.ProjectListActionProcessorHolder1
import a.nakana.myandroidarchitecture.n1.mvi.list.ProjectListViewModel1
import a.nakana.myandroidarchitecture.n2.easymvi.detail.ProjectDetailIntentProcessorHolder2
import a.nakana.myandroidarchitecture.n2.easymvi.detail.ProjectDetailViewModel2
import a.nakana.myandroidarchitecture.n2.easymvi.list.ProjectListIntentProcessorHolder2
import a.nakana.myandroidarchitecture.n2.easymvi.list.ProjectListViewModel2
import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class GithubClientModelFactory private constructor(
        private val applicationContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ProjectDetailViewModel1::class.java) {
            return ProjectDetailViewModel1(
                    ProjectDetailActionProcessorHolder1(
                            RxProjectRepository())) as T
        }
        if (modelClass == ProjectListViewModel1::class.java) {
            return ProjectListViewModel1(
                    ProjectListActionProcessorHolder1(
                            RxProjectRepository())) as T
        }
        if (modelClass == ProjectDetailViewModel2::class.java) {
            return ProjectDetailViewModel2(
                    ProjectDetailIntentProcessorHolder2(
                            RxProjectRepository())) as T
        }
        if (modelClass == ProjectListViewModel2::class.java) {
            return ProjectListViewModel2(
                    ProjectListIntentProcessorHolder2(
                            RxProjectRepository())) as T
        }
        throw IllegalArgumentException("unknown model class " + modelClass)
    }
    companion object : SingletonHolderSingleArg<GithubClientModelFactory, Context>(::GithubClientModelFactory)
}
