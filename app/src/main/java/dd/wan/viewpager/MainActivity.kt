package dd.wan.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var mSelectedPageIndex = 1
    var list = ArrayList<CalendarFragment>()
    var listday = arrayListOf<String>("Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun")
    var sdfMonth = SimpleDateFormat("MMMM", Locale.ENGLISH)
    var sdfYear = SimpleDateFormat("yyyy", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var calendar: Calendar = Calendar.getInstance(Locale.getDefault())
        var prevMonth = calendar.clone() as Calendar
        var nextMonth = calendar.clone() as Calendar
        prevMonth.add(Calendar.MONTH, -1)
        nextMonth.add(Calendar.MONTH, 1)
        var month = sdfMonth.format(calendar.time)
        tv_month.text = month
        var year = sdfYear.format(calendar.time)
        tv_year.text = year

        // tạo danh sách fragment và setup viewpager
        list.add(CalendarFragment().newInstance(prevMonth, 5)) // mặc định bắt đầu từ thứ 2
        list.add(CalendarFragment().newInstance(calendar, 5))
        list.add(CalendarFragment().newInstance(nextMonth, 5))

        var adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, list)

        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                mSelectedPageIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mSelectedPageIndex < 1) {
                        for (item in list)
                            item.onPreviousMonth()
                    } else if (mSelectedPageIndex > 1) {
                        for (item in list)
                            item.onNextMonth()
                    }
                    viewPager.setCurrentItem(1, false)
                    var cal = list[1].getCurrentCalendar()
                    tv_month.text = sdfMonth.format(cal.time)
                    tv_year.text = sdfYear.format(cal.time)
                }
            }
        })
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 100
        viewPager.setCurrentItem(1, false)
        var adapterDay = DayAdapter(listday)
        setting.setOnClickListener {
            var popupMenu = PopupMenu(applicationContext, it)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.mon -> {
                        changeStart(5) //-2
                        changeDay("Mon")
                    }
                    R.id.tue -> {
                        changeStart(4) //-3
                        changeDay("Tue")
                    }
                    R.id.wed -> {
                        changeStart(3) //-4
                        changeDay("Wed")
                    }
                    R.id.thur -> {
                        changeStart(2) //-5
                        changeDay("Thur")
                    }
                    R.id.fri -> {
                        changeStart(1)
                        changeDay("Fri")
                    }
                    R.id.sat -> {
                        changeStart(0)
                        changeDay("Sat")
                    }
                    R.id.sun -> {
                        changeStart(-1)
                        changeDay("Sun")
                    }
                }
                adapterDay.notifyDataSetChanged()
                false
            }
        }

        // hiển thị ngày trong tuần

        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 7)
        dayOfWeek.layoutManager = layoutManager
        dayOfWeek.setHasFixedSize(true)
        dayOfWeek.setItemViewCacheSize(7)
        dayOfWeek.adapter = adapterDay
    }

    fun changeStart(start: Int) {
        for (item in list) {
            item.startWeekOn(start)
        }
    }

    fun changeDay(string: String) {
        var listPhu = ArrayList<String>()
        while (!listday.get(0).equals(string)) {
            listPhu.add(listday.get(0))
            listday.removeAt(0)
        }
        listday.addAll(listPhu)
    }

    class DayAdapter(var list: ArrayList<String>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.ViewHolder {
            var view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.custom_days, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: DayAdapter.ViewHolder, position: Int) {
            holder.setData(list.get(position))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView = itemView.findViewById(R.id.tv_dayOfW)
            fun setData(string: String) {
                textView.text = string
            }
        }
    }
}

