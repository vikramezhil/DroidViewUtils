package github.com.vikramezhil.droidviewutils.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.fragment.FragmentFlipperView
import github.com.vikramezhil.droidviewutils.fragment.FragmentSearchView
import github.com.vikramezhil.droidviewutils.fragment.FragmentSeekBarCircleView
import github.com.vikramezhil.droidviewutils.fragment.FragmentWheelView
import kotlinx.android.synthetic.main.app_bar_dvu.*

/**
 * Droid View Utils Example Activity
 * @author vikramezhil
 */

data class DvuFragmentModel(var tag: Int, var instance: Fragment)

class DvuExampleActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private lateinit var fragments: Array<DvuFragmentModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dvu)

        fragments = arrayOf(DvuFragmentModel(R.id.nav_item_dvu_sv, FragmentSearchView.newInstance()),
                            DvuFragmentModel(R.id.nav_item_dvu_wv, FragmentWheelView.newInstance()),
                            DvuFragmentModel(R.id.nav_item_dvu_fv, FragmentFlipperView.newInstance()),
                            DvuFragmentModel(R.id.nav_item_dvu_sb_cv, FragmentSeekBarCircleView.newInstance()))

        toolbar = findViewById(R.id.dvu_toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.dvu_drawer_layout)
        navView = findViewById(R.id.dvu_nav_view)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            // Starting the fragment transaction for the selected id
            startFragmentTransaction(it.itemId, it.title.toString())

            // Closing the drawer layout
            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

        // Staring the initial fragment transaction
        startFragmentTransaction(R.id.nav_item_dvu_sv, resources.getString(R.string.dvu_search_view))
    }

    /**
     * Starts fragment transaction based on nav item id
     * @param itemId Int The menu item id
     */
    private fun startFragmentTransaction(itemId: Int, title: String) {
        // Setting the tool bar title based on the selected fragment
        dvu_toolbar_title.text = title

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragments.forEach { fragment ->
            if (fragment.tag == itemId) {
                if (supportFragmentManager.findFragmentByTag(fragment.tag.toString()) == null) {
                    fragmentTransaction.add(R.id.dvu_fl_content, fragment.instance, fragment.tag.toString())
                }

                // Showing the selected fragment
                fragmentTransaction.show(fragment.instance)
            } else if (supportFragmentManager.findFragmentByTag(fragment.tag.toString()) != null) {
                // Hiding the others fragments
                fragmentTransaction.hide(fragment.instance)
            }
        }

        fragmentTransaction.commit()
    }
}
