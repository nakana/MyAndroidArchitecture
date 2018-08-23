package a.nakana.myandroidarchitecture.n1.mvi.detail

import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectDetailActionProcessorHolder1(
        private val repo : RxProjectRepository
) {
    private val loadProcessor =
            ObservableTransformer<ProjectDetailAction1.LoadAction, ProjectDetailResult1> { actions ->
                actions.flatMap { action ->
                    repo
                            .getProjectDetails(action.userName, action.projectName)
                            .toObservable()
                            .map(ProjectDetailResult1.LoadResult::Success)
                            .cast(ProjectDetailResult1.LoadResult::class.java)
                            .onErrorReturn(ProjectDetailResult1.LoadResult::Failure)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .startWith(ProjectDetailResult1.LoadResult.InFlight)
                }
            }
    internal var actionProcessor =
//            ObservableTransformer<ProjectDetailAction1, ProjectDetailResult1> { actions ->
//                actions.publish { shared ->
//                    Observable.merge<ProjectDetailResult1>(
//                            shared.ofType(ProjectDetailAction1.LoadAction::class.java)
//                                    .compose(loadProcessor),
//                            shared.ofType(ProjectDetailAction1.LoadAction::class.java)
//                                    .compose(loadProcessor))
//                            .mergeWith(
//                                    shared.filter { v ->
//                                        v !is ProjectDetailAction1.LoadAction
//                                    }.flatMap { w ->
//                                        Observable.error<ProjectDetailResult1>(
//                                                IllegalArgumentException("Unknown Action type: " + w))
//                                    })
//                }
//            }
            ObservableTransformer<ProjectDetailAction1, ProjectDetailResult1> { actions ->
                actions
                        .cast(ProjectDetailAction1.LoadAction::class.java)
                        .compose(loadProcessor)
            }
}
