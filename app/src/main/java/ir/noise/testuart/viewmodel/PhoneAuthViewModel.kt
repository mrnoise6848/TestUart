package ir.noise.testuart.viewmodel

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.noise.testuart.App.Companion.appContext
import ir.noise.testuart.netowrk.ApiService
import ir.noise.testuart.hardware.UartManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val apiService: ApiService,
    private val uartManager: UartManager

) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    var phoneNumber by mutableStateOf("")
        private set

    fun onAddDigit(digit: Char) {
        if (phoneNumber.length < 11) {
            phoneNumber += digit
        }
    }

    fun onDeleteDigit() {
        if (phoneNumber.isNotEmpty()) {
            phoneNumber = phoneNumber.dropLast(1)
        }
    }

    fun sendPhoneNumberToServer() {
        if (phoneNumber.isBlank()) return

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = apiService.sendPhoneNumber(mapOf("phoneNumber" to phoneNumber))
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d("API_RESPONSE", "Response: $responseBody")

                    if (!responseBody.isNullOrEmpty()) {
                        try {
                            val serverResponse = JSONObject(responseBody)
                            val output = serverResponse.getString("output")
                            Log.d("AAAA ==>", output)

                            val device = getUsbDevice()
                            if (device != null) {
                                if (!uartManager.hasPermission(device)) {
                                    uartManager.requestPermission(device)
                                    _uiState.value = UiState.Error("Phone number successfully sent.\n response=>$output\n But =>Permission Fail!")
                                } else {
                                    uartManager.connectToDevice(device)
                                    if (output.isNotEmpty()) {
                                        uartManager.sendData(output)
                                        _uiState.value = UiState.Success("Phone number successfully sent.\nresponse=>$output\n Then =>Sent data to USB port")
                                    }
                                }
                            } else {
                                Log.e("USB_ERROR", "No USB device found")
                                _uiState.value = UiState.Error("Phone number successfully sent.\nresponse=>$output\n But => No USB device found")
                            }
                        } catch (e: Exception) {
                            Log.e("JSON_ERROR", "Error parsing JSON: ${e.localizedMessage}")
                            _uiState.value = UiState.Error("Error parsing server response: ${e.localizedMessage}")
                        }
                    } else {
                        Log.e("API_ERROR", "Response body is empty")
                        _uiState.value = UiState.Error("Server response is empty")
                    }
                } else {
                    Log.e("API_ERROR", "Error Code: ${response.code()}")
                    Log.e("API_ERROR", "Error Body: ${response.body()}")
                    _uiState.value = UiState.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("NETWORK_ERROR", "Exception: ${e.localizedMessage}")
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            } finally {
                uartManager.closeConnection()
            }
        }
    }


    private fun getUsbDevice(): UsbDevice? {
        val usbManager = appContext.getSystemService(Context.USB_SERVICE) as UsbManager
        val deviceList = usbManager.deviceList
        return deviceList.values.firstOrNull()
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data class Success(val message: String) : UiState()
        data class Error(val error: String) : UiState()
    }
}