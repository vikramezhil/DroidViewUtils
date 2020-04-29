package github.com.vikramezhil.droidviewutils

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import github.com.vikramezhil.dvu.views.wheel.OnDvuWvListener
import kotlinx.android.synthetic.main.activity_wheel_view.*

/**
 * Wheel picker activity
 * @author vikramezhil
 */

class WheelPickerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wheel_view)

        wv_horizontal.setOnDvuWvWheelListener(object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                val liveValue = "Horizontal wheel - $value"
                tv_horizontal_val.text = liveValue
            }

            override fun onScrolling() {
                Log.d(application.packageName, "wv_horizontal is scrolling")
            }
        })

        wv_vertical_1.setOnDvuWvWheelListener(object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                val liveValue = "Vertical wheel 1 - $value"
                tv_vertical_val.text = liveValue
            }

            override fun onScrolling() {
                Log.d(application.packageName, "wv_vertical_1 is scrolling")
            }
        })

        wv_vertical_2.setOnDvuWvWheelListener(object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                val liveValue = "Vertical wheel 2 - $value"
                tv_vertical_val.text = liveValue
            }

            override fun onScrolling() {
                Log.d(application.packageName, "wv_vertical_2 is scrolling")
            }
        })

        wv_vertical_3.setOnDvuWvWheelListener(object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                val liveValue = "Vertical wheel 3 - $value"
                tv_vertical_val.text = liveValue
            }

            override fun onScrolling() {
                Log.d(application.packageName, "wv_vertical_3 is scrolling")
            }
        })
    }
}