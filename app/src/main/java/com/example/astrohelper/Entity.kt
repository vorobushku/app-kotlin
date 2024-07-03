package com.example.astrohelper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "magic_ball_table")
data class MagicBall (
    @PrimaryKey val id: Int,
    val text: String
)

@Entity(tableName = "Numerology")
data class NumerologyEntity(
    @PrimaryKey val id: Int,
    val lasso: Int,
    val text: String
)

@Entity(tableName = "tarot_table")
data class TarotItem(
    @PrimaryKey val id: Int,
    val text: String,
    val path: String
)