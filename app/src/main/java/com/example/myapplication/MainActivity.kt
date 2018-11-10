package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = SimplePagerAdapter(supportFragmentManager)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter = pagerAdapter

        tab_layout.setupWithViewPager(viewPager)

        for (i in 0 until tab_layout.tabCount) {
            val customTextTitle = pagerAdapter.getTabTitleView(this, i)
            tab_layout.getTabAt(i)?.customView = customTextTitle
        }
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            val textView = rootView.findViewById<View>(R.id.section_label) as TextView
            textView.text = getString(R.string.section_format, arguments!!.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            private const val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    inner class SimplePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Title 1"
                1 -> "Title 2"
                2 -> "Very Long Title 3"
                3 -> "Very Very Very Long Long Long Title 4"
                else -> throw IllegalStateException("Should only 4 pages exist")
            }
        }

        fun getTabTitleView(context: Context, position: Int): View {
            val customTextTitle = TextView(context)
            customTextTitle.gravity = Gravity.CENTER

            customTextTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
            customTextTitle.setSingleLine(true)

            customTextTitle.text = getPageTitle(position)

            return customTextTitle
        }
    }
}
