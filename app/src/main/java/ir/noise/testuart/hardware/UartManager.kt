package ir.noise.testuart.hardware

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.noise.testuart.App
import ir.noise.testuart.App.Companion.appContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UartManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val usbManager: UsbManager = appContext.getSystemService(Context.USB_SERVICE) as UsbManager
    private var serialPort: UsbSerialPort? = null

    companion object {
        const val ACTION_USB_PERMISSION = "ir.noise.testuart.USB_PERMISSION"
    }

    private val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_USB_PERMISSION) {
                synchronized(this) {
                    val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        device?.let {
                            Log.d("UartManager", "Permission granted for device $device")
                            CoroutineScope(Dispatchers.IO).launch {
                                connectToDevice(device)
                            }                        }
                    } else {
                        Log.d("UartManager", "Permission denied for device $device")
                    }
                }
            }
        }
    }

    init {
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        appContext.registerReceiver(usbReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
    }

    fun hasPermission(device: UsbDevice): Boolean {
        return usbManager.hasPermission(device)
    }

    fun requestPermission(device: UsbDevice) {
        val permissionIntent = PendingIntent.getBroadcast(
            appContext,
            0,
            Intent(ACTION_USB_PERMISSION),
            PendingIntent.FLAG_IMMUTABLE
        )
        usbManager.requestPermission(device, permissionIntent)
    }

    suspend fun connectToDevice(device: UsbDevice) {
        withContext(Dispatchers.IO) {
            try {
                val connection = usbManager.openDevice(device)
                val driver = UsbSerialProber.getDefaultProber().probeDevice(device)
                serialPort = driver?.ports?.get(0)
                serialPort?.open(connection)
                serialPort?.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
                Log.d("UartManager", "Device connected: $device")
            } catch (e: IOException) {
                Log.e("UartManager", "Error connecting to device: ${e.message}")
            }
        }
    }

    suspend fun sendData(data: String) {
        withContext(Dispatchers.IO) {
            try {
                serialPort?.let {
                    it.write(data.toByteArray(), 1000)
                    Log.d("UartManager", "Data sent: $data")
                } ?: run {
                    Log.e("UartManager", "serialPort is not initialized")
                }
            } catch (e: IOException) {
                Log.e("UartManager", "Error sending data: ${e.message}")
            }
        }
    }

    fun closeConnection() {
        try {
            serialPort?.close()
            Log.d("UartManager", "USB connection closed")
        } catch (e: IOException) {
            Log.e("UartManager", "Error closing connection: ${e.message}")
        }
    }
}
