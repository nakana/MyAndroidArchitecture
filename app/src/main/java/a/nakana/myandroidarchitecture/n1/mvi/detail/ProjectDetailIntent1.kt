package a.nakana.myandroidarchitecture.n1.mvi.detail

import a.nakana.myandroidarchitecture.common.mvibase.MviIntent

sealed class ProjectDetailIntent1 : MviIntent {
    data class InitialIntent(val userName : String, val projectName :String) : ProjectDetailIntent1()
}
