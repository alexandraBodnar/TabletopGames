package games.diamant;

import core.*;
import games.GameType;
import players.human.HumanConsolePlayer;
import players.simple.OSLAPlayer;
import players.simple.RandomPlayer;
import utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class DiamantGame extends Game {
    public DiamantGame(List<AbstractPlayer> players, AbstractParameters gameParameters) {
        super(GameType.Diamant, players, new DiamantForwardModel(), new DiamantGameState(gameParameters, players.size()));
    }

    public static void main(String[] args) {
        ArrayList<AbstractPlayer> agents = new ArrayList<>();
        agents.add(new RandomPlayer());
        agents.add(new RandomPlayer());
        agents.add(new OSLAPlayer());
        //agents.add(new HumanConsolePlayer());

        // Play n games and return the pct of wins of each player
        double [] playerWins = new double[agents.size()];
        for (int i=0; i<agents.size(); i++)
            playerWins[i] = 0.0;

        int n = 1000;
        for (int i=0; i<n; i++) {
            System.out.println("GAME : " + i);
            AbstractParameters gameParameters = new DiamantParameters(System.currentTimeMillis());

            Game game = new DiamantGame(agents, gameParameters);
            game.run(null);

            Utils.GameResult [] results =  game.getGameState().getPlayerResults();
            for (int j=0; j<agents.size(); j++)
                if (results[j] == Utils.GameResult.WIN)
                {
                    playerWins[j] += 1;
                    break;
                }
                else if (results[j] == Utils.GameResult.DRAW)
                {
                    playerWins[j] += 0.5;
                }
        }

        for (int i=0; i<agents.size(); i++)
            System.out.println("Player " + i + " [ " + agents.get(i).toString() +"] won: " + (playerWins[i] / n)*100.0 + "% of games.");
    }

    @Override
    public String toString()
    {
        return "Diamant Game for " + gameState.getNPlayers() + " players.";
    }
}
