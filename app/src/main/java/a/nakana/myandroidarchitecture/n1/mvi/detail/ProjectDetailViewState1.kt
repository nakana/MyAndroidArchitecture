package a.nakana.myandroidarchitecture.n1.mvi.detail


import a.nakana.myandroidarchitecture.common.mvibase.MviViewState
import java.util.*

data class ProjectDetailViewState1(
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
        fun idle(): ProjectDetailViewState1 {
            return ProjectDetailViewState1(
                    name = "",
                    description = "",
                    language = "",
                    watchers = 0,
                    open_issues = 0,
                    created_at = Date(),
                    updated_at = Date(),
                    clone_url = "",
                    isInFright = true,
                    error = null
            )
        }
    }
}
