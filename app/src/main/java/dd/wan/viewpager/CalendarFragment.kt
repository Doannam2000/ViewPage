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

class CalendarFragment : Fragment() {
//    var calendar: Calendar = Calendar.getInstance(Locale.getDefault())
    var dates: ArrayList<Date> = ArrayList()
    var sdfDate = SimpleDateFormat("dd")
    lateinit var calendar:Calendar
    lateinit var adapter:CalendarAdapter
    fun newInstance(calendar: Calendar) = CalendarFragment().apply {
        arguments = bundleOf(
            "calendar" to calendar
        )
    }
    private lateinit var calendarRecycler: RecyclerView
    private lateinit var currentDate: TextView
    private lateinit var month: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarRecycler = view.findViewById(R.id.recycler_calendar)
        currentDate = view.findViewById(R.id.tv_currentdate)
        month = view.findViewById(R.id.tv_month)
        calendar = arguments?.getSerializable("calendar") as Calendar
        updateData()
        adapter = CalendarAdapter(dates, calendar)
        setUpCalendar()
        return view
    }

    fun setUpCalendar() {
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
    fun updateData()
    {
        dates.clear()
        var date = sdfDate.format(calendar.time)
        currentDate.text = date
        var monthCalendar: Calendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-2
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstOfMonth)
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
}