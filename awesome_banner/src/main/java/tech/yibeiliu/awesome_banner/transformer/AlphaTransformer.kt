package tech.yibeiliu.awesome_banner.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class AlphaTransformer(var minAlpha: Float = 1f) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val alpha: Float = if (position < 0) {
            (1 - minAlpha) * position + 1
        } else {
            (minAlpha - 1) * position + 1
        }
        page.alpha = alpha


    }
}