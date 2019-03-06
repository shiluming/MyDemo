package com.slm.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebClientTest {


    @Test
    public void testJunitClient() {

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            CookieManager cookieManager = webClient.getCookieManager();
            String cookie = "Ugrow-G0=57484c7c1ded49566c905773d5d00f82; SUB=_2AkMrI6sdf8NxqwJRmP4Tz2jkbYR1zw7EieKdf1rGJRMxHRl-yj83qlIDtRB6AKOF8p0JpvHIhFf0q7JUUAws_hpjzBAd; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WFeUzCyiy5HGRraUmLZcGVE; login_sid_t=bf680787b8bb8d9674627f3276e74287; cross_origin_proto=SSL";
            String[] split = cookie.split(";");
            String domain = "weibo.com";
            for (String str : split) {
                String[] tmp = str.split("=");
                cookieManager.addCookie(new Cookie(domain, tmp[0], tmp[1]));
            }

            webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
            webClient.setCookieManager(cookieManager);
            final HtmlPage page = webClient.getPage(new URL("https://weibo.com/?category=99991"));

            HtmlElement documentElement =
                    page.getDocumentElement();
//            System.out.println(documentElement.getTextContent());
            System.out.println(page.asText());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
