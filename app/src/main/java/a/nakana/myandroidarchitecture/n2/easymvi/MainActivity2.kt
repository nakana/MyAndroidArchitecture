package a.nakana.myandroidarchitecture.n2.easymvi

import a.nakana.myandroidarchitecture.R
import a.nakana.myandroidarchitecture.n2.easymvi.detail.ProjectDetailFragment2
import a.nakana.myandroidarchitecture.n2.easymvi.list.ProjectListFragment2
import a.nakana.myandroidarchitecture.service.entity.Project
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment2()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    fun show(project: Project) {
        val projectFragment = ProjectDetailFragment2.forProject(project.name)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment)
                .commit()
    }
}
