package com.digirr.fitdiet.data

data class User (val uid: String? = null,
                 val nick: String? = null,
                 val email: String? = null,
                 val maxKcal: Int? = null,
                 val maxProtein: Int? = null,
                 val maxCarbohydrates: Int? = null,
                 val maxFat: Int? = null,
                 val eatenKcal: Int? = 0,
                 val eatenProtein: Int? = 0,
                 val eatenCarbohydrates: Int? = 0,
                 val eatenFat: Int? = 0,
                 val eatenProducts: List<String>? = null)