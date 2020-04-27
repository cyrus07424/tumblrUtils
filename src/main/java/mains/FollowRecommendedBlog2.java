package mains;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * おすすめのブログを全てフォロー.
 *
 * @author cyrus
 */
public class FollowRecommendedBlog2 extends AbstractCrawler {

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// クロール処理
		crawlMain((WebDriver webDriver) -> {
			while (true) {
				try {
					// オススメのブログの先頭要素を取得
					WebElement webElement = webDriver.findElement(By.cssSelector("._3PuD6"));

					// フォローボタンをクリック
					webDriver.findElement(By.cssSelector("._3PuD6 button.KeFJu")).click();

					// アニメーション完了のため待機
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		});
	}
}