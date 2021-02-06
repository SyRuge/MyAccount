package com.xcx.account.utils

import java.util.*

/**
 * Created by SyRuge on 2020年12月28日.
 */

private const val TAG = "TimeUtils"

fun todayStartTime(): Long {
    val c = Calendar.getInstance()

    c.set(Calendar.HOUR_OF_DAY,0)
    c.set(Calendar.MINUTE,0)
    c.set(Calendar.SECOND,0)
    c.set(Calendar.MILLISECOND,0)

    return c.timeInMillis
}

fun todayEndTime(): Long {
    val c = Calendar.getInstance()

    c.set(Calendar.HOUR_OF_DAY,23)
    c.set(Calendar.MINUTE,59)
    c.set(Calendar.SECOND,59)
    c.set(Calendar.MILLISECOND,999)

    return c.timeInMillis
}
fun weekStartTime(): Long {
    val c = Calendar.getInstance()

    var dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1
    if (dayOfWeek == 0) {
        dayOfWeek = 7
    }
    c.add(Calendar.DATE, -dayOfWeek + 1)
    c.set(Calendar.HOUR_OF_DAY,0)
    c.set(Calendar.MINUTE,0)
    c.set(Calendar.SECOND,0)
    c.set(Calendar.MILLISECOND,0)

    return c.timeInMillis
}

fun weekEndTime(): Long {
    val c = Calendar.getInstance()
    var dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1
    if (dayOfWeek == 0) {
        dayOfWeek = 7
    }
    c.add(Calendar.DATE, -dayOfWeek + 7)
    c.set(Calendar.HOUR_OF_DAY,23)
    c.set(Calendar.MINUTE,59)
    c.set(Calendar.SECOND,59)
    c.set(Calendar.MILLISECOND,999)

    return c.timeInMillis
}
fun monthStartTime(time:Long=0): Long {
    val c = Calendar.getInstance()

    if (time > 0) {
        c.timeInMillis = time
    }
    c.set(Calendar.DAY_OF_MONTH, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)


    return c.timeInMillis
}

fun monthEndTime(time:Long=0): Long {
    val c = Calendar.getInstance()
    if (time > 0) {
        c.timeInMillis = time
    }
    val endDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)

    c.set(Calendar.DAY_OF_MONTH, endDayOfMonth)
    c.set(Calendar.HOUR_OF_DAY, 23)
    c.set(Calendar.MINUTE, 59)
    c.set(Calendar.SECOND, 59)
    c.set(Calendar.MILLISECOND, 999)

    return c.timeInMillis
}

fun yearStartTime(time:Long=0): Long {
    val c = Calendar.getInstance()
    if (time > 0) {
        c.timeInMillis = time
    }

    c.set(Calendar.DAY_OF_YEAR, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)


    return c.timeInMillis
}

fun yearEndTime(time:Long=0): Long {
    val c = Calendar.getInstance()
    if (time > 0) {
        c.timeInMillis = time
    }
    val lastDayOfYear = c.getActualMaximum(Calendar.DAY_OF_YEAR)

    c.set(Calendar.DAY_OF_YEAR, lastDayOfYear)
    c.set(Calendar.HOUR_OF_DAY, 23)
    c.set(Calendar.MINUTE, 59)
    c.set(Calendar.SECOND, 59)
    c.set(Calendar.MILLISECOND, 999)

    return c.timeInMillis
}

fun preMonthStartTime(startTime:Long): Long {
    val c = Calendar.getInstance()
    c.timeInMillis = startTime

    val month = c.get(Calendar.MONTH) - 1
    c.set(Calendar.MONTH, month)
    c.set(Calendar.DAY_OF_MONTH, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)

    return c.timeInMillis
}

fun nextMonthStartTime(endTime:Long): Long {
    val c = Calendar.getInstance()
    c.timeInMillis = endTime

    val month = c.get(Calendar.MONTH) + 1
    c.set(Calendar.MONTH, month)
    c.set(Calendar.DAY_OF_MONTH, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)

    return c.timeInMillis
}

fun preYearStartTime(startTime:Long): Long {
    val c = Calendar.getInstance()
    c.timeInMillis = startTime

    val year = c.get(Calendar.YEAR) - 1
    c.set(Calendar.YEAR, year)
    c.set(Calendar.DAY_OF_YEAR, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)

    return c.timeInMillis
}

fun nextYearStartTime(endTime:Long): Long {
    val c = Calendar.getInstance()
    c.timeInMillis = endTime

    val year = c.get(Calendar.YEAR) + 1
    c.set(Calendar.YEAR, year)
    c.set(Calendar.DAY_OF_YEAR, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)

    return c.timeInMillis
}

fun dayStartTime(time : Long=0): Long {
    val c = Calendar.getInstance()
    if (time>0){
        c.timeInMillis = time
    }

    c.set(Calendar.HOUR_OF_DAY,0)
    c.set(Calendar.MINUTE,0)
    c.set(Calendar.SECOND,0)
    c.set(Calendar.MILLISECOND,0)

    return c.timeInMillis
}

fun dayEndTime(time : Long=0): Long {
    val c = Calendar.getInstance()
    if (time>0){
        c.timeInMillis = time
    }

    c.set(Calendar.HOUR_OF_DAY,23)
    c.set(Calendar.MINUTE,59)
    c.set(Calendar.SECOND,59)
    c.set(Calendar.MILLISECOND,999)

    return c.timeInMillis
}