package com.example.searchgithubusers.view.SearchUsers

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.abs

class ChangeListModeView: AppCompatImageView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    private var startRawX = 0F
    private var startRawY = 0F
    private var xCoOrdinate = 0F
    private var yCoOrdinate = 0F

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                // 得儲存是手指在畫面上的位置(RawX, RawY)，而非View中的位置(X, Y)
                startRawX = event.rawX
                startRawY = event.rawY

                xCoOrdinate = this.x - event.rawX
                yCoOrdinate = this.y - event.rawY
            }

            MotionEvent.ACTION_MOVE -> {
                this.animate()
                    .x(event.rawX + xCoOrdinate)
                    .y(event.rawY + yCoOrdinate)
                    .setDuration(0)
                    .start()
            }

            MotionEvent.ACTION_UP -> {
                if ((abs(event.rawX - startRawX) < 3 || abs(event.rawY - startRawY) < 3)) {
                    this.performClick()
                }
            }
        }

        return true
    }
}