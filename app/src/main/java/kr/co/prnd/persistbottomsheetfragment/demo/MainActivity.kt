package kr.co.prnd.persistbottomsheetfragment.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var samplePersistBottomFragment: SamplePersistBottomFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        samplePersistBottomFragment =
            SamplePersistBottomFragment.show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    override fun onBackPressed() {
        if (samplePersistBottomFragment?.handleBackKeyEvent() == true) {
            // no-op
        } else {
            super.onBackPressed()
        }

    }
}
