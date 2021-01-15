package com.xcx.account

import android.bluetooth.BluetoothClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
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
    fun changeDate() {
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val c = Calendar.getInstance()


        val d=1610616481498

        println(f.format(d))


        c.timeInMillis = d
        c.set(Calendar.YEAR, 2021)
        c.set(Calendar.MONTH, 0)
        c.set(Calendar.DAY_OF_MONTH, 13)

        println(f.format(c.timeInMillis))
    }

    @Test
    fun testListener() {
        val l = MyListener()
        l.setListener {
            cancel {

            }
            ok {

            }
        }
        l.zzz()
        val list = mutableListOf<TimeBean>().toList()
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val c = Calendar.getInstance()

        val max = c.getActualMaximum(Calendar.DAY_OF_MONTH)

        println(max)

        c.set(Calendar.MONTH,1)
        c.set(Calendar.DAY_OF_MONTH,1)

        println(f.format(c.timeInMillis))
    }

    @Test
    fun testSortDay() {
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        val c = Calendar.getInstance()


        //1610350424964
        var t = 1610350424964

        val list = mutableListOf<TimeBean>()
        for (i in 0..1) {
            var time = f.format(t)
            list.add(TimeBean("水果${i + 1}", (i + 1) * 10L, t))
            t += 100000 * (i + 1)
        }

        //today 1610437748854
        list.add(TimeBean("水果4", 123, 1610437748854))
        list.add(TimeBean("水果5", 45, 1610437748854 + 300000))


        println(f.format(System.currentTimeMillis()))
        list.groupBy {
            c.timeInMillis = it.time
            c.get(Calendar.DAY_OF_MONTH)
        }.values.map {
            it.reduce { acc, item ->
                println("reduce: ${acc.msg}")
                c.timeInMillis = item.time
                c.get(Calendar.DAY_OF_MONTH)
                TimeBean(acc.msg, acc.money + item.money, c.get(Calendar.DAY_OF_MONTH).toLong())
            }
        }.forEach {
//            println(it)
        }
        println(f.format(System.currentTimeMillis()))


    }

    @Test
    fun testMap() {
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        val c = Calendar.getInstance()


        //1610350424964
        var t = 1610350424964

        val list = mutableListOf<TimeBean>()
        for (i in 0..0) {
            var time = f.format(t)
            list.add(TimeBean("水果${i + 1}", (i + 1) * 10L, i+1L))
            t += 100000 * (i + 1)
        }

        //today 1610437748854
        list.add(TimeBean("水果4", 123, 4))
        list.add(TimeBean("水果5", 45, 4))


        val map = list.groupBy {
           it.time
        }.toMutableMap()

        for (i in 1..12) {
            if (!map.containsKey(i.toLong())) {
                map[i.toLong()] = mutableListOf(TimeBean("水果$i",0,i.toLong()))
            }
        }


        val map1 = map.values.map {
            it.reduce { acc, item ->
                println("reduce acc: $acc")
                println("reduce item: $item")
                TimeBean(acc.msg, acc.money + item.money, acc.time)
            }
        }.sortedBy {
            it.time
        }.toMutableList()

        println(map1[0])


        c.timeInMillis = System.currentTimeMillis()

        val actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH)

        println(actualMaximum)


        map.forEach { (t, u) ->
//            println(u)
        }

    }

    @Test
    fun testToday() {


        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val format = simpleDateFormat.format(Date())

        val c = Calendar.getInstance()

        val curTime = c.timeInMillis


        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        val startTime = c.timeInMillis

        println(startTime)

        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)

        val endTime = c.timeInMillis

        if (curTime in startTime..endTime) {
            println("true")
        }

    }

    @Test
    fun testWeek() {
        var date = "${getFirstDay()} ~ ${getLastDay()}"
        println(date)

    }

    @Test
    fun testList() {
        val list = mutableListOf<MyBean>()

        list.add(MyBean(3))
        Thread.sleep(500)
        list.add(MyBean(5))
        Thread.sleep(500)
        list.add(MyBean(8))
        Thread.sleep(500)
        list.add(MyBean(6))
        Thread.sleep(500)

        val l = mutableListOf<MyBean>()
        l.addAll(list)

        val filter = l.filter {
            it.time in 2..3
        }.toMutableList()


        println("==============")
        list.sortByDescending {
            it.time
        }
        for ((i, v) in list.withIndex()) {
            println("i: $i, v: $v")

        }
        /*for (myBean in list) {
            println(myBean)

        }*/

    }

    @Test
    fun testString() {
        var str = "23."

//        println(index)
        val ori = BigDecimal(str)
        val b1 = ori.multiply(BigDecimal.valueOf(100))

        val divide = BigDecimal.valueOf(100.0)

        val b2 = b1.divide(BigDecimal.valueOf(100.0)).setScale(2, BigDecimal.ROUND_DOWN)

        println(b1.toLong())
        println(b2.toString())

        val toString = BigDecimal.valueOf(0).divide(BigDecimal.valueOf(100.0))
//        toString.setScale(2,BigDecimal.ROUND_DOWN)
        println(toString.toDouble().toString())

    }

    fun getFirstDay(): String {
        val c = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1
        if (day_of_week == 0) {
            day_of_week = 7
        }
        c.add(Calendar.DATE, -day_of_week + 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        val timeInMillis = c.timeInMillis

        val format = simpleDateFormat.format(c.time)
        val format1 = simpleDateFormat.format(Date(timeInMillis))
        println(format1)
        return format
    }

    fun getLastDay(): String {
        val c = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1
        if (day_of_week == 0) {
            day_of_week = 7
        }
        c.add(Calendar.DATE, -day_of_week + 7)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)

        val timeInMillis = c.timeInMillis

        val format = simpleDateFormat.format(c.time)
        val format1 = simpleDateFormat.format(Date(timeInMillis))
        println(format1)
        return format
    }

    @Test
    fun testMonth() {
        val c = Calendar.getInstance()
        c.set(Calendar.DAY_OF_MONTH, 1)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val startDate = simpleDateFormat.format(c.time)
        println(startDate)


        val end_day_of_month = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        c.set(Calendar.DAY_OF_MONTH, end_day_of_month)

        val endDate = simpleDateFormat.format(c.time)
        println(endDate)

        var rangeDate = "$startDate ~ $endDate"
        println(rangeDate)


        c.set(Calendar.DAY_OF_MONTH, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)



        c.set(Calendar.DAY_OF_MONTH, end_day_of_month)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)

        val timeInMillis = c.timeInMillis

        val format = simpleDateFormat.format(Date(timeInMillis))
        println(format)

        println(timeInMillis)


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
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val c = Calendar.getInstance()

        c.set(Calendar.DAY_OF_YEAR, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        val end_day_of_month = c.getActualMaximum(Calendar.DAY_OF_YEAR)


        c.set(Calendar.DAY_OF_YEAR, end_day_of_month)
        c.set(Calendar.HOUR_OF_DAY, 23)
        c.set(Calendar.MINUTE, 59)
        c.set(Calendar.SECOND, 59)
        c.set(Calendar.MILLISECOND, 999)
        val timeInMillis = c.timeInMillis

        val firstDay = simpleDateFormat.format(Date(timeInMillis))
        println(firstDay)

        c.set(Calendar.DAY_OF_YEAR, end_day_of_month)
        val lastDay = simpleDateFormat.format(c.time)
        println(lastDay)

        var rangDate = "$firstDay ~ $lastDay"
        println(rangDate)

    }
}