package a.nakana.myandroidarchitecture.n1.mvi

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.n1.mvi.detail.ProjectDetailFragment1
import a.nakana.myandroidarchitecture.n1.mvi.list.ProjectListFragment1
import a.nakana.myandroidarchitecture.service.entity.Project
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment1()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    fun show(project: Project) {
        val projectFragment = ProjectDetailFragment1.forProject(project.name)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment)
                .commit()
    }
}
