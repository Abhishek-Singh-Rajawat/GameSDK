package com.example.itggaming.GameLanding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.adapter.GameLandingAdapter
import com.example.itggaming.GameLanding.api.apiService.GameService
import com.example.itggaming.GameLanding.api.apiService.RetrofitHelper
import com.example.itggaming.GameLanding.api.model.Data
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.GameLanding.api.repository.GameLandingRepository
import com.example.itggaming.GameLanding.api.viewmodel.MainViewModel
import com.example.itggaming.GameLanding.api.viewmodel.MainViewModelFactory
import com.example.itggaming.R
import com.example.itggaming.util.GameConstants
import com.facebook.shimmer.ShimmerFrameLayout

class GameLandingActivity : AppCompatActivity() {

    private lateinit var data:Data
    private lateinit var dataList:ArrayList<GameList>
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_landing)

        initializeModels()
        getGameList()
        setSystemProperties()
        setBackBtn()
    }

    private fun initializeModels() {
        val gameService=RetrofitHelper.getInstance().create(GameService::class.java)
        val repository=GameLandingRepository(gameService)
        mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
    }

    private fun setBackBtn() {
        val backBtn=findViewById<ImageView>(R.id.iv_back_btn)
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView() {
        val adsData= data.ads
        val adBundle=Bundle()
        if (adsData != null) {
            adBundle.putString(GameConstants.AD_UNIT_ID,adsData.adUnitId)
            adsData.adCategoryPosition?.let { adBundle.putInt(GameConstants.AD_CATEGORY_POSITION, it) }
        }
        val adapter=GameLandingAdapter(adBundle,dataList)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_gameLanding)
        recyclerView.visibility=View.VISIBLE
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter=adapter
    }

    private fun setDataWithAds() {
        val gameDataListwithAds= addAdsToList()
        dataList= arrayListOf()
        dataList.clear()
        dataList.addAll(gameDataListwithAds)
    }

    private fun setSystemProperties() {
        window.statusBarColor=getColor(R.color.toolbar)
        val windowInsetsController=WindowCompat.getInsetsController(window,window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        windowInsetsController.systemBarsBehavior= WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun addAdsToList(): ArrayList<GameList> {
        val adPosition= data.ads?.adLandingPosition?.plus(1)
        var i= data.ads?.adLandingPosition
        val arr=dataList
        val adsData=GameList(type = "ads","","", arrayListOf())
        if (i != null) {
            if(i<arr.size){
                while(i<arr.size){
                    arr.add(i,adsData)
                    if (adPosition != null) {
                        i+=adPosition
                    }
                }
            }
            else{
                arr.add(adsData)
            }
        }
        return arr
    }

    private fun getGameList(){
        mainViewModel.games.observe(this, Observer {
            if(it.data!=null){
                data= it.data!!
                dataList= data.gameList
                val shimmer=findViewById<ShimmerFrameLayout>(R.id.shimmer)
                shimmer.stopShimmer()
                shimmer.visibility= View.GONE
                setDataWithAds()
                setRecyclerView()
            }
        })
    }
}