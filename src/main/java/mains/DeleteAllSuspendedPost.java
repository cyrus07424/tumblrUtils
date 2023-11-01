package mains;

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
public class DeleteAllSuspendedPost extends AbstractCrawler {

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// クロール処理
		crawlMain((WebDriver webDriver) -> {
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
		});
	}
}