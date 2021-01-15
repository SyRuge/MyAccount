package com.xcx.account.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xuchongxiang on 2020年12月25日.
 */
private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
private val timeFormat = SimpleDateFormat("HH:mm")

private const val TAG = "DateFormatUtils"


/**
 * @return like 2020-12-30
 */
fun formatDate(time: Long): String {
    return simpleDateFormat.format(Date(time))
}

fun getHourOfDay(time: Long): Int {
    val c =Calendar.getInstance()
    c.timeInMillis = time
    val h = c.get(Calendar.HOUR_OF_DAY)
    return h
}

fun getHourOfMinute(time: Long): Int {
    val c =Calendar.getInstance()
    c.timeInMillis = time
    val m = c.get(Calendar.MINUTE)
    return m
}

/**
 * return like 12:15
 */
fun getTime(time: Long): String {
    return timeFormat.format(Date(time))
}

fun todayDate(): String {
    val today = simpleDateFormat.format(Date())
    return today
}

fun curWeekRangeDate(): String {
    //2020-12-21 ~ 2020-12-27
    val rangWeek = "${getCurWeekFirstDay()} ~ ${getCurWeekLastDay()}"
    return rangWeek
}

fun curMonthRangeDate(): String {
    val c = Calendar.getInstance()
    c.set(Calendar.DAY_OF_MONTH, 1)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val startDate = simpleDateFormat.format(c.time)

    val end_day_of_month = c.getActualMaximum(Calendar.DAY_OF_MONTH)
    c.set(Calendar.DAY_OF_MONTH, end_day_of_month)
    val endDate = simpleDateFormat.format(c.time)

    var rangeDate = "$startDate ~ $endDate"
    return rangeDate
}

fun curRangeYearDate(): String {
    val c = Calendar.getInstance()
    c.set(Calendar.DAY_OF_YEAR, 1)
    val firstDay = simpleDateFormat.format(c.time)

    val end_day_of_month = c.getActualMaximum(Calendar.DAY_OF_YEAR)
    c.set(Calendar.DAY_OF_YEAR, end_day_of_month)
    val lastDay = simpleDateFormat.format(c.time)

    var rangDate = "$firstDay ~ $lastDay"
    return rangDate
}

fun getCurWeekFirstDay(): String {
    val c = Calendar.getInstance()
    var day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1
    if (day_of_week == 0) {
        day_of_week = 7
    }
    c.add(Calendar.DATE, -day_of_week + 1)

    val format = simpleDateFormat.format(c.time)
    println(format)
    return format
}

fun getCurWeekLastDay(): String {
    val c = Calendar.getInstance()

    var day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1
    if (day_of_week == 0) {
        day_of_week = 7
    }
    c.add(Calendar.DATE, -day_of_week + 7)

    val format = simpleDateFormat.format(c.time)
    return format
}