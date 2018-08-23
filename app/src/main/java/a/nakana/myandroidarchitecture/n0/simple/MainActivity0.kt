package a.nakana.myandroidarchitecture.n0.simple

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.n0.simple.detail.ProjectDetailFragment0
import a.nakana.myandroidarchitecture.n0.simple.list.ProjectListFragment0
import a.nakana.myandroidarchitecture.service.entity.Project
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity0 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment0()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    fun show(project: Project) {
        val projectFragment = ProjectDetailFragment0.forProject(project.name)
        supportFragmentManager
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment)
                .commit()
    }
}
