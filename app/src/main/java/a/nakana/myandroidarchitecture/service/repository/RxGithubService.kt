package a.nakana.myandroidarchitecture.service.repository


import a.nakana.myandroidarchitecture.service.entity.Project
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface RxGithubService {
    @GET("users/{user}/repos")
    fun getProjectList(@Path("user") user: String): Single<List<Project>>

    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user: String, @Path("reponame") projectName: String): Single<Project>

    companion object {
        val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }
}
