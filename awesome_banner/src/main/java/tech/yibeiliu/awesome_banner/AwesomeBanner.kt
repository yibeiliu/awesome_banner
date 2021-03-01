package tech.yibeiliu.awesome_banner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import tech.yibeiliu.awesome_banner.adapter.BannerAdapter
import tech.yibeiliu.awesome_banner.adapter.getRealPosition
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class AwesomeBanner<DATA, Adapter : BannerAdapter<DATA, out RecyclerView.ViewHolder>> :
    FrameLayout {
    lateinit var viewPager2: ViewPager2
    var isInfiniteLoop = false
    lateinit var bannerAdapter: Adapter
    private lateinit var compositePageTransformer: CompositePageTransformer
    private lateinit var onPageChangeCallback: OnPageChangeCallback
    private val autoLoopRunnable: Runnable = AutoLoopRunnable()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
        initTypedArray(context, attrs)
    }

    private fun initTypedArray(context: Context, attrs: AttributeSet?) {
        attrs?.let {
//            val a = context.obtainStyledAttributes(it, R.styleable.Banner)
        }
    }

    private fun init(context: Context) {
        compositePageTransformer = CompositePageTransformer()
        viewPager2 = ViewPager2(context)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        viewPager2.layoutParams = layoutParams
        viewPager2.offscreenPageLimit = 1
        viewPager2.setPageTransformer(compositePageTransformer)
        addView(viewPager2)
        onPageChangeCallback = PageChangeCallback()
        viewPager2.registerOnPageChangeCallback(onPageChangeCallback)
    }


    private fun getItemCount() = bannerAdapter.itemCount

    inner class PageChangeCallback : OnPageChangeCallback() {
        private var currPosition: Int = 0
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            Log.d("lpy", "onPageScrollStateChanged state = $state position = $currPosition")
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                if (currPosition == getItemCount() - 1) {
                    viewPager2.setCurrentItem(1, false)
                } else if (currPosition == 0) {
                    viewPager2.setCurrentItem(bannerAdapter.realCount - 1, false)
                }
            }
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            Log.d("lpy", "onPageScrolled position = $position")
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currPosition = position
            Log.d("lpy", "onPageSelected position = $position")

        }
    }

    inner class AutoLoopRunnable : Runnable {
        override fun run() {
            if (bannerAdapter.itemCount == 0) return
            Log.d("lpy1", "viewPager2.currentItem = ${viewPager2.currentItem}")
            Log.d("lpy1", "bannerAdapter.itemCount = ${bannerAdapter.itemCount}")
            val nextPosition = (viewPager2.currentItem + 1) % bannerAdapter.itemCount
            viewPager2.currentItem = nextPosition
        }

    }

    //============================================= public =====================================================

    fun setAdapter(adapter: Adapter) {
        bannerAdapter = adapter
        bannerAdapter.setIncreaseNum(if (isInfiniteLoop) 2 else 0)
        viewPager2.adapter = adapter
        viewPager2.setCurrentItem(0, false)
    }

    fun addTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.addTransformer(transformer)
    }

    fun setRecyclerViewPadding(
        leftItemPadding: Int,
        rightItemPadding: Int
    ) {
        val recyclerView = viewPager2.getChildAt(0) as RecyclerView
        if (viewPager2.orientation == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(0, leftItemPadding, 0, rightItemPadding)
        } else {
            recyclerView.setPadding(leftItemPadding, 0, rightItemPadding, 0)
        }
        recyclerView.clipToPadding = false
    }

    fun startAutoLoop() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
            autoLoopRunnable, 0L,
            1L, TimeUnit.SECONDS
        )
    }
}



