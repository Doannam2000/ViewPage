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
import java.text.SimpleDateFormat


class CalendarAdapter(var list: ArrayList<Date>, var currentDate: Calendar) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        return ViewHolder(view)
    }

    var da = Calendar.getInstance(Locale.getDefault())
    var itemSelected = 0

    override fun onBindViewHolder(holder: CalendarAdapter.ViewHolder, position: Int) {
        holder.setData()

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.tv_day)
        var layout: ConstraintLayout = itemView.findViewById(R.id.constraint)
        fun setData() {
            var monthDate = list.get(adapterPosition)
            var calendar: Calendar = Calendar.getInstance()
            calendar.time = monthDate
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            var month = calendar.get(Calendar.MONTH) + 1
            var year = calendar.get(Calendar.YEAR)
            var currentMonth = currentDate.get(Calendar.MONTH) + 1
            var currentYear = currentDate.get(Calendar.YEAR)
            if (month == currentMonth && year == currentYear) {
                setColor()
            } else {
                textView.alpha = 0.2F
            }
            if (day == da.get(Calendar.DAY_OF_MONTH) && currentDate.get(Calendar.MONTH) == da.get(Calendar.MONTH)) {
                layout.setBackgroundColor(Color.GRAY)
            } else {
                layout.setBackgroundColor(Color.parseColor("#f8f8f8"))
            }
            textView.text = day.toString()
            if (itemSelected != adapterPosition) {
                layout.setBackgroundColor(Color.parseColor("#f8f8f8"))
            }
            else
            {

            }
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
                    itemSelected = adapterPosition
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
