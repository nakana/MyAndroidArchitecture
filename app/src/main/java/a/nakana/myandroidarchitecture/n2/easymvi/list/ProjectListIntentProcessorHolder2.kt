package a.nakana.myandroidarchitecture.n2.easymvi.list

import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

class ProjectListIntentProcessorHolder2(
        private val repo : RxProjectRepository
) {
    private val loadProcessor =
            ObservableTransformer<ProjectListIntent2.InitialIntent, ProjectListResult2> { intents ->
                intents.flatMap { intent ->
                    repo
                            .getProjectList(intent.userName)
                            .toObservable()
                            .map(ProjectListResult2.LoadResult::Success)
                            .cast(ProjectListResult2.LoadResult::class.java)
                            .onErrorReturn(ProjectListResult2.LoadResult::Failure)
                            .subscribeOn(Schedulers.io())
                            .startWith(ProjectListResult2.LoadResult.InFlight)
                }
            }
    internal var intentProcessor =
            ObservableTransformer<ProjectListIntent2, ProjectListResult2> { intents ->
                intents
                        .cast(ProjectListIntent2.InitialIntent::class.java)
                        .compose(loadProcessor)
            }
}
