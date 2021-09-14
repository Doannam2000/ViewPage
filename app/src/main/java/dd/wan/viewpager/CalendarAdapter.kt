package dd.wan.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener


class CalendarAdapter(var list: ArrayList<Date>, var currentDate: Calendar) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.ViewHolder, position: Int) {
        var monthDate = list.get(position)
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = monthDate
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH) + 1
        var year = calendar.get(Calendar.YEAR)
        var currentMonth = currentDate.get(Calendar.MONTH) + 1
        var currentYear = currentDate.get(Calendar.YEAR)
        holder.setData(day.toString())
        if (month == currentMonth && year == currentYear) {
            holder.setColor()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.tv_day)
        var layout: ConstraintLayout = itemView.findViewById(R.id.constraint)
        fun setData(string: String) {
            textView.text = string
        }

        fun setColor() {
            textView.alpha = 1F
        }

        init {
            val rnd = Random()
            var i = 0
            var mHandler: Handler = Handler()
            layout.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    i++
                    if (i == 1) {
                        mHandler.postDelayed(Runnable {
                            if (i != 0) {
                                layout.setBackgroundColor(Color.BLUE)
                            }
                            i = 0
                        }, 250)
                    }
                    if (i == 2) {
                        i = 0
                        val color: Int =
                            Color.argb(
                                255,
                                rnd.nextInt(256),
                                rnd.nextInt(256),
                                rnd.nextInt(256)
                            )
                        layout.setBackgroundColor(color)
                    }
                    return false
                }
            })
        }
    }
}
