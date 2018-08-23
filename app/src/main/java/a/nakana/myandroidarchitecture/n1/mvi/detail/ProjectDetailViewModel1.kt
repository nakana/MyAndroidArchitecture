package a.nakana.myandroidarchitecture.n1.mvi.detail

import a.nakana.myandroidarchitecture.common.mvibase.MviViewModel
import a.nakana.myandroidarchitecture.common.mviutil.notOfType
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.*

class ProjectDetailViewModel1(private val actionProcessorHolder: ProjectDetailActionProcessorHolder1) : ViewModel(), MviViewModel<ProjectDetailIntent1, ProjectDetailViewState1> {

    private val intentsSubject: PublishSubject<ProjectDetailIntent1> = PublishSubject.create()
    private val statesObservable: Observable<ProjectDetailViewState1> = compose()

    private val intentFilter: ObservableTransformer<ProjectDetailIntent1, ProjectDetailIntent1>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<ProjectDetailIntent1>(
                        shared.ofType(ProjectDetailIntent1.InitialIntent::class.java).take(1),
                        shared.notOfType(ProjectDetailIntent1.InitialIntent::class.java)
                )
            }
        }

    override fun processIntents(intents: Observable<ProjectDetailIntent1>) {
        intents.subscribe(intentsSubject)
    }

    override fun states() : Observable<ProjectDetailViewState1> = statesObservable

    private fun compose() : Observable<ProjectDetailViewState1> {
       return intentsSubject
               .compose(intentFilter)
               .map<ProjectDetailAction1>(this::actionFromIntent)
               .compose(actionProcessorHolder.actionProcessor)
               .scan(ProjectDetailViewState1.idle(), reducer)
               .distinctUntilChanged()
               .replay(1)
               .autoConnect(0)
    }

    private fun actionFromIntent(intent: ProjectDetailIntent1): ProjectDetailAction1 {
        return when (intent) {
            is ProjectDetailIntent1.InitialIntent -> {
                ProjectDetailAction1.LoadAction(intent.userName, intent.projectName)
            }
        }
    }

    companion object {
        private val reducer = BiFunction { previousState: ProjectDetailViewState1, result: ProjectDetailResult1 ->
            when (result) {
                is ProjectDetailResult1 -> when (result) {
                    is ProjectDetailResult1.LoadResult.Success -> {
                        result.project.let { project ->
                            previousState.copy(
                                    name = project.name,
                                    description = project.description ?: "",
                                    language = project.language ?: "",
                                    watchers = project.watchers,
                                    open_issues = project.open_issues,
                                    created_at = project.created_at ?: Date(),
                                    updated_at = project.updated_at ?: Date(),
                                    clone_url = project.clone_url ?: "",
                                    isInFright = false
                            )
                        }
                    }
                    is ProjectDetailResult1.LoadResult.Failure -> previousState.copy(error = result.error)
                    is ProjectDetailResult1.LoadResult.InFlight -> previousState
                }
            }
        }
    }
}
