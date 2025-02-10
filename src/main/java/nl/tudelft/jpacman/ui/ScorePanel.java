package nl.tudelft.jpacman.ui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.level.Player;

/**
 * A panel displaying the players' scores and lives.
 */
public class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Map<Player, JLabel> scoreLabels;
    private final Map<Player, JLabel> lifeLabels;

    public static final ScoreFormatter DEFAULT_SCORE_FORMATTER =
        player -> String.format("Score: %3d", player.getScore());

    public static final ScoreFormatter DEFAULT_LIFE_FORMATTER =
        player -> String.format("Life: %3d", player.getlife());

    private ScoreFormatter scoreFormatter = DEFAULT_SCORE_FORMATTER;
    private ScoreFormatter lifeFormatter = DEFAULT_LIFE_FORMATTER;

    /**
     * Creates a new ScorePanel with a column for each player.
     *
     * @param players The list of players.
     */
    public ScorePanel(List<Player> players) {
        assert players != null;
        setLayout(new GridLayout(2, players.size()));

        scoreLabels = new LinkedHashMap<>();
        lifeLabels = new LinkedHashMap<>();

        for (int i = 1; i <= players.size(); i++) {
            add(new JLabel("Group 11: " + i, JLabel.CENTER));
        }

        for (Player player : players) {
            JLabel scoreLabel = new JLabel("0", JLabel.CENTER);
            JLabel lifeLabel = new JLabel("0", JLabel.CENTER);

            scoreLabels.put(player, scoreLabel);
            lifeLabels.put(player, lifeLabel);

            add(scoreLabel);
            add(lifeLabel);
        }
    }

    /**
     * Updates the displayed scores and lives of the players.
     */
    protected void refresh() {
        scoreLabels.forEach((player, label) -> {
            String scoreText = player.isAlive() ? scoreFormatter.format(player) : "You died.";
            label.setText(scoreText);
        });

        lifeLabels.forEach((player, label) -> {
            label.setText(lifeFormatter.format(player));
        });
    }

    /**
     * Interface for formatting player scores.
     */
    public interface ScoreFormatter {
        String format(Player player);
    }

    /**
     * Sets a custom score formatter.
     *
     * @param scoreFormatter The score formatter to use.
     */
    public void setScoreFormatter(ScoreFormatter scoreFormatter) {
        assert scoreFormatter != null;
        this.scoreFormatter = scoreFormatter;
    }
}
