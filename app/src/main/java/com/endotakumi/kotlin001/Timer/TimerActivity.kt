package com.endotakumi.kotlin001.Timer

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.endotakumi.kotlin001.Data.TimerRepository
import com.endotakumi.kotlin001.R
import com.endotakumi.kotlin001.Util.SharedPreferencesManager
import com.endotakumi.kotlin001.Util.replaceFragmentInActivity


class TimerActivity : AppCompatActivity() {

    private lateinit var presenter: TimerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerFragment = TimerFragment.newInstance().also { replaceFragmentInActivity(it, R.id.contentFrame) }

        val repository = TimerRepository(
            SharedPreferencesManager(
                this,
                TimerRepository.SP_DATA_NAME,
                Context.MODE_PRIVATE
            )
        )


        println("SLDFR: " +  repository.sharedPreferencesManager.getValue(SHOULD_LOAD_DATA_FROM_REPO_KEY, false))
        val shouldLoadDataFromRepo = repository.sharedPreferencesManager.getValue(SHOULD_LOAD_DATA_FROM_REPO_KEY, false)

        presenter = TimerPresenter(repository, timerFragment, shouldLoadDataFromRepo)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        // Save the state so that next time we know if we need to refresh data.
//        println("ssc:" + presenter.isDataMissing)
//        super.onSaveInstanceState(outState.apply {
//            putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, presenter.isDataMissing)
//        })
//    }

    override fun onPause() {
        presenter.pause()
        super.onPause()
    }

    companion object {
        const val SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY"
    }

}
