package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.common.mvibase.MviViewModel
import a.nakana.myandroidarchitecture.common.mviutil.notOfType
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class ProjectListViewModel1(private val actionProcessorHolder: ProjectListActionProcessorHolder1) : ViewModel(), MviViewModel<ProjectListIntent1, ProjectListViewState1> {

    private val intentsSubject: PublishSubject<ProjectListIntent1> = PublishSubject.create()
    private val statesObservable: Observable<ProjectListViewState1> = compose()

    private val intentFilter: ObservableTransformer<ProjectListIntent1, ProjectListIntent1>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<ProjectListIntent1>(
                        shared.ofType(ProjectListIntent1.InitialIntent::class.java).take(1),
                        shared.notOfType(ProjectListIntent1.InitialIntent::class.java)
                )
            }
        }
    override fun processIntents(intents: Observable<ProjectListIntent1>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<ProjectListViewState1> {
        return statesObservable
    }

    private fun compose() : Observable<ProjectListViewState1> {
        return intentsSubject
                .compose(intentFilter)
                .map<ProjectListAction1>(this::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(ProjectListViewState1.idle(), ProjectListViewModel1.reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: ProjectListIntent1): ProjectListAction1 {
        return ProjectListAction1.LoadAction((intent as ProjectListIntent1.InitialIntent).userName)
    }

    companion object {
        private val reducer = BiFunction { previousState: ProjectListViewState1, result: ProjectListResult1 ->
            when (result) {
                is ProjectListResult1 -> when (result) {
                    is ProjectListResult1.LoadResult.Success -> ProjectListViewState1(result.projects, error = null, isInFright = false)
                    is ProjectListResult1.LoadResult.Failure -> previousState.copy(error = result.error, isInFright = false)
                    is ProjectListResult1.LoadResult.InFlight -> previousState.copy(error = null, isInFright = true)
                }
            }
        }
    }


}
