package dd.wan.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

import androidx.recyclerview.widget.GridLayoutManager


class CustomCalendar(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    var calendar: Calendar = Calendar.getInstance(Locale.getDefault())
    var dates: ArrayList<Date> = ArrayList()
    var sdfDate = SimpleDateFormat("dd")

    private lateinit var calendarRecycler: RecyclerView
    private lateinit var currentDate: TextView
    private lateinit var month: TextView

    init {
        context?.let {
            var view: View = View.inflate(context, R.layout.custom_calendar, this)
            calendarRecycler = view.findViewById(R.id.recycler_calendar)
            currentDate = view.findViewById(R.id.tv_year)
            month = view.findViewById(R.id.tv_month)
            SetUpCalendar()
        }
    }

    fun SetUpCalendar() {
        var date = sdfDate.format(calendar.time)
        currentDate.text = date
        dates.clear()
        var monthCalendar: Calendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) + 5
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstOfMonth)
        while (dates.size < 42) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        var adapter = CalendarAdapter(dates, calendar)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(context, 7)
        calendarRecycler.layoutManager = layoutManager
        calendarRecycler.setHasFixedSize(true)
        var dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        var dividerItemDecoration1 = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        calendarRecycler.addItemDecoration(dividerItemDecoration)
        calendarRecycler.addItemDecoration(dividerItemDecoration1)
        calendarRecycler.adapter = adapter
    }
}