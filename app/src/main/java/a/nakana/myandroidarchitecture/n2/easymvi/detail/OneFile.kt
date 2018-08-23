package a.nakana.myandroidarchitecture.n2.easymvi.detail

import a.nakana.myandroidarchitecture.common.mvibase.MviIntent
import a.nakana.myandroidarchitecture.common.mvibase.MviResult
import a.nakana.myandroidarchitecture.common.mvibase.MviViewState
import a.nakana.myandroidarchitecture.common.toDate
import a.nakana.myandroidarchitecture.service.entity.Project
import java.util.*

sealed class ProjectDetailIntent2 : MviIntent {
    data class InitialIntent(val userName : String, val projectName :String) : ProjectDetailIntent2()
}

sealed class ProjectDetailResult2 : MviResult {
    sealed class LoadResult : ProjectDetailResult2() {
        data class Success(val project: Project) : LoadResult()
        data class Failure(val error : Throwable) : LoadResult()
        object InFlight : LoadResult()
    }
}

data class ProjectDetailViewState2(
        val name : String,
        val description : String,
        val language : String,
        val watchers : Int,
        val open_issues : Int,
        val created_at : Date,
        val updated_at : Date,
        val clone_url : String,
        val isInFright : Boolean,
        val error : Throwable? = null
) : MviViewState {
    companion object {
        fun idle(): ProjectDetailViewState2 {
            return ProjectDetailViewState2(
                    name = "",
                    description = "",
                    language = "",
                    watchers = 0,
                    open_issues = 0,
                    created_at = "1970/01/01 00:00:00".toDate()!!,
                    updated_at = "1970/01/01 00:00:00".toDate()!!,
                    clone_url = "",
                    isInFright = true,
                    error = null
            )
        }
    }
}
