import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class DataOrganiserTest {

    @Test
    fun testCanLimitSizeOfArray() {
        // Given I have all player data from understat
        val understat = Understat()
        val playerData = understat.playerData

        // When I limit the size of the passed in array to 20
        val size = 20
        val limitedList = DataOrganiser().limit(playerData, size)

        // Then
        assertEquals(limitedList.size, size)
    }

    @Test
    fun testCanSortByKey() {
        // Given I have all player data from understat
        val understat = UnderstatKT()
        val playerData = understat.playerData

        // When I sort by the npxG attribute
        val key = "npxG"
        val sortedList = DataOrganiser().sortByKey(playerData, key)

        // Then the npxG value at index 1 should be greater than at index 2 which should be greater than at index 3
        val first = sortedList[0].getFloat(key)
        val second = sortedList[1].getFloat(key)
        val third = sortedList[2].getFloat(key)

        assertTrue("First " + first + " should be greater than " + second, first > second)
        assertTrue("Second " + second + " should be greater than " + third, second > third)
    }


}
