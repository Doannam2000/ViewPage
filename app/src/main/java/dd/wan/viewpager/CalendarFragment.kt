package dd.wan.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment() {
    //    var calendar: Calendar = Calendar.getInstance(Locale.getDefault())
    var dates: ArrayList<Date> = ArrayList()
    var sdfDate = SimpleDateFormat("dd",Locale.getDefault())
    lateinit var calendar: Calendar
    var start = -2
    lateinit var adapter: CalendarAdapter
    lateinit var calendarRecycler: RecyclerView


    fun newInstance(calendar: Calendar, start: Int) = CalendarFragment().apply {
        arguments = bundleOf(
            "calendar" to calendar,
            "start" to start
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calendar = arguments?.getSerializable("calendar") as Calendar
        start = arguments?.getInt("start")!!
        var view: View = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarRecycler = view.findViewById(R.id.recycler_calendar)
        // hiển thị ngày tháng
        adapter = CalendarAdapter(dates, calendar)
        updateData()
        setUpCalendar()
        return view
    }

    fun setUpCalendar() {
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(context, 7)
        calendarRecycler.layoutManager = layoutManager
        calendarRecycler.setHasFixedSize(true)
        calendarRecycler.setItemViewCacheSize(42)
        var dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        var dividerItemDecoration1 = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        calendarRecycler.addItemDecoration(dividerItemDecoration)
        calendarRecycler.addItemDecoration(dividerItemDecoration1)
        calendarRecycler.setItemAnimator(null)
        calendarRecycler.adapter = adapter
    }

    fun updateData() {
        dates.clear()
        var monthCalendar: Calendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) + start
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstOfMonth)
        if(sdfDate.format(monthCalendar.time).toString() < "26" )
        {
            if(start==-2)
                start = 5
            else if(start == 5 )
                start = -2

            if(start==-3)
                start = 4
            else if(start == 4)
                start = -3
            monthCalendar = calendar.clone() as Calendar
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
            firstOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) + start
            monthCalendar.add(Calendar.DAY_OF_MONTH, -firstOfMonth)
        }
        while (dates.size < 42) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    fun onNextMonth() {
        if (this::calendar.isInitialized) {
            calendar.add(Calendar.MONTH, 1)
            updateData()
            adapter.notifyDataSetChanged()
        }
    }

    fun onPreviousMonth() {
        if (this::calendar.isInitialized) {
            calendar.add(Calendar.MONTH, -1)
            updateData()
            adapter.notifyDataSetChanged()
        }
    }

    fun startWeekOn(start: Int) {
        this.start = start
        if (this::calendar.isInitialized) {
            updateData()
            adapter.notifyDataSetChanged()
        }
    }

    fun getCurrentCalendar():Calendar{
        return calendar
    }
}