package neu.tests;

import neu.ComputerPlayer;
import neu.IPlayer;
import neu.VierGewinnt;
import neu.computerPlayerLevels.CPLevel1;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the computerPlayer against other computerPlayers
 */
public class ComputerPlayerTest {

    @Test
    public void testWinsAgainstLvl1asFirstPlayer() throws Exception {
        VierGewinnt game = new VierGewinnt();
        IPlayer favorite = new ComputerPlayer();
        IPlayer opponent = new CPLevel1();

        assertEquals(favorite, game.getWinner(favorite, opponent));
    }

    @Test
    public void testWinsAgainstLvl1asSecondPlayer() throws Exception {
        VierGewinnt game = new VierGewinnt();
        IPlayer favorite = new ComputerPlayer();
        IPlayer opponent = new CPLevel1();

        assertEquals(favorite, game.getWinner(opponent, favorite));
    }

}