package tech.yibeiliu.awesome_banner.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ScaleTransformer(var minScale: Float = 1f) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val scale: Float = if (position < 0) {
            (1 - minScale) * position + 1
        } else {
            (minScale - 1) * position + 1
        }
        page.scaleY = scale
    }
}
