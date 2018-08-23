package a.nakana.myandroidarchitecture.service.repository

import a.nakana.myandroidarchitecture.service.entity.Project
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

open class RxProjectRepository {
    open fun getProjectList(userId: String): Single<List<Project>> {
        return githubService.getProjectList(userId)
    }

    open fun getProjectDetails(userID: String, projectName: String): Single<Project> {
        return githubService.getProjectDetails(userID, projectName)
    }

    companion object {
        private val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
        private val retrofit = Retrofit.Builder()
                .baseUrl(RxGithubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        private val githubService: RxGithubService = retrofit.create(RxGithubService::class.java)
    }

}
