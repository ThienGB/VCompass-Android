package com.example.vcompass.enum.tab

import com.example.vcompass.R

enum class ScheduleTab(val titleRes: Int, val selectedIconRes: Int, val unselectedIconRes: Int) {
    SUMMARY(R.string.lb_summary, R.drawable.ic_home_fill_24dp, R.drawable.ic_home_outline_24dp),
    DETAIL(R.string.lb_detail, R.drawable.ic_article_fill_24dp, R.drawable.ic_article_outline_24dp),
    MAP(R.string.lb_map, R.drawable.ic_map_fill_24dp, R.drawable.ic_map_outline_24dp),
}