package a.nakana.myandroidarchitecture.n1.mvi.list

sealed class ProjectListAction1 {
    data class LoadAction(val userName: String) : ProjectListAction1()
}
