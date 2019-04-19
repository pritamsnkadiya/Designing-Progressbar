package com.example.pritamsankadiya.myapplication

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    internal val TAG = MainActivity::class.java.simpleName
    var hud: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        label_indeterminate.setOnClickListener(this)
        indeterminate.setOnClickListener(this)
        detail_indeterminate.setOnClickListener(this)
        grace_indeterminate.setOnClickListener(this)
        determinate.setOnClickListener(this)
        annular_determinate.setOnClickListener(this)
        bar_determinate.setOnClickListener(this)
        custom_view.setOnClickListener(this)
        dim_background.setOnClickListener(this)
        custom_color_animate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.label_indeterminate -> {

                hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable {
                        Toast.makeText(
                            this@MainActivity, "You " + "cancelled manually!", Toast
                                .LENGTH_SHORT
                        ).show()
                    }

                scheduleDismiss()
            }
            R.id.indeterminate -> {

                hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                scheduleDismiss()
            }
            R.id.detail_indeterminate -> {

                hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Downloading data")
                scheduleDismiss()
            }
            R.id.grace_indeterminate -> {

                hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setGraceTime(1000)
                scheduleDismiss()
            }
            R.id.determinate -> {

                hud = KProgressHUD.create(this@MainActivity)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel("Please wait")
                simulateProgressUpdate()
            }
            R.id.annular_determinate -> {

                hud = KProgressHUD.create(this@MainActivity)
                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Downloading data")
                simulateProgressUpdate()
            }
            R.id.bar_determinate -> {

                hud = KProgressHUD.create(this@MainActivity)
                    .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                    .setLabel("Please wait")
                simulateProgressUpdate()
            }
            R.id.custom_view -> {

                val imageView = ImageView(this)
                imageView.setBackgroundResource(R.drawable.spin_animation)
                val drawable = imageView.background as AnimationDrawable
                drawable.start()
                hud = KProgressHUD.create(this)
                    .setCustomView(imageView)
                    .setLabel("This is a custom view")
                scheduleDismiss()
            }
            R.id.dim_background -> {

                hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f)
                scheduleDismiss()
            }
            R.id.custom_color_animate -> {

                hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setWindowColor(resources.getColor(R.color.colorPrimary))
                    .setAnimationSpeed(2)
                scheduleDismiss()
            }
            else -> {
                Toast.makeText(this@MainActivity, "Its Else !", Toast.LENGTH_SHORT).show()
            }
        }
        hud?.show()
    }

    private fun scheduleDismiss() {
        val handler = Handler()
        handler.postDelayed({ hud?.dismiss() }, 2000)
    }

    private fun simulateProgressUpdate() {
        hud?.setMaxProgress(100)
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            internal var currentProgress: Int = 0
            override fun run() {
                currentProgress += 1
                hud?.setProgress(currentProgress)
                if (currentProgress == 80) {
                    hud?.setLabel("Almost finish...")
                }
                if (currentProgress < 100) {
                    handler.postDelayed(this, 50)
                }
            }
        }, 100)
    }
}
