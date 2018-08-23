package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.common.mvibase.MviIntent


sealed class ProjectListIntent1 : MviIntent {
    data class InitialIntent(val userName : String) : ProjectListIntent1()
}
