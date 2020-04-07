package mains;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.SeleniumHelper;

/**
 * 成人向けに指定された投稿を全て削除.
 *
 * @author cyrus
 */
public class TumblrDashboardCrawler {

	/**
	 * メールアドレス.
	 */
	private static final String EMAIL = "CHANGEME";

	/**
	 * パスワード.
	 */
	private static final String PASSWORD = "CHANGEME";

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// WebDriver
		WebDriver webDriver = null;
		try {
			// WebDriverを取得
			webDriver = SeleniumHelper.getWebDriver();

			// ログイン画面を開く
			webDriver.get("https://www.tumblr.com/login");
			SeleniumHelper.waitForBrowserToLoadCompletely(webDriver);

			// メールアドレスを入力
			webDriver.findElement(By.cssSelector("#signup_determine_email")).sendKeys(EMAIL);

			// 次へボタンをクリック
			webDriver.findElement(By.cssSelector("#signup_forms_submit")).click();

			// パスワードを使用してログインするボタンをクリック
			webDriver.findElement(By.cssSelector(".forgot_password_link")).click();

			// パスワードを入力
			webDriver.findElement(By.cssSelector("#signup_password")).sendKeys(PASSWORD);

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

			// アカウントメニューを表示
			webDriver.findElement(By.cssSelector("button.KeFJu._2PdxL[aria-label='アカウント']")).click();

			// 成人向けに指定された投稿を見るリンクをクリック
			try {
				webDriver.findElement(By.linkText("成人向けに指定された投稿を見る")).click();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				webDriver.findElement(By.cssSelector("._1A8vn > li:nth-child(7) > a:nth-child(1)")).click();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 成人向けに指定された投稿画面が表示されるまで待機
			SeleniumHelper.waitForBrowserToLoadCompletely(webDriver);

			// 処理済みフラグ
			boolean processed;
			while (true) {
				processed = false;

				// 全ての削除ボタンに対して実行
				for (WebElement deleteButton : webDriver.findElements(By.cssSelector("button[aria-label='削除']"))) {
					try {
						// スクロール位置を先頭まで戻す
						((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 0);");

						// クリック
						deleteButton.click();

						// OKボタンをクリック
						webDriver.findElement(By.cssSelector("button.rW5mf[aria-label='OK']")).click();

						// 処理済みフラグを変更
						processed = true;

						// アニメーション完了のため待機
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// 1件以上処理した場合
				if (processed) {
					// リロード
					webDriver.navigate().refresh();
				} else {
					// 終了
					break;
				}
			}
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