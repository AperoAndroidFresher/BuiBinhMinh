package com.example.buibinhminh.data

import androidx.annotation.DrawableRes

data class MenuOption(
    val title: String,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)