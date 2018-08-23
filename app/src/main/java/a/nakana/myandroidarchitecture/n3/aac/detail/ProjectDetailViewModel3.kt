package a.nakana.myandroidarchitecture.n3.aac.detail

import a.nakana.myandroidarchitecture.service.entity.Project
import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import android.arch.lifecycle.ViewModel
import io.reactivex.Single

class ProjectDetailViewModel3 : ViewModel() {
    fun getDetail(userName : String, projectName : String) : Single<Project> {
        return RxProjectRepository()
                .getProjectDetails(userName, projectName)
    }
}
