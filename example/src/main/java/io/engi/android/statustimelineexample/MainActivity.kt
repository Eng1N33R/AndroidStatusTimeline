package io.engi.android.statustimelineexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import io.engi.android.views.StatusTimeline
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("UNUSED_PARAMETER")
    fun randomise(view: View) {
        val timeline = findViewById<StatusTimeline>(R.id.statusTimeline)
        fun randColor(i: Int) : Int {
            return when (i) {
                0 -> ContextCompat.getColor(this.applicationContext, R.color.statusHealthy)
                else -> ContextCompat.getColor(this.applicationContext, R.color.statusSick)
            }
        }

        var i = 0
        val r = Random()
        while (i < timeline.units) {
            timeline.setUnitColor(i++, randColor(r.nextInt(2)))
        }
    }
}
