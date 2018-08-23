package a.nakana.myandroidarchitecture.n2.easymvi.detail

import a.nakana.myandroidarchitecture.service.entity.Project
import a.nakana.myandroidarchitecture.service.repository.RxProjectRepository
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class IntentProcessorTest {
    @Test
    fun `loadProcessorのテスト`() {

        val projectRepository = mock(RxProjectRepository::class.java)
        `when`(projectRepository.getProjectDetails("clojure", "algo.monads")).thenReturn(Single.just(Project()))

        val intent = ProjectDetailIntent2.InitialIntent("clojure", "algo.monads")
        val expectedFirst = ProjectDetailResult2.LoadResult.InFlight
        val expectedSecond = ProjectDetailResult2.LoadResult.Success(Project())
        Observable
                .just(intent)
                .compose(ProjectDetailIntentProcessorHolder2(projectRepository).intentProcessor)
                .test()
                .await()
                .assertValues(expectedFirst, expectedSecond)
    }
}
