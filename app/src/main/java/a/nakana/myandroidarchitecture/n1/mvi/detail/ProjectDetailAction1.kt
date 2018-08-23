package a.nakana.myandroidarchitecture.n1.mvi.detail

import a.nakana.myandroidarchitecture.common.mvibase.MviAction

sealed class ProjectDetailAction1 : MviAction {
    data class LoadAction(val userName : String, val projectName: String) : ProjectDetailAction1()
}
