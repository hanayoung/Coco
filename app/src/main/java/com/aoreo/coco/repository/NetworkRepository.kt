package com.aoreo.coco.repository

import com.aoreo.coco.network.Api
import com.aoreo.coco.network.RetrofitInstance

class NetworkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
}