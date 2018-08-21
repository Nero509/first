package com.example.nero.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.R.attr.y
import android.R.attr.x
import android.widget.Toast



class MainActivity : AppCompatActivity(),SensorEventListener {

    lateinit var sensor:Sensor
    lateinit var sensorManager:SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this@MainActivity,sensor,SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this@MainActivity)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        log("X-AXIS",p0!!.values[0])
        log("Y-AXIS",p0!!.values[1])
        log("Z-AXIS",p0!!.values[2])

        shakeEvent(p0)

        /*

        val alpha = 0.8
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        */

    }

    private var lastUpdate:Long=0
    private val SHAKE_THRESHOLD = 75.25f // m/S**2
    private val MIN_TIME_BETWEEN_SHAKES_MILLISECS = 2000
    private var mLastShakeTime: Long = 0

    private fun shakeEvent(event:SensorEvent){

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
            if (curTime - mLastShakeTime > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = Math.sqrt(Math.pow(x.toDouble(), 2.0) +
                        Math.pow(y.toDouble(), 2.0) +
                        Math.pow(z.toDouble(), 2.0)) - SensorManager.GRAVITY_EARTH
                log("DEMO", "Acceleration is " + acceleration + "m/s^2")

                if(acceleration>40){
                Toast.makeText(this@MainActivity,"Acceleration is " + acceleration + "m/s^2",Toast.LENGTH_SHORT).show()}
                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime
                    Toast.makeText(this@MainActivity,"EVENT SHAKE",Toast.LENGTH_SHORT).show()
                    log("DEMO", "Shake, Rattle, and Roll")
                }
            }
        }



    }

    fun log(st1:String,str:Any){
        Log.d(st1,str.toString())
    }
}
