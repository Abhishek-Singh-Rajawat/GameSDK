package com.itg.itggaming.gameLanding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg.itggaming.gameLanding.api.adapter.GameLandingAdapter
import com.itg.itggaming.gameLanding.api.apiService.GameService
import com.itg.itggaming.gameLanding.api.apiService.RetrofitHelper
import com.itg.itggaming.gameLanding.api.model.Data
import com.itg.itggaming.gameLanding.api.model.GameList
import com.itg.itggaming.gameLanding.api.model.Games
import com.itg.itggaming.gameLanding.api.repository.GameLandingRepository
import com.itg.itggaming.gameLanding.api.viewmodel.MainViewModel
import com.itg.itggaming.gameLanding.api.viewmodel.MainViewModelFactory
import com.itg.itggaming.R
import com.itg.itggaming.util.GameConstants
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.Locale

class GameLandingActivity : AppCompatActivity() {

    private lateinit var data:Data
    private lateinit var dataList:ArrayList<GameList>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var url: String
    private lateinit var repository: GameLandingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        setContentView(R.layout.activity_game_landing)
        initializeModels()
        getGameList()
        setSystemProperties()
        setBackBtn()
    }

    private fun setLanguage() {
        val language=intent.getStringExtra(GameConstants.LANGUAGE)
        if (language != null) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                val config = this.resources.configuration
                config.setLayoutDirection(locale)
                config.setLocale(locale)
                resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    private fun initializeModels() {
        url=intent.getStringExtra(GameConstants.BASE_URL)!!
        val gameService=RetrofitHelper.getInstance().create(GameService::class.java)
        repository=GameLandingRepository(gameService)
        callApi(repository,url)
    }

    private fun callApi(repository: GameLandingRepository, url: String?) {
        if(url!=null)
            mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository,url)).get(MainViewModel::class.java)
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

//        val callback=if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            (intent.getSerializableExtra(GameConstants.GamingLogCallbacks, GamingLogCallbacks::class.java))!!
//        } else {
//            intent.getSerializableExtra(GameConstants.GamingLogCallbacks) as GamingLogCallbacks
//        }
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
        window.statusBarColor=ContextCompat.getColor(this,R.color.itgg_toolbar)
        window.navigationBarColor=ContextCompat.getColor(this,R.color.itgg_black)
    }

    private fun addAdsToList(): ArrayList<GameList> {
        val adPosition= data.ads?.adLandingPosition?.plus(1)
        var i= data.ads?.adLandingPosition
        val arr=dataList
        val adsData=GameList(type = "ads","","", arrayListOf())
        if (i != null && i!=0) {
                while(i<arr.size){
                    arr.add(i,adsData)
                    if (adPosition != null) {
                        i+=adPosition
                    }
                }
        }
        return arr
    }

    private fun getGameList(){
        mainViewModel.games.observe(this, Observer {
            if(it.data!=null){
                data= it.data!!
                dataList=data.gameList
                val shimmer=findViewById<ShimmerFrameLayout>(R.id.shimmer)
                shimmer.stopShimmer()
                shimmer.visibility= View.GONE
                setFilterData()
                setDataWithAds()
                setRecyclerView()
            }
        })
    }

    private fun setFilterData() {
        val gameData=filterDataList()
        dataList.clear()
        dataList.addAll(gameData)
    }

    private fun filterDataList():ArrayList<GameList> {
        val arr= arrayListOf<GameList>()
        arr.addAll(dataList)
        var i=0;
        while (i<arr.size){
            var filterListDevices=arr[i].games.filter{ !(it.device.equals("all") || it.device.equals("android"))} as ArrayList<Games>
            val filterListEnabled=arr[i].games.filter {it.enabled!!.equals("false") }
            arr[i].games.removeAll(filterListDevices)
            arr[i].games.removeAll(filterListEnabled)
            if(arr[i].games.size==0)
                arr.removeAt(i)
            i++

        }

        return arr
    }

}