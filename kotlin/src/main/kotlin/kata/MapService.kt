package kata

import java.time.Duration
import kotlin.math.*

class MapService
{
    // in km/h
    private var averageSpeed: Double = 50.0

    fun calculateETA(location1: Location, location2: Location): Double {
        val distance = this.calculateDistance(location1, location2)
        return distance / this.averageSpeed * MINUTES_PER_HOUR
    }

    fun updateAverageSpeed(location1: Location, location2: Location, elapsedTime: Duration) {
        val distance = this.calculateDistance(location1, location2)
        val updatedSpeed = distance / (elapsedTime.toSeconds() / SECONDS_PER_HOUR)
        this.averageSpeed = updatedSpeed
    }

    private fun calculateDistance(location1: Location, location2: Location): Double {
        val d1 = location1.latitude * (Math.PI / 180.0)
        val num1 = location1.longitude * (Math.PI / 180.0)
        val d2 = location2.latitude * (Math.PI / 180.0)
        val num2 = location2.longitude * (Math.PI / 180.0) - num1
        val d3 = sin((d2 - d1) / 2.0).pow(2.0) + cos(d1) * cos(d2) * sin(num2 / 2.0).pow(2.0)

        return R * (2.0 * atan2(sqrt(d3), sqrt(1.0 - d3)))
    }

    companion object {
        private const val MINUTES_PER_HOUR: Int = 60
        private const val SECONDS_PER_HOUR: Int = 3600
        private const val R: Double = 6373.0
    }
}