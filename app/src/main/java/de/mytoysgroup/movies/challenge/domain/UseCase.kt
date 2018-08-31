package de.mytoysgroup.movies.challenge.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import androidx.work.*
import de.mytoysgroup.movies.challenge.domain.model.Either
import de.mytoysgroup.movies.challenge.observeOnce
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

typealias OnUseCaseFinish<O> = (Either<Exception, O>?) -> Unit

abstract class UseCase<I, O> : Worker() {

    abstract val inputMapper: DataMapper<I>
    abstract val outputMapper: DataMapper<O>

    fun execute(params: I, onUseCaseFinish: OnUseCaseFinish<O>? = null) {

        val liveData = MutableLiveData<Either<Exception, O>>()
        onUseCaseFinish?.let { liveData.observeOnce(it) }

        val workRequest = OneTimeWorkRequest.Builder(this::class.java)
                .setInputData(params.toInputData())
                .build()

        val liveWorkStatus = runWorker(workRequest)

        liveWorkStatus.whenWorkFinish {
            Log.d(this::class.java.simpleName, "Notify work result: ${it.state.name}")
            liveData.value = when (it.state) {
                State.SUCCEEDED -> Either.Success(it.toOutput())
                else -> Either.Failure(Exception())
            }
        }
    }

    final override fun doWork() = try {

        val output = run(inputData.toInput())
        outputData = output.toOutputData()

        Result.SUCCESS
    } catch (e: Exception) {
        Log.w(this::class.java.simpleName, e)

        Result.FAILURE
    }

    internal abstract fun run(params: I): O

    private fun runWorker(workRequest: OneTimeWorkRequest): LiveData<WorkStatus> {
        val workManager = WorkManager.getInstance()
        workManager.enqueue(workRequest)
        return workManager.getStatusById(workRequest.id)
    }

    private inline fun LiveData<WorkStatus>.whenWorkFinish(crossinline action: (workStatus: WorkStatus) -> Unit) {
        observeForever(object : Observer<WorkStatus> {
            override fun onChanged(workStatus: WorkStatus?) {
                Log.d(this@UseCase::class.java.simpleName, "WorkStatus changed: ${workStatus?.state?.name}")
                workStatus ?: return
                if (workStatus.state.isFinished) {
                    action(workStatus)
                    removeObserver(this)
                }
            }
        })
    }

    private fun Data.toInput() = inputMapper.fromMap(JSONObject(getString("input")).toMap())

    private fun I.toInputData() = Data.Builder()
            .putString("input", JSONObject(inputMapper.toMap(this)).toString())
            .build()

    private fun WorkStatus.toOutput() = outputMapper.fromMap(JSONObject(outputData.getString("output")).toMap())

    private fun O.toOutputData() = Data.Builder()
            .putString("output", JSONObject(outputMapper.toMap(this)).toString())
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