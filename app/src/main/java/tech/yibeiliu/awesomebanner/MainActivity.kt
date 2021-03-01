package tech.yibeiliu.awesomebanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.MarginPageTransformer
import tech.yibeiliu.awesome_banner.AwesomeBanner
import tech.yibeiliu.awesome_banner.adapter.BannerBitmapAdapter
import tech.yibeiliu.awesome_banner.transformer.AlphaTransformer
import tech.yibeiliu.awesome_banner.transformer.ScaleTransformer

class MainActivity : AppCompatActivity() {
    private lateinit var banner: AwesomeBanner<Bitmap, BannerBitmapAdapter>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        banner = findViewById(R.id.banner)
        banner.addTransformer(MarginPageTransformer(30))
        banner.addTransformer(AlphaTransformer(0.5f))
        banner.addTransformer(ScaleTransformer(0.8f))
        banner.isInfiniteLoop = true
        banner.setRecyclerViewPadding(100, 100)
        banner.setAdapter(
            BannerBitmapAdapter(
                listOf(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.image1
                    ),
                    BitmapFactory.decodeResource(resources, R.drawable.image2),
                    BitmapFactory.decodeResource(resources, R.drawable.image3),
                    BitmapFactory.decodeResource(resources, R.drawable.image4),
                    BitmapFactory.decodeResource(resources, R.drawable.image5)
                )
            )
        )
        banner.startAutoLoop()
    }
}
