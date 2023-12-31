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

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking

class RegisterForBackgroundDataWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.i(TAG, "Worker running")
        runBlocking {
            if (!HealthServicesManager.getInstance(applicationContext).isRegistered()
                && HealthServicesManager.getInstance(applicationContext).hasHealthEventsCapability()
            ) {
                HealthServicesManager.getInstance(applicationContext).registerForHealthEventsData()
            }
        }
        return Result.success()
    }
}