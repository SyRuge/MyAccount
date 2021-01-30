package com.xcx.account.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xuchongxiang on 2020年12月25日.
 */
private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
private val timeFormat = SimpleDateFormat("HH:mm")
private val dateAndTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

private const val TAG = "DateFormatUtils"


/**
 * @return like 2020-12-30
 */
fun formatDate(time: Long): String {
    return dateFormat.format(Date(time))
}

fun getHourOfDay(time: Long): Int {
    val c = Calendar.getInstance()
    c.timeInMillis = time
    return c.get(Calendar.HOUR_OF_DAY)
}

fun getHourOfMinute(time: Long): Int {
    val c = Calendar.getInstance()
    c.timeInMillis = time
    return c.get(Calendar.MINUTE)
}

/**
 * return like 12:15
 */
fun getTime(time: Long): String {
    return timeFormat.format(Date(time))
}

/**
 * return like 2021-01-22 10:28:32
 */
fun getDateAndTime(time: Long): String {
    return dateAndTimeFormat.format(Date(time))
}

/**
 * return like 2021-01-22
 */
fun todayDate(): String {
    return dateFormat.format(Date())
}

/**
 * todo will remove
 * return 2020-12-21 ~ 2020-12-27
 */
fun curWeekRangeDate(): String {
    //2020-12-21 ~ 2020-12-27
    return "${getCurWeekFirstDay()} ~ ${getCurWeekLastDay()}"
}

fun curMonthRangeDate(): String {
    val c = Calendar.getInstance()
    c.set(Calendar.DAY_OF_MONTH, 1)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val startDate = simpleDateFormat.format(c.time)

    val endDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
    c.set(Calendar.DAY_OF_MONTH, endDayOfMonth)
    val endDate = simpleDateFormat.format(c.time)

    return "$startDate ~ $endDate"
}

fun curRangeYearDate(): String {
    val c = Calendar.getInstance()
    c.set(Calendar.DAY_OF_YEAR, 1)
    val firstDay = dateFormat.format(c.time)

    val endDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_YEAR)
    c.set(Calendar.DAY_OF_YEAR, endDayOfMonth)
    val lastDay = dateFormat.format(c.time)

    return "$firstDay ~ $lastDay"
}

fun getCurWeekFirstDay(): String {
    val c = Calendar.getInstance()
    var dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1
    if (dayOfWeek == 0) {
        dayOfWeek = 7
    }
    c.add(Calendar.DATE, -dayOfWeek + 1)

    val format = dateFormat.format(c.time)
    logd(TAG, format)
    return format
}

fun getCurWeekLastDay(): String {
    val c = Calendar.getInstance()

    var dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1
    if (dayOfWeek == 0) {
        dayOfWeek = 7
    }
    c.add(Calendar.DATE, -dayOfWeek + 7)

    return dateFormat.format(c.time)
}