package com.aoreo.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aoreo.coco.dataModel.CurrentPrice
import com.aoreo.coco.dataModel.CurrentPriceResult
import com.aoreo.coco.datastore.MyDataStore
import com.aoreo.coco.db.entity.InterestCoinEntity
import com.aoreo.coco.repository.DBRepository
import com.aoreo.coco.repository.NetworkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val networkRepository = NetworkRepository()
    private val dbRepository = DBRepository()
    private lateinit var currentPriceResultList : ArrayList<CurrentPriceResult>

    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
    get() = _currentPriceResult

    private val _saved = MutableLiveData<String>()
    val saved: LiveData<String>
    get() = _saved
    fun getCurrentCoinList() = viewModelScope.launch {

        val result = networkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

        for (coin in result.data){
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))

                val gsonFromJson = gson.fromJson(gsonToJson,CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key,gsonFromJson)

                currentPriceResultList.add(currentPriceResult)
            }
           catch (e:java.lang.Exception){
               Timber.d(e.toString())
           }
        }
        _currentPriceResult.value=currentPriceResultList
    }

    fun setUpFirstFlag() = viewModelScope.launch { // 기본적으로 Main thread // https://gift123.tistory.com/60
        MyDataStore().setupFirstData()
    }

    // DB에 데이터 저장
    fun saveSelectedCoinList(selectedCoinList : ArrayList<String>) = viewModelScope.launch(Dispatchers.IO) {

        // 1. 전체 코인 데이터를 가져와서
        for(coin in currentPriceResultList){
            Timber.d(coin.toString())

            val selected = selectedCoinList.contains(coin.coinName)

            val interestCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )
            interestCoinEntity.let { // let - 수신 객체가 null이 아닐 때만 코드를 실행해야 하는 경우 사용, 또는 지역 변수의 범위를 제한하고자 할 때 사용
                dbRepository.insertInterestCoinData(it)
            }
        }

        // 2. 내가 선택한 코인인지 아닌지 구분해서

        // 3. 저장
        withContext(Dispatchers.Main){
            _saved.value="done"
        } // cannot invoke setvalue on a background thread 에러 해결을 위해 withContext로 스레드 변경

    }

}