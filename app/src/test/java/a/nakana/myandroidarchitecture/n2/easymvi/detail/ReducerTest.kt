package a.nakana.myandroidarchitecture.n2.easymvi.detail

import a.nakana.myandroidarchitecture.service.entity.Project
import org.assertj.core.api.Assertions
import org.junit.Test

class ReducerTest {
    @Test
    fun `reducerのテスト--成功`() {
        val previousState = ProjectDetailViewState2.idle() // 事前ステートを準備
        val intentProcessorResult = ProjectDetailResult2.LoadResult.Success(Project()) // ActionProcessorの結果を準備
        val result = ProjectDetailViewModel2.reducer.apply(previousState, intentProcessorResult) // reducerを通す
        val expected = ProjectDetailViewState2.idle().copy(isInFright = false) // 期待値を準備
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `reducerのテスト--失敗`() {
        val previousState = ProjectDetailViewState2.idle() // 事前ステートを準備
        val error = RuntimeException()
        val intentProcessorResult = ProjectDetailResult2.LoadResult.Failure(error) // ActionProcessorの結果を準備
        val result = ProjectDetailViewModel2.reducer.apply(previousState, intentProcessorResult) // reducerを通す
        val expected = ProjectDetailViewState2.idle().copy(error = error) // 期待値を準備
        Assertions.assertThat(result).isEqualTo(expected)
    }
}
