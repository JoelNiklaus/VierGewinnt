package tests.computerPlayer;

import vierGewinnt.ComputerPlayer;
import vierGewinnt.IPlayer;
import vierGewinnt.VierGewinnt;
import computerPlayerLevels.CPLevel4;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the computerPlayer against CPLevel2
 */
public class ComputerPlayerTestLevel4 {
    IPlayer favorite = new ComputerPlayer();
    IPlayer opponent = new CPLevel4();

    // as firstPlayer
    @Test
    public void testDoesNotLoseAgainstLvl1asFirstPlayer() throws Exception {
        VierGewinnt game = new VierGewinnt();

        assertNotEquals(opponent, game.getWinner(favorite, opponent));
    }

    @Test
    public void testWinsAgainstLvl1asFirstPlayer() throws Exception {
        VierGewinnt game = new VierGewinnt();

        assertEquals(favorite, game.getWinner(favorite, opponent));
    }

    // as secondPlayer
    @Test
    public void testDoesNotLoseAgainstLvl1asSecondPlayer() throws Exception {
        VierGewinnt game = new VierGewinnt();

        assertNotEquals(opponent, game.getWinner(opponent, favorite));
    }

    @Test
    public void testWinsAgainstLvl1asSecondPlayer() throws Exception {
        VierGewinnt game = new VierGewinnt();

        assertEquals(favorite, game.getWinner(opponent, favorite));
    }

}