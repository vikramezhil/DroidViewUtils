package github.com.vikramezhil.droidviewutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Droid View Utils Example Activity
 * @author vikramezhil
 */

class DvuExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_wheel_picker.setOnClickListener {
            startActivity(Intent(this, WheelPickerActivity::class.java))
        }

        btn_search_view.setOnClickListener {
            startActivity(Intent(this, SearchViewActivity::class.java))
        }

        btn_flipper_view.setOnClickListener {
            startActivity(Intent(this, FlipperViewActivity::class.java))
        }
    }
}
