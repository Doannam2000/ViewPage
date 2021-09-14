package dd.wan.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val PAGE_MIDDLE = 1
    private var mSelectedPageIndex = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var calendar: Calendar = Calendar.getInstance(Locale.getDefault())
        var prevMonth = calendar.clone() as Calendar
        var nextMonth = calendar.clone() as Calendar
        prevMonth.set(Calendar.MONTH, -1)
        nextMonth.set(Calendar.MONTH, 1)
        var list = ArrayList<CalendarFragment>()
        list.add(CalendarFragment().newInstance(prevMonth))
        list.add(CalendarFragment().newInstance(calendar))
        list.add(CalendarFragment().newInstance(nextMonth))
        var adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, list)
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                mSelectedPageIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mSelectedPageIndex < PAGE_MIDDLE) {
                        list[0].onPreviousMonth()
                        list[1].onPreviousMonth()
                        list[2].onPreviousMonth()
                    } else if (mSelectedPageIndex > PAGE_MIDDLE) {
                        list[0].onNextMonth()
                        list[1].onNextMonth()
                        list[2].onNextMonth()
                    }
                    viewPager.setCurrentItem(1, false);
                }
            }
        })
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, false);
    }
}

