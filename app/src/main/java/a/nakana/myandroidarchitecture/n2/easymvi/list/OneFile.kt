package a.nakana.myandroidarchitecture.n2.easymvi.list

import a.nakana.myandroidarchitecture.common.mvibase.MviIntent
import a.nakana.myandroidarchitecture.common.mvibase.MviResult
import a.nakana.myandroidarchitecture.common.mvibase.MviViewState
import a.nakana.myandroidarchitecture.service.entity.Project

sealed class ProjectListIntent2 : MviIntent {
    data class InitialIntent(val userName : String) : ProjectListIntent2()
    data class SelectIntent(val userName : String, val projectName :String) : ProjectListIntent2()
}

sealed class ProjectListResult2 : MviResult {
    sealed class LoadResult : ProjectListResult2() {
        data class Success(val projects: List<Project>) : LoadResult()
        data class Failure(val error : Throwable) : LoadResult()
        object InFlight : LoadResult()
    }
    sealed class SelectResult : ProjectListResult2() {
        data class Success(val project: String) : SelectResult()
        object InFlight : SelectResult()
    }
}

data class ProjectListViewState2(
        val projects : List<Project>,
        val isInFright : Boolean,
        val error : Throwable? = null) : MviViewState {
    companion object {
        fun idle(): ProjectListViewState2 {
            return ProjectListViewState2(projects = listOf(), isInFright = true, error = null)
//            return emptyList<Project>()
        }
    }

}
