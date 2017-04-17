package com.eggpogg.testviewpagerintocoordinaterlayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById(R.id.pager) as ViewPager
        val fragments = arrayListOf<Fragment>().apply {
            add(SampleFragment().apply { arguments = Bundle().apply { putString("position", "1") } })
            add(SampleFragment().apply { arguments = Bundle().apply { putString("position", "2") } })
        }
        pager.adapter = MyViewPagerAdapter(supportFragmentManager, fragments)
    }

    private class MyViewPagerAdapter(fm: FragmentManager, val fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
        override fun getCount() = 2
        override fun getItem(position: Int) = fragments[position]
    }

    class SampleFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val type = arguments?.getString("position") ?: "null"

            val view = inflater?.inflate(R.layout.fragment_sample, container, false)?.apply {
                (findViewById(R.id.drawer) as DrawerLayout).setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                (findViewById(R.id.list) as RecyclerView).apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = MyListAdapter(type)
                }
                findViewById(R.id.btnText).setOnClickListener {
                    (findViewById(R.id.drawer) as DrawerLayout).openDrawer(Gravity.START)
                }
            }

            return view
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        }

        private class MyListAdapter(val type: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
                holder ?: return
                (holder.itemView.findViewById(R.id.text) as TextView).text = "$type: $position"
            }

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
                parent ?: return null
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_sample_item, parent, false)
                return MyViewHolder(view)
            }

            override fun getItemCount() = 20

            private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
        }
    }
}
