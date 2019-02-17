package com.endotakumi.kotlin001.Timer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.endotakumi.kotlin001.R
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.android.synthetic.main.fragment_timer.view.*

class TimerFragment : Fragment(), TimerContract.View {
    override lateinit var presenter: TimerContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

//    private lateinit var buttonStart : Button
//    private lateinit var buttonStop : Button
//    private lateinit var buttonReset : Button
//
//    private lateinit var textViewTime : TextView
//    private lateinit var textViewLapTime : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_timer, container, false)

        with(view) {
            // Operation when START button is pushed
            buttonStart.setOnClickListener {
                presenter.timerStart()
            }

            // Operation when STOP button is pushed
            buttonStop.setOnClickListener {
                presenter.timerStop()
            }

            // Operation when RESET button is pushed
            buttonReset.setOnClickListener {
                presenter.timerReset()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setTextButtonReset(textId: Int){
        buttonReset.text = getString(textId)
    }

    override fun setTextTextviewTime(text: String){
        textViewTime.text = text
    }

    override fun setTextTextviewLapTime(text: String){
        textViewLapTime.text = text
    }

    companion object {
        fun newInstance() : TimerFragment{
            var timerFragment = TimerFragment()
            var bundle = Bundle()
            timerFragment.arguments = bundle
            return timerFragment
        }
    }
}