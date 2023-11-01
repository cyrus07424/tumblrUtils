package constants;

/**
 * 環境設定.
 *
 * @author cyrus
 */
public interface Configurations {

	/**
	 * ChromeDriverの実行ファイルのパス.
	 */
	String CHROME_DRIVER_EXECUTABLE_PATH = "./drivers/chromedriver-windows-32bit.exe";

	/**
	 * ブラウザをヘッドレスモードで使用するか.
	 */
	boolean USE_HEADLESS_MODE = false;

	/**
	 * Tumblerアカウントのメールアドレス.
	 */
	final String TUMBLR_EMAIL = "CHANGEME";

	/**
	 * Tumblerアカウントのパスワード.
	 */
	final String TUMBLR_PASSWORD = "CHANGEME";
}