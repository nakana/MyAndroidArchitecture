package a.nakana.myandroidarchitecture.n1.mvi.list

import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectListActionProcessorHolder1(
        private val repo : RxProjectRepository
) {
    private val loadProcessor =
            ObservableTransformer<ProjectListAction1.LoadAction, ProjectListResult1> { actions ->
                actions.flatMap { action ->
                    repo
                            .getProjectList(action.userName)
                            .toObservable()
                            .map(ProjectListResult1.LoadResult::Success)
                            .cast(ProjectListResult1.LoadResult::class.java)
                            .onErrorReturn (ProjectListResult1.LoadResult::Failure)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .startWith(ProjectListResult1.LoadResult.InFlight)
                }
            }
    internal var actionProcessor =

//            ObservableTransformer<ProjectListAction1, ProjectListResult1> { actions ->
//                actions.publish { shared ->
//                    Observable.merge<ProjectListResult1>(
//                            shared.ofType(ProjectListAction1.LoadAction::class.java)
//                                    .compose(loadProcessor),
//                            shared.ofType(ProjectListAction1.LoadAction::class.java)
//                                    .compose(loadProcessor))
//                            .mergeWith(
//                                    shared.filter { v ->
//                                        v !is ProjectListAction1.LoadAction
//                                    }.flatMap { w ->
//                                        Observable.error<ProjectListResult1>(
//                                                IllegalArgumentException("Unknown Action type: " + w))
//                                    })
//                }
//            }
            ObservableTransformer<ProjectListAction1, ProjectListResult1> { actions ->
                actions
                        .cast(ProjectListAction1.LoadAction::class.java)
                        .compose(loadProcessor)
            }

}
