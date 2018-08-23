package a.nakana.myandroidarchitecture.n2.easymvi.list

import a.nakana.myandroidarchitecture.common.mvibase.MviViewModel
import a.nakana.myandroidarchitecture.common.mviutil.notOfType
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class ProjectListViewModel2(private val intentProcessorHolder: ProjectListIntentProcessorHolder2) : ViewModel(), MviViewModel<ProjectListIntent2, ProjectListViewState2> {

    private val intentsSubject: PublishSubject<ProjectListIntent2> = PublishSubject.create()
    private val statesObservable: Observable<ProjectListViewState2> = compose()

    private val intentFilter: ObservableTransformer<ProjectListIntent2, ProjectListIntent2>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<ProjectListIntent2>(
                        shared.ofType(ProjectListIntent2.InitialIntent::class.java).take(1),
                        shared.notOfType(ProjectListIntent2.InitialIntent::class.java)
                )
            }
        }
    override fun processIntents(intents: Observable<ProjectListIntent2>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<ProjectListViewState2> {
        return statesObservable
    }

    private fun compose() : Observable<ProjectListViewState2> {
        return intentsSubject
                .compose(intentFilter)
                .compose(intentProcessorHolder.intentProcessor)
                .scan(ProjectListViewState2.idle(), ProjectListViewModel2.reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    companion object {
        private val reducer = BiFunction { previousState: ProjectListViewState2, result: ProjectListResult2 ->
            when (result) {
                is ProjectListResult2 -> when (result) {
                    is ProjectListResult2.LoadResult.Success -> ProjectListViewState2(result.projects, false, error = null)
                    is ProjectListResult2.LoadResult.Failure -> previousState.copy(error = result.error)
                    is ProjectListResult2.LoadResult.InFlight -> previousState
                    else -> previousState
                }
            }
        }
    }


}
