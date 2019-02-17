package com.endotakumi.kotlin001.Timer

import com.endotakumi.kotlin001.BasePresenter
import com.endotakumi.kotlin001.BaseView

interface TimerContract{
    interface View : BaseView<Presenter>{
        var isActive : Boolean

        fun setTextButtonReset(textId: Int)
        fun setTextTextviewTime(text: String)
        fun setTextTextviewLapTime(text: String)
    }

    interface Presenter : BasePresenter{
        var isDataMissing : Boolean

        fun timerStart()
        fun timerStop()
        fun timerReset()
    }
}