package mains;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.SeleniumHelper;

/**
 * おすすめのブログを全てフォロー.
 *
 * @author cyrus
 */
public class FollowRecommendedBlog extends AbstractCrawler {

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// クロール処理
		crawlMain((WebDriver webDriver) -> {
			// あなたへのオススメ画面を表示
			webDriver.get("https://www.tumblr.com/explore/recommended-for-you");
			SeleniumHelper.waitForBrowserToLoadCompletely(webDriver);

			// その他のブログを見るリンクをクリック
			webDriver.findElement(By.cssSelector(".show-more-blogs")).click();

			// 全てのオススメのブログに対して実行
			for (WebElement blogCard : webDriver
					.findElements(By.cssSelector(".tumblelog_popover.popover--blog-card"))) {
				try {
					// フォロー
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
}