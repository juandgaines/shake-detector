package com.example.shakedetector

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shakedetector.dialog.DialogAccelerometer
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(),SensorEventListener {


    private val pm by lazy{packageManager}
    private  var accSensor:Sensor?=null
    private lateinit var  sensManager:SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)){
            sensManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
            accSensor=sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        else{
            DialogAccelerometer().show(supportFragmentManager,DialogAccelerometer::class.java.simpleName)
        }
    }

    override fun onResume() {
        super.onResume()
        accSensor?.let { sensor ->
            sensManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        accSensor?.let {
            sensManager.unregisterListener(this)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) =Unit

    override fun onSensorChanged(event: SensorEvent?) {
        val x= event?.values?.get(0)?.toDouble()
        val y= event?.values?.get(1)?.toDouble()
        val z= event?.values?.get(2)?.toDouble()
        // acceleration vector
        val accVector= getAccVector(x, y, z)

        if (accVector>THRESHOLD) {
            val message = getString(R.string.shake_detected)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getAccVector(x: Double?, y: Double?, z: Double?): Double {
        return sqrt(
            x?.pow(2.0)!!
                .plus(y?.pow(2.0)!!)
                .plus(z?.pow(2.0)!!)
        )
    }

    companion object{
        // acceleration set point m/s2
        const val THRESHOLD=20.0
    }
}
