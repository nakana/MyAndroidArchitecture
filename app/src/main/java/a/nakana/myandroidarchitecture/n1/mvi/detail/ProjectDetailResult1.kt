package a.nakana.myandroidarchitecture.n1.mvi.detail

import a.nakana.myandroidarchitecture.common.mvibase.MviResult
import a.nakana.myandroidarchitecture.service.entity.Project


sealed class ProjectDetailResult1 : MviResult {
    sealed class LoadResult : ProjectDetailResult1() {
        data class Success(val project: Project) : LoadResult()
        data class Failure(val error : Throwable) : LoadResult()
        object InFlight : LoadResult()
    }
}
