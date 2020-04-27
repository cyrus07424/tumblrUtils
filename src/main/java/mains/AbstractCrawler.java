package mains;

import java.util.Scanner;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import constants.Configurations;
import utils.SeleniumHelper;

/**
 * ベースクラス.
 *
 * @author cyrus
 */
public abstract class AbstractCrawler {

	/**
	 * クロール共通処理.
	 *
	 * @param consumer
	 */
	public static void crawlMain(Consumer<WebDriver> consumer) {
		// WebDriver
		WebDriver webDriver = null;
		try {
			// WebDriverを取得
			webDriver = SeleniumHelper.getWebDriver();

			// ログイン画面を開く
			webDriver.get("https://www.tumblr.com/login");
			SeleniumHelper.waitForBrowserToLoadCompletely(webDriver);

			// メールアドレスを入力
			webDriver.findElement(By.cssSelector("#signup_determine_email")).sendKeys(Configurations.TUMBLR_EMAIL);

			// 次へボタンをクリック
			webDriver.findElement(By.cssSelector("#signup_forms_submit")).click();

			// パスワードを使用してログインするボタンをクリック
			webDriver.findElement(By.cssSelector(".forgot_password_link")).click();

			// パスワードを入力
			webDriver.findElement(By.cssSelector("#signup_password")).sendKeys(Configurations.TUMBLR_PASSWORD);

			// ログインボタンをクリック
			webDriver.findElement(By.cssSelector("#signup_forms_submit")).click();

			try {
				// セキュリティコードの入力欄が表示されているかを確認
				WebElement tfa = webDriver.findElement(By.cssSelector("#tfa_response_field"));

				// セキュリティコードをコンソールから入力
				System.out.println("セキュリティコードを入力してください。");
				String securityCode = null;
				try (Scanner scanner = new Scanner(System.in)) {
					securityCode = scanner.nextLine();
				}

				// セキュリティコードを入力
				tfa.sendKeys(securityCode);

				// ログインボタンをクリック
				webDriver.findElement(By.cssSelector("#signup_forms_submit")).click();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ダッシュボードが表示されるまで待機
			SeleniumHelper.waitForBrowserToLoadCompletely(webDriver);

			// クロール処理メイン
			consumer.accept(webDriver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// WebDriverを終了
			if (webDriver != null) {
				webDriver.quit();
			}

			// 終了
			System.exit(0);
		}
	}
}