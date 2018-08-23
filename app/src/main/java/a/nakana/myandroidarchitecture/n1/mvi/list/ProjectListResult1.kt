package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.common.mvibase.MviResult
import a.nakana.myandroidarchitecture.service.entity.Project

sealed class ProjectListResult1 : MviResult {
    sealed class LoadResult : ProjectListResult1() {
        data class Success(val projects: List<Project>) : LoadResult()
        data class Failure(val error : Throwable) : LoadResult()
        object InFlight : LoadResult()
    }
}
