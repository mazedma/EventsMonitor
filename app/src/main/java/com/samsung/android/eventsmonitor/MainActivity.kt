/*
 * Copyright 2022 Samsung Electronics Co., Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samsung.android.eventsmonitor

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.samsung.android.eventsmonitor.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

const val TAG = "Events Monitor"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var permissionGranted: Boolean = false
    private var CALL_PHONE_PERMISSION_REQUEST_CODE = 123

    //미디어플레이어 선언
    var mediaPlayer : MediaPlayer?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 앱 실행 시 권한 요청
        requestCallPhonePermission()

        // 음성출력 볼륨 최대로
//        val audio = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        audio.setStreamVolume(AudioManager.STREAM_MUSIC, Int.MAX_VALUE,0)

        permissionGranted =
            applicationContext.checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                permissionGranted = result
            }

        HealthServicesManager.getInstance(applicationContext)
            .setHealthEventsObserver(healthEventsObserver)

        if (!getPermissionGranted()) {
            requestPermissions()
            return
        }
    }

    private fun requestCallPhonePermission() {
        // 전화 걸기 권한이 이미 허용되어 있는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            // 사용자에게 권한 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_REQUEST_CODE
            )
        }
    }

    private var healthEventsObserver: HealthEventsObserver = object : HealthEventsObserver {
        override fun onEventDataChanged(eventData: EventData) {
            runOnUiThread {
                binding.textEventTypeValue.text = eventData.eventType
                binding.textEventTimeValue.text = eventData.eventTime

                val intent = Intent(this@MainActivity, AfterEventActivity::class.java)
                intent.putExtra("eventType", eventData.eventType)
                intent.putExtra("eventTime", eventData.eventTime)
                startActivity(intent)
            }

        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onSubscribeButtonClick(view: View) {
        if (!getPermissionGranted()) {
            Toast.makeText(
                applicationContext,
                R.string.not_all_permissions_granted,
                Toast.LENGTH_LONG
            ).show()
            requestPermissions()
            return
        }

        lifecycleScope.launch {
            if (!HealthServicesManager.getInstance(applicationContext)
                    .hasHealthEventsCapability()
            ) {
                Toast.makeText(
                    applicationContext,
                    R.string.not_all_health_events_available,
                    Toast.LENGTH_LONG
                ).show()
                return@launch
            }
            if (HealthServicesManager.getInstance(applicationContext).isRegistered()) {
                HealthServicesManager.getInstance(applicationContext).unregisterHealthEventsData()
                binding.btnSubscribe.setText(R.string.subscribe_label)
            } else {
                HealthServicesManager.getInstance(applicationContext).registerForHealthEventsData()
                binding.btnSubscribe.setText(R.string.unsubscribe_label)
            }
        }
    }

    private fun getPermissionGranted(): Boolean {
        return permissionGranted
    }

    private fun requestPermissions() {
        permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
    }
}