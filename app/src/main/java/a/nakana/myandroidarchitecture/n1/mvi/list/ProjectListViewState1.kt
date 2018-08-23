package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.common.mvibase.MviViewState
import a.nakana.myandroidarchitecture.service.entity.Project


data class ProjectListViewState1(
        val projects : List<Project>,
        val error : Throwable? = null,
        val isInFright: Boolean = false) : MviViewState {
    companion object {
        fun idle(): ProjectListViewState1 {
            return ProjectListViewState1(projects = listOf(), error = null, isInFright = false)
        }
    }

}
