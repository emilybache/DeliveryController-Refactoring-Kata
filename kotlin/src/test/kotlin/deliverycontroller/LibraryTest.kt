package deliverycontroller

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class LibraryTest {
    @Test
    fun someLibraryMethodReturnsTrue() {
        val classUnderTest = Library()
        assertTrue(classUnderTest.someLibraryMethod(), "someLibraryMethod should return 'true'")
    }
}
