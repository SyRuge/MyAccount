package com.xcx.account.ui.view

import android.app.Activity
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.utils.logd
import java.util.*

/**
 * Create By SyRuge at 2021-01-30
 */
class ChartHelper {
    companion object {

        const val TAG = "ChartHelper"

        /**
         * 设置pieChart图表基本属性
         */
        fun initPieChart(pieChart: PieChart) {
            /**
             * 设置坐标轴
             */
            pieChart.apply {
                //设置 pieChart 图表基本属性
                setUsePercentValues(true)//使用百分比显示, 表内数据用百分比替代，而不是原先的值。并且ValueFormatter中提供的值也是该百分比的。默认false
                setNoDataText(context.getString(R.string.chart_no_data))
                //设置pieChart图表的描述
                description.isEnabled = false
                //设置pieChart图表上下左右的偏移，类似于外边距
                //            setExtraOffsets(5F, 10F, 5F, 5F)
                //设置pieChart图表转动阻力摩擦系数[0,1]
                dragDecelerationFrictionCoef = 0.95F
                //设置pieChart图表起始角度
                rotationAngle = 0F
                //设置pieChart图表是否可以手动旋转
                isRotationEnabled = true
                //设置pieChart图表点击Item高亮是否可用
                isHighlightPerTapEnabled = true
                //设置pieChart图表展示动画效果
                //animateY(1400, Easing.EaseInOutQuad)
                //中心文本边界框矩形半径比例，默认是100%
                centerTextRadiusPercent = 100F
                //设置整个饼形图的角度，默认是360°即一个整圆，也可以设置为弧，这样现实的值也会重新计算
                maxAngle = 360F
                /**
                 * 设置 PieChart 图表Item文本属性
                 */
                //setDrawSliceText(true); //将X值绘制到饼状图环切片内,否则不显示。默认true,已弃用，用下面setDrawEntryLabels()
                setDrawEntryLabels(true)    // 同上,默认true，记住颜色和环不要一样，否则会显示不出来（true：下面属性才有效果）
                //            setEntryLabelColor(Color.BLACK)       //设置pieChart图表文本字体颜色
                setEntryLabelTextSize(10f)
                /**
                 * 设置 pieChart 内部圆环属性
                 */
                isDrawHoleEnabled = true              //默认true是否显示PieChart内部圆环,默认true(true:下面属性才有意义)
                holeRadius = 60f  // 设置PieChart内部圆的半径占整个饼形图圆半径（图表半径）的百分比。默认50%
                // 设置环形与中心圆之间的透明圆环半径占图表半径的百分比。默认55%（比如，中心圆为50%占比，而透明环设置为55%占比，要去掉中心圆的占比，也就是环只有5%的占比）
                transparentCircleRadius = 0f
                setTransparentCircleColor(Color.RED) // 上述透明圆环的颜色
                setTransparentCircleAlpha(0)    // 上述透明圆环的透明度[0-255],数值越小越透明，默认100
                setHoleColor(Color.WHITE)             //设置PieChart内部圆的颜色
                setDrawCenterText(true)               //是否绘制PieChart内部中心文本（true：下面属性才有意义）
                centerText =
                    AccountApp.appContext.getString(R.string.piechart_center_text) // 圆环中心的文字，会自动适配不会被覆盖
                setCenterTextSize(12f)                //设置PieChart内部圆文字的大小
                setCenterTextColor(Color.BLACK)         //设置PieChart内部圆文字的颜色
            }

            /**
             * 设置图例
             */
            pieChart.legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                isEnabled = false
            }
        }

        /**
         * 设置PieChart数据
         */
        fun setPieChartData(pieChart: PieChart, pieDataSet: PieDataSet) {
            pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            pieDataSet.sliceSpace = 3f // 每块之间的距离
            //        iPieDataSet.setValueLinePart1OffsetPercentage(80.f);//组成折线的两段折线长短
            //        iPieDataSet.setValueLinePart1OffsetPercentage(80.f);
            //        iPieDataSet.setValueLinePart1Length(0.5f);
            //        iPieDataSet.setValueLinePart2Length(0.5f);
            //        iPieDataSet.setValueTextColors(colors);
            val colors = mutableListOf<Int>()
            val MATERIAL_COLORS = intArrayOf(
                Color.rgb(200, 172, 255)
            )
            for (c in MATERIAL_COLORS) {
                colors.add(c)
            }
            for (c in ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(c)
            }
            pieDataSet.apply {
                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            }
            pieChart.setEntryLabelColor(Color.BLACK)
            pieDataSet.colors = colors
            val pieData = PieData(pieDataSet)

            pieData.setValueFormatter(PercentFormatter(pieChart))
            pieData.setValueTextSize(11f)
            pieData.setValueTextColor(Color.BLACK)

            pieChart.apply {
                data = pieData
                highlightValues(null)
                invalidate()
            }
        }

        /**
         * 设置LineChart图表基本属性
         */
        fun initLineChart(lineChart: LineChart, context: Activity) {
            /**
             * 设置坐标轴
             */
            lineChart.apply {
                //显示边界
                setDrawBorders(false)
                description.isEnabled = false
                setNoDataText(AccountApp.appContext.getString(R.string.chart_no_data))
            }
            //得到X轴
            val xAxis = lineChart.xAxis
            xAxis.apply {
                //设置X轴的位置（默认在上方)
                position = XAxis.XAxisPosition.BOTTOM
                //设置X轴坐标之间的最小间隔
                granularity = 1f
                //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
                setLabelCount(6, false)
                val c = Calendar.getInstance()
                val days = c.getActualMaximum(Calendar.DAY_OF_MONTH)
                //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
                //setAxisMinimum(0f)
                axisMaximum = days.toFloat()
                //不显示网格线
                setDrawGridLines(false)
                // 标签倾斜
                //setLabelRotationAngle(45)
                //设置X轴值为字符串
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        //                    logd(TAG, "initLineChart() value: $value")
                        val month = c.get(Calendar.MONTH) + 1
                        return "$month.${(value + 1).toInt()}"
                        //                    return super.getFormattedValue(value)
                    }
                }
            }
            //得到左右Y轴
            val yAxisLeft = lineChart.axisLeft
            val yAxisRight = lineChart.axisRight
            //右侧Y轴不显示
            yAxisRight.apply {
                //设置Y轴是否显示
                isEnabled = false
                setDrawAxisLine(false)
                setDrawLabels(false)
            }
            yAxisLeft.apply {
                //设置Y轴是否显示
                isEnabled = true
                //设置y轴坐标之间的最小间隔
                granularity = 1f
                //设置从Y轴值最小值
                axisMinimum = 0f
                //不显示网格线
                setDrawAxisLine(false)
                //零坐标线
                setDrawZeroLine(false)
                //            zeroLineWidth = 0f
                //            zeroLineColor = Color.TRANSPARENT
                //设置Y轴值为字符串
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        //                    return super.getFormattedValue(value)
                        return value.toInt().toString()
                    }
                }
                //设置y轴的刻度数量
                setLabelCount(6, false)
                //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
                // setLabelCount(Collections.max(list) + 2, false)
                //+1:y轴多一个单位长度，为了好看
                //setAxisMaximum(Collections.max(list) + 1)
            }

            /**
             * 设置图例
             */
            val legend = lineChart.legend
            legend.isEnabled = false

            /**
             * 设置MarkView
             */
            val mv = LineChartMarkView(context)
            mv.chartView = lineChart
            lineChart.marker = mv
            lineChart.invalidate()
        }

        /**
         * 设置LineChart数据
         */
        fun setLineChartData(lineChart: LineChart, lineDataSet: LineDataSet) {
            //一个LineDataSet就是一条线
            lineDataSet.apply {
                //线颜色
                color = AccountApp.appContext.resources.getColor(R.color.pink_color_500, null)
                //线宽度
                lineWidth = 1.6f
                //不显示圆点
                setDrawCircles(false)
                //线条平滑
                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                //设置折线图填充
                //setDrawFilled(true)
            }
            val lineData = LineData(lineDataSet)
            //折线图不显示数值
            lineData.setDrawValues(false)
            lineChart.apply {
                data = lineData
                invalidate()
            }
        }

        /**
         * 设置BarChart图表基本属性
         */
        fun initBarChart(barChart: BarChart) {
            /**
             * 设置坐标轴
             */
            barChart.apply {
                //显示边界
                setDrawBorders(false)
                // 不显示描述
                description.isEnabled = false
                setNoDataText(AccountApp.appContext.getString(R.string.chart_no_data))
                // 设置饼图的偏移量，类似于内边距 ，设置视图窗口大小
                //setExtraOffsets(20f, 20f, 20f, 20f)
            }
            // x是横坐标，表示位置，y是纵坐标，表示高度

            val labelName = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
            barChart.xAxis.apply {
                // 设置x轴
                // 设置x轴显示在下方，默认在上方
                position = XAxis.XAxisPosition.BOTTOM
                // 将此设置为true，绘制该轴的网格线。
                setDrawGridLines(false)
                // 设置x轴上的标签个数
                labelCount = labelName.size
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        //                    return super.getFormattedValue(value)
                        logd(TAG, "initBarChart: value: $value")
                        return if (value.toInt() < labelName.size) {
                            labelName[value.toInt()]
                        } else {
                            ""
                        }
                    }
                }
                // x轴上标签的大小
                //setTextSize(15f);
                // 设置标签对x轴的偏移量，垂直方向
                //setYOffset(15)
            }

            // 设置y轴，y轴有两条，分别为左和右
            barChart.axisLeft.apply {
                // 设置y轴的最小值
                axisMinimum = 0f
                //setTextSize(15f); // 设置y轴的标签大小
            }
            barChart.axisRight.apply {
                // 不显示右边的y轴
                isEnabled = false
                //setAxisMaximum(1200f) // 设置y轴的最大值
                //setAxisMinimum(0f) // 设置y轴的最小值
            }

            /**
             * 设置图例
             */
            barChart.legend.apply {
                //不显示图例
                form = Legend.LegendForm.NONE
                // 设置图例在图中
                setDrawInside(false)
                // 图例的方向为垂直
                orientation = Legend.LegendOrientation.HORIZONTAL
                //显示位置，水平右对齐
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                // 显示位置，垂直上对齐
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                // 设置水平与垂直方向的偏移量
                //setYOffset(55f)
                //setXOffset(30f)
                //setFormSize(12f) // 图例的图形大小
                //setTextSize(15f) // 图例的文字大小
            }
        }

        /**
         * 设置BarChart数据
         */
        fun setBarChartData(barChart: BarChart, barDataSet: BarDataSet) {
            barDataSet.apply {
                // 值的颜色
                valueTextColor = Color.RED
                // 柱子的颜色
                color = AccountApp.appContext.resources.getColor(R.color.pink_color_500, null)
                //setValueTextSize(15f) // 值的大小
                //label = "" // 设置标签之后，图例的内容默认会以设置的标签显示
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        if (value > 0) {
                            return value.toInt().toString()
                        }
                        return ""
                    }
                }
            }
            val barData = BarData(barDataSet)
            // 设置柱子的宽度
            barData.barWidth = 0.4f
            barChart.apply {
                data = barData
                invalidate()
            }
        }

    }
}