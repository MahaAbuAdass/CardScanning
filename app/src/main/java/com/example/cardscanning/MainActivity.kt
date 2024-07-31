package com.example.cardscanning


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.supremainc.sdk.SvpManager
import com.supremainc.sdk.callback.Punch
import com.supremainc.sdk.service.DeviceListener

class MainActivity : AppCompatActivity() {

    private lateinit var deviceListener: DeviceListener
    private val svpManager = SvpManager()
    private val TAG = "YourApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the deviceListener
        deviceListener = object : DeviceListener() {
            override fun onCardScanCompleted(data: Punch) {
                Log.i(TAG, "result : " + data.result)
                Log.i(TAG, "card number : " + data.displayString)
            }

            override fun onCardScanProgress(scanTimeout: Int) {
                Log.i(TAG, "scanTimeout: $scanTimeout")
            }
        }

        // Initialize SDK
        svpManager.initialize(this, deviceListener)
        svpManager.run()

        findViewById<Button>(R.id.scanButton).setOnClickListener {
            // Scan card
            svpManager.scanCard()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        svpManager.stop()
    }
}

