package com.aoreo.coco.db.dao

import androidx.room.*
import com.aoreo.coco.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestCoinDAO {

    // getAllData
    // Flow는 데이터의 변경 사항을 감지하기 좋다. 변경사항을 감지하지 못하면 View를 업데이트하는 코드를 따로 작성해주어야 함
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData() : Flow<List<InterestCoinEntity>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    // Update
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    // getSelectedCoinList -> 내가 관심 있어한 코인 데이터를 가져오는 것
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected : Boolean = true) : List<InterestCoinEntity>

}