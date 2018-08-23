package a.nakana.myandroidarchitecture.n3.aac.list

import a.nakana.myandroidarchitecture.service.entity.Project
import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import android.arch.lifecycle.ViewModel
import io.reactivex.Single

class ProjectListViewModel3 : ViewModel() {
    fun getProjectList(userName : String) : Single<List<Project>> {
        return RxProjectRepository()
                .getProjectList(userName)
    }
}
