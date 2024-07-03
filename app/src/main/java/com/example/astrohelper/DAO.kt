package com.example.astrohelper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MagicBallDao {
    @Query("SELECT * FROM magic_ball_table WHERE id = :id")
    suspend fun getMagicBallById(id: Int): MagicBall

    @Insert
    suspend fun insertAll(vararg magicBalls: MagicBall)

    @Query("SELECT COUNT(*) FROM magic_ball_table")
    suspend fun getMagicBallCount(): Int

    @Query("DELETE FROM magic_ball_table")
    suspend fun deleteAll()
}

@Dao
interface NumerologyDao {
    @Insert
    suspend fun insertNumerology(vararg numerology: NumerologyEntity)

    @Query("SELECT * FROM Numerology WHERE id = :id")
    suspend fun getNumerologyById(id: Int): NumerologyEntity

    @Query("SELECT COUNT(*) FROM Numerology")
    suspend fun getNumerologyCount(): Int

    @Query("DELETE FROM Numerology")
    suspend fun deleteAll()
}

@Dao
interface TarotDao {
    @Query("SELECT * FROM tarot_table WHERE id = :id")
    suspend fun getItemById(id: Int): TarotItem

    @Query("SELECT COUNT(*) FROM tarot_table")
    suspend fun getMagicBallCountTarot(): Int

    @Insert
    suspend fun insertAll(vararg tarotItem: TarotItem)

    @Query("DELETE FROM tarot_table")
    suspend fun deleteAll()
}