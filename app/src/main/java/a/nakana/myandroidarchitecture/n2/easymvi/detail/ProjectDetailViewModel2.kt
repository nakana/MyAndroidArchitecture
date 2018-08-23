package a.nakana.myandroidarchitecture.n2.easymvi.detail

import a.nakana.myandroidarchitecture.common.mvibase.MviViewModel
import a.nakana.myandroidarchitecture.common.mviutil.notOfType
import a.nakana.myandroidarchitecture.common.toDate
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.*

class ProjectDetailViewModel2(private val intentProcessorHolder: ProjectDetailIntentProcessorHolder2) : ViewModel(), MviViewModel<ProjectDetailIntent2, ProjectDetailViewState2> {

    private val intentsSubject: PublishSubject<ProjectDetailIntent2> = PublishSubject.create()
    private val statesObservable: Observable<ProjectDetailViewState2> = compose()

    private val intentFilter: ObservableTransformer<ProjectDetailIntent2, ProjectDetailIntent2>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<ProjectDetailIntent2>(
                        shared.ofType(ProjectDetailIntent2.InitialIntent::class.java).take(1),
                        shared.notOfType(ProjectDetailIntent2.InitialIntent::class.java)
                )
            }
        }

    override fun processIntents(intents: Observable<ProjectDetailIntent2>) {
        intents.subscribe(intentsSubject)
    }

    override fun states() : Observable<ProjectDetailViewState2> = statesObservable

    private fun compose() : Observable<ProjectDetailViewState2> {
       return intentsSubject
               .compose(intentFilter)
               .compose(intentProcessorHolder.intentProcessor)
               .scan(ProjectDetailViewState2.idle(), reducer)
               .distinctUntilChanged()
               .replay(1)
               .autoConnect(0)
    }

    companion object {
        internal val reducer = BiFunction { previousState: ProjectDetailViewState2, result: ProjectDetailResult2 ->
            when (result) {
                is ProjectDetailResult2 -> when (result) {
                    is ProjectDetailResult2.LoadResult.Success -> {
                        result.project.let { project ->
                            previousState.copy(
                                    name = project.name,
                                    description = project.description ?: "",
                                    language = project.language ?: "",
                                    watchers = project.watchers,
                                    open_issues = project.open_issues,
                                    created_at = project.created_at ?: "1970/01/01 00:00:00".toDate()!!,
                                    updated_at = project.updated_at ?: "1970/01/01 00:00:00".toDate()!!,
                                    clone_url = project.clone_url ?: "",
                                    isInFright = false
                            )
                        }
                    }
                    is ProjectDetailResult2.LoadResult.Failure -> previousState.copy(error = result.error)
                    is ProjectDetailResult2.LoadResult.InFlight -> previousState
                }
            }
        }
    }
}
