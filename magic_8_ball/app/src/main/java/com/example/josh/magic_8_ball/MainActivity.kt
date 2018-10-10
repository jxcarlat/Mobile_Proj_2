package com.example.josh.magic_8_ball

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), SensorEventListener {

    var myStrings: Array<String> = arrayOf(
            "It is certain", "You may rely on it",
            "Reply hazy, try again", "Ask again later",
            "Better not tell you now", "My reply is no",
            "Outlook not so good", "Very doubtful",
            "Most likely", "Yes - definitely"
    )
    var mySensorManager: SensorManager? = null
    var myAccelerometer: Sensor? = null
    var mAccel: Double = 0.0
    var mAccelCurrent: Double = 0.0
    var mAccelLast: Double = 0.0
    var mGravity = FloatArray(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mySensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        myAccelerometer = mySensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        fun ClosedRange<Int>.random() =
                Random().nextInt((endInclusive + 1) - start) + start
        var indexPoint: Int = (0..9).random()
        val predictionText: TextView =
                findViewById(R.id.textView)
        val shake: Button =
                findViewById(R.id.button)
            predictionText.text = myStrings[indexPoint]

            shake.setOnClickListener {
                indexPoint = (0..9).random()
                predictionText.text = myStrings[indexPoint]

        }

    }
    override fun onResume(){
        super.onResume()

        mySensorManager!!.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause(){
        super.onPause()

        mySensorManager!!.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        // This is the function that will be updated every time our
        // accelerometer sends an update to our app
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();

            var x = mGravity[0].toDouble();
            var y = mGravity[1].toDouble();
            var z = mGravity[2].toDouble();
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z)
            var delta = mAccelCurrent - mAccelLast
            mAccel = mAccel * 0.9f + delta
            if(mAccel > 1.5)
            {

            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
