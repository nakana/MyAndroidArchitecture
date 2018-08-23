package a.nakana.myandroidarchitecture.n2.easymvi.detail

import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

class ProjectDetailIntentProcessorHolder2(
        private val repo : RxProjectRepository
) {
    private val loadProcessor =
            ObservableTransformer<ProjectDetailIntent2.InitialIntent, ProjectDetailResult2> { intents ->
                intents.flatMap { intent ->
                    repo
                            .getProjectDetails(intent.userName, intent.projectName)
                            .toObservable()
                            .map(ProjectDetailResult2.LoadResult::Success)
                            .cast(ProjectDetailResult2.LoadResult::class.java)
                            .onErrorReturn(ProjectDetailResult2.LoadResult::Failure)
                            .subscribeOn(Schedulers.io())
                            .startWith(ProjectDetailResult2.LoadResult.InFlight)
                }
            }
    internal var intentProcessor =
//            ObservableTransformer<ProjectDetailIntent2, ProjectDetailResult2> { intents ->
//                intents.publish { shared ->
//                    Observable.merge<ProjectDetailResult2>(
//                            shared.ofType(ProjectDetailIntent2.InitialIntent::class.java)
//                                    .compose(loadProcessor),
//                            shared.ofType(ProjectDetailIntent2.InitialIntent::class.java)
//                                    .compose(loadProcessor))
//                            .mergeWith(
//                                    shared.filter { v ->
//                                        v !is ProjectDetailIntent2.InitialIntent
//                                    }.flatMap { w ->
//                                        Observable.error<ProjectDetailResult2>(
//                                                IllegalArgumentException("Unknown Action type: " + w))
//                                    })
//                }
//            }
            ObservableTransformer<ProjectDetailIntent2, ProjectDetailResult2> { intents ->
                intents
                        .cast(ProjectDetailIntent2.InitialIntent::class.java)
                        .compose(loadProcessor)
            }


}
