package de.mytoysgroup.movies.challenge.domain

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import androidx.work.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

abstract class UseCase<I, O> : Worker() {

    abstract val inputConverter: DataConverter<I>
    abstract val outputConverter: DataConverter<O>

    fun execute(params: I, success: MutableLiveData<O>, error: MutableLiveData<Exception>) {

        val workRequest = OneTimeWorkRequest.Builder(this::class.java)
                .setInputData(params.toInputData())
                .build()

        val workManager = WorkManager.getInstance()
        workManager.enqueue(workRequest)
        val liveWorkStatus = workManager.getStatusById(workRequest.id)

        val observer = object : Observer<WorkStatus> {
            override fun onChanged(workStatus: WorkStatus?) {
                workStatus ?: return
                if (workStatus.state.isFinished) {
                    liveWorkStatus.removeObserver(this)

                    if (State.SUCCEEDED == workStatus.state) {
                        success.value = workStatus.toOutput()
                    } else {
                        error.value = null
                    }
                }
            }
        }

        liveWorkStatus.observeForever(observer)
    }

    final override fun doWork() = try {

        val output = run(inputData.toInput())
        outputData = output.toOutputData()

        Result.SUCCESS
    } catch (e: Exception) {
        Log.w("TAG", e)

        Result.FAILURE
    }

    internal abstract fun run(params: I): O

    private fun Data.toInput() = inputConverter.fromMap(JSONObject(getString("input")).toMap())

    private fun I.toInputData() = Data.Builder()
            .putString("input", JSONObject(inputConverter.toMap(this)).toString())
            .build()

    private fun WorkStatus.toOutput() = outputConverter.fromMap(JSONObject(outputData.getString("output")).toMap())

    private fun O.toOutputData() = Data.Builder()
            .putString("output", JSONObject(outputConverter.toMap(this)).toString())
            .build()

    @Throws(JSONException::class)
    private fun JSONObject.toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        val keysItr = this.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value = this.get(key)

            if (value is JSONArray) {
                value = value.toList()
            } else if (value is JSONObject) {
                value = value.toMap()
            }
            map[key] = value
        }
        return map
    }

    @Throws(JSONException::class)
    private fun JSONArray.toList(): List<Any?> {
        val list = mutableListOf<Any?>()
        for (i in 0 until this.length()) {
            var value = this.get(i)
            if (value is JSONArray) {
                value = value.toList()
            } else if (value is JSONObject) {
                value = value.toMap()
            }
            list.add(value)
        }
        return list
    }
}