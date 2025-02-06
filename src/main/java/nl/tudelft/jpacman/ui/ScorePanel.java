package nl.tudelft.jpacman.ui;

import java.awt.GridLayout;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import nl.tudelft.jpacman.level.Player;

public class ScorePanel extends JPanel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient Map<Player, JLabel> scoreLabels;
    private transient ScoreFormatter scoreFormatter;

    public static final ScoreFormatter DEFAULT_FORMATTER = new DefaultScoreFormatter();

    public ScorePanel(List<Player> players) {
        super();
        Objects.requireNonNull(players, "Players list cannot be null");
        setLayout(new GridLayout(2, players.size()));

        for (int i = 1; i <= players.size(); i++) {
            add(new JLabel("Player " + i, SwingConstants.CENTER));
        }
        scoreLabels = new LinkedHashMap<>();
        players.forEach(player -> {
            JLabel scoreLabel = new JLabel("0", SwingConstants.CENTER);
            scoreLabels.put(player, scoreLabel);
            add(scoreLabel);
        });

        this.scoreFormatter = DEFAULT_FORMATTER;
    }

    protected void refresh() {
        scoreLabels.forEach((player, label) ->
            label.setText(scoreFormatter.format(player))
        );
    }

    public interface ScoreFormatter extends Serializable {
        String format(Player player);
    }

    public void setScoreFormatter(ScoreFormatter scoreFormatter) {
        this.scoreFormatter = Objects.requireNonNull(scoreFormatter, "Formatter cannot be null");
    }

    private static class DefaultScoreFormatter implements ScoreFormatter {
        @Override
        public String format(Player player) {
            return player.isAlive()
                ? String.format("Score: %3d", player.getScore())
                : String.format("You died. Score: %3d", player.getScore());
        }
    }
}
