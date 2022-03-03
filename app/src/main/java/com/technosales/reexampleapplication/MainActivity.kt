package com.technosales.reexampleapplication


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*


import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNumber = 1
    var totalresult = -1
    val TAG = "MainActivity"
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //admob code
        MobileAds.initialize(this)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"


        mInterstitialAd.loadAd(AdRequest.Builder().build())







        adapter = NewsAdapter(this@MainActivity, articles)
        var newsList = findViewById<RecyclerView>(R.id.newsList)
        var container = findViewById<LinearLayout>(R.id.container)
        newsList.adapter = adapter

        //newsList.layoutManager = LinearLayoutManager(this@MainActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG, "first visible Item - ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG, "Total Count - ${layoutManager.itemCount}")

                //paging concept
                if (totalresult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    pageNumber++
                    getMyData();
                }

                if (position % 5 == 0) {
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    }
                }
            }

        })

        newsList.layoutManager = layoutManager

        getMyData();


    }


    private fun getMyData() {
        Log.d(TAG, "Request sent for $pageNumber")
        val news = NewsService.newsInstance.getHeadLines("in", pageNumber)

        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    totalresult = news.totalResults
                    Log.d("exapmle", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()


                }

            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                Log.d("exapmle", "Error news", t)
            }
        }
        )

    }
}


