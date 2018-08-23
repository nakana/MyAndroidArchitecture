package a.nakana.myandroidarchitecture.n3.aac

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.n3.aac.detail.ProjectDetailFragment3
import a.nakana.myandroidarchitecture.n3.aac.list.ProjectListFragment3
import a.nakana.myandroidarchitecture.service.entity.Project
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment3()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    fun show(project: Project) {
        val projectFragment = ProjectDetailFragment3.forProject(project.name)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment)
                .commit()
    }
}
