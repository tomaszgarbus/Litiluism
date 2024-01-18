package com.tgarbus.litiluism.ui.reusables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun StatisticsLineChart(title: String, values: List<Int>) {
    Text(title)
    val chartEntryModel = entryModelOf(1f, 10f, 8, 6f, 0f)
    Chart(
        chart = lineChart(),
        model = chartEntryModel,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(),
    )
}
