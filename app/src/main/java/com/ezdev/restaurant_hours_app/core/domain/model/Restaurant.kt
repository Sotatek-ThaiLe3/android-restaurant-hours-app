package com.ezdev.restaurant_hours_app.core.domain.model

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Restaurant(
    val name: String = "Unknown restaurant",
    val operatingHours: String = "Mon-Sun 0:00 am - 11:59 pm",
) {
    val weeklyCalendar: List<String> = getWeeklyCalendar(getOperatingHourItems(operatingHours))
    val isOpening: Boolean = isOpening(weeklyCalendar)

    /**
     * parse @operatingHours
     * "Mon-Wed, Sun 10:00 am - 10:00 pm / Fri-Sat 5:00 am - 5:00 pm / ..."
     * to list of single operatingHour
     * [Mon-Wed 10:00 am - 10:00 pm, Sun 10:00 am - 10:00 pm, Fri-Sat 5:00 am - 5:00 pm, ...]
     * */
    private fun getOperatingHourItems(operatingHours: String): List<String> {
        val operatingHourItems = operatingHours.split(" / ")

        if (operatingHours.contains(",")) {
            val mutableOperatingHourItems = operatingHourItems.toMutableList()
            operatingHourItems.forEach { operatingHour ->
                if (operatingHour.contains(",")) {
                    val items = operatingHour.split(", ")
                    val item1 = items[1]
                    val item2 = "${items[0]} ${item1.substringAfter(" ")}"

                    mutableOperatingHourItems.remove(operatingHour)
                    mutableOperatingHourItems.add(item1)
                    mutableOperatingHourItems.add(item2)
                }
            }

            return mutableOperatingHourItems
        }

        return operatingHourItems
    }

    /**
     * parse list of single operatingHour
     * [Mon-Tue 2:00 am - 2:00 pm]
     * to list of day in week
     * [Mon 2:00 am - 2:00 pm, Tue 2:00 am - 2:00 pm, Wed, ...]
     * */
    private fun getWeeklyCalendar(operatingHourItems: List<String>): List<String> {
        val daysOfWeek = listOf(
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        )

        val weeklyCalendar = daysOfWeek.toMutableList()
        operatingHourItems.forEach { operatingHour ->
            // time part
            val daysPart: String = operatingHour.substringBefore(" ")
            val hoursPart: String = operatingHour.substringAfter(" ")

            // day range
            val days = daysPart.split("-")
            val startDay = days[0]
            val endDay = if (days.size == 2) days[1] else startDay

            val startDayIndex = daysOfWeek.indexOf(startDay)
            val endDayIndex = daysOfWeek.indexOf(endDay)

            for (dayIndex in daysOfWeek.indices) {
                if (dayIndex in startDayIndex..endDayIndex) {
                    weeklyCalendar[dayIndex] = "${daysOfWeek[dayIndex]} $hoursPart"
                }
            }
        }

        return weeklyCalendar
    }

    /**
     * get day in @weeklyCalendar by current day
     * [Mon 2:00 am - 2:00 pm, Tue 3:00 am - 3:00 pm, Wed 4:00 am - 4:00 pm, Thu 5:00 am - 5:00 pm, ...]
     * to
     * (Wed, 4:00 am - 4:00 pm)
     * */
    private fun getCurrentOperatingHour(weeklyCalendar: List<String>): Pair<String, String> {
        val date = Date()
        val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
        val hourFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        var currentDay = dayFormatter.format(date)
        val currentHour = hourFormatter.format(date)

        for (day in weeklyCalendar) {
            val dayInWeek = day.substringBefore(" ")
            if (currentDay.equals(dayInWeek)) {
                currentDay = day
                println(day)
                break
            }
        }


        return Pair(currentDay, currentHour)
    }

    private fun isOpening(weeklyCalendar: List<String>): Boolean {
        val (currentDay, currentHour) = getCurrentOperatingHour(weeklyCalendar)

        val hoursPart: String = currentDay.substringAfter(" ", missingDelimiterValue = "")
        if (hoursPart.isBlank()) {
            return false
        }

        val hours = hoursPart.split(" - ")
        val startHour = hours[0]
        val endHour = hours[1]
        val current = currentHour.toTime()
        val start = startHour.toTime()
        val end = endHour.toTime()

        return if (start <= end) {
            current in start..end
        } else {
            current >= start || current <= end
        }
    }

    private fun String.toTime(): Date {
        try {
            val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
            val time = formatter.parse(this.uppercase())
            return time!!
        } catch (e: ParseException) {
            throw IllegalArgumentException("Invalid time format provided")
        }
    }
}