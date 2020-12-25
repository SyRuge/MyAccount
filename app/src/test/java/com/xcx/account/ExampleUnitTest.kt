package com.xcx.account

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMap() {
        var list1 = mutableListOf<SourceBean>()
        list1.add(SourceBean("1"))
        list1.add(SourceBean("2"))
        list1.add(SourceBean("3"))
        list1.add(SourceBean("4"))

        val map = list1.map {
            ConBean(it.id.toDouble() / 2)
        }

        println(map)
    }

    @Test
    fun testToday() {


        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val format = simpleDateFormat.format(Date())


    }

    @Test
    fun testWeek() {
        var date = "${getFirstDay()} ~ ${getLastDay()}"
        println(date)

    }

    fun getFirstDay(): String {
        val c = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        var day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1
        if (day_of_week == 0) {
            day_of_week = 7
        }
        c.add(Calendar.DATE, -day_of_week + 1)

        val format = simpleDateFormat.format(c.time)
        println(format)
        return format
    }

    fun getLastDay(): String {
        val c = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        var day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1
        if (day_of_week == 0) {
            day_of_week = 7
        }
        c.add(Calendar.DATE, -day_of_week + 7)

        val format = simpleDateFormat.format(c.time)
        println(format)
        return format
    }

    @Test
    fun testMonth() {
        val c = Calendar.getInstance()
        c.set(Calendar.DAY_OF_MONTH, 1)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDate = simpleDateFormat.format(c.time)
        println(startDate)


        val end_day_of_month = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        c.set(Calendar.DAY_OF_MONTH, end_day_of_month)

        val endDate = simpleDateFormat.format(c.time)
        println(endDate)

        var rangeDate = "$startDate ~ $endDate"
        println(rangeDate)


        /* val year = calendar.get(Calendar.YEAR)
         val month = calendar.get(Calendar.MONTH) + 1
         val day_of_month = calendar.get(Calendar.DAY_OF_MONTH)
         var format = String.format("%02d", day_of_month)
         val end_day_of_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
         var startDate = "$year-$month-$format"
         var endDate = "$year-$month-$end_day_of_month"
         var rangDate = "$year-$month-$format ~ $year-$month-$end_day_of_month"

         */
        /**
         * 0代表前面补领
         * 2代表数字长度为2
         * d代表参数为正数型
         *//*

        println("startDate: $startDate")
        println("endDate: $endDate")
        println("rangDate: $rangDate")*/


    }

    @Test
    fun testYear() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        c.set(Calendar.DAY_OF_YEAR, 1)
        val firstDay = simpleDateFormat.format(c.time)
        println(firstDay)

        val end_day_of_month = c.getActualMaximum(Calendar.DAY_OF_YEAR)
        c.set(Calendar.DAY_OF_YEAR, end_day_of_month)
        val lastDay = simpleDateFormat.format(c.time)
        println(lastDay)

        var rangDate = "$firstDay ~ $lastDay"
        println(rangDate)

    }
}