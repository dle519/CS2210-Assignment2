/**
 * Configuration class for the score and config of board
 * 
 * @author David Le
 *
 */
public class Configuration {
	protected String config;
	protected int score;

	public Configuration(String config, int score) {
		this.config = config;
		this.score = score;

	}

	/**
	 * Return string configuration
	 */
	public String getStringConfiguration() {
		return this.config;
	}

	/**
	 * Return score of the config
	 */
	public int getScore() {
		return this.score;
	}
}
