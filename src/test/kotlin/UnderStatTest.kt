import junit.framework.TestCase.*
import org.junit.Test

class UnderStatTest {

    @Test
    fun testCanGetPlayerData() {
        // When obtaining the player data from understat site
        val understat = Understat()
        val playerData = understat.playerData

        // Then we expect there to be a minimum of 220 items (11 players x 20 teams)
        val minimumExpectedNumberOfItems = 220
            assertTrue(playerData.length() >= minimumExpectedNumberOfItems)
    }

    @Test
    fun testCanGetDatesData() {
        // When obtaining the dates data from understat site
        val understat = Understat()
        val data = understat.fixtureData

        // Then we expect there to be 38 rounds of 10 fixtures (38x10=380 items)
        val expectedItems = 380
        assertEquals(expectedItems, data.length())
    }

    @Test
    fun testCanGetTeamsData() {
        // When obtaining the teams data from understat site
        val understat = Understat()
        val teamsData = understat.teamsData

        // Then we expect 20 items (one for each team in the premier league)
        val teamsInLeague = 20
        assertEquals(teamsInLeague, teamsData.length())
    }
}
