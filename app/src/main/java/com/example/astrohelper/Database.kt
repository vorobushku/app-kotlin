package com.example.astrohelper

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MagicBall::class, TarotItem::class, NumerologyEntity::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun MagicBallDao(): MagicBallDao
    abstract fun tarotDao(): TarotDao
    abstract fun numerologyDao(): NumerologyDao
}