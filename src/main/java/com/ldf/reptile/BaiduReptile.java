package com.ldf.reptile;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.nio.cs.UnicodeEncoder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author lidefu
 * @date 2019/6/6 10:39
 */
public class BaiduReptile {

    public static void main(String[] args) throws IOException {
        test5();
//        test4();
        System.out.println(java.net.URLDecoder.decode("%E4%B8%9C%E6%96%B9%E5%9B%BD%E4%BF%A1", "UTF-8"));
        System.out.println(java.net.URLEncoder.encode("东方国信", "UTF-8"));
    }

    private static void test5() throws IOException {
        String url = "https://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=fde1fc920008274e&ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=%E4%B8%9C%E6%96%B9%E5%9B%BD%E4%BF%A1&rsv_spt=1&oq=%25E4%25B8%259C%25E6%2596%25B9%25E5%259B%25BD%25E4%25BF%25A1&rsv_pq=dfdac14000127dbe&rsv_t=4487P5b3piwD4BD3DmNTXmINE5SXmYQXkxol2vI748UIJj8lukOk0MYzUz1dA35Q%2BsDn&rqlang=cn&rsv_enter=0&gpc=stf%3D1559727850%2C1559814250%7Cstftype%3D1&tfflag=1&bs=%E4%B8%9C%E6%96%B9%E5%9B%BD%E4%BF%A1&rsv_sid=undefined&_ss=1&clist=c0ec7f32a1fc47ce&hsug=&f4s=1&csor=0&_cr1=36859";
        WebClient webclient = new WebClient();
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);
        WebRequest request = new WebRequest(new URL(url), HttpMethod.GET);
//        WebResponse response = webclient.loadWebResponse(request);
        HtmlPage page = webclient.getPage(request);
        String s = page.asXml();
        System.out.println(1);
    }

    private static void test4() throws IOException {

        String url = "https://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=fde1fc920008274e&ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=%E4%B8%9C%E6%96%B9%E5%9B%BD%E4%BF%A1&rsv_spt=1&oq=%25E4%25B8%259C%25E6%2596%25B9%25E5%259B%25BD%25E4%25BF%25A1&rsv_pq=dfdac14000127dbe&rsv_t=4487P5b3piwD4BD3DmNTXmINE5SXmYQXkxol2vI748UIJj8lukOk0MYzUz1dA35Q%2BsDn&rqlang=cn&rsv_enter=0&gpc=stf%3D1559727850%2C1559814250%7Cstftype%3D1&tfflag=1&bs=%E4%B8%9C%E6%96%B9%E5%9B%BD%E4%BF%A1&rsv_sid=undefined&_ss=1&clist=c0ec7f32a1fc47ce&hsug=&f4s=1&csor=0&_cr1=36859";
        WebClient webclient = new WebClient();
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = webclient.getPage(url);
        String asXml = page.asXml();
        System.out.println(asXml);
    }

    /**
     * 不能启用JS 才能拿到完整的页面内容（原因未知）
     * 依靠不断的点击事件，没法获取到时间不限的下拉框中的内容
     * @throws IOException
     */
    private static void test3() throws IOException {
        WebClient webclient = new WebClient();
        HtmlPage htmlPage;
        HtmlInput inputs;
        HtmlInput btn;
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);
        //百度搜索首页
        htmlPage = webclient.getPage("https://www.baidu.com/");
        inputs = (HtmlInput) htmlPage.getElementById("kw");
        btn = (HtmlInput) htmlPage.getElementById("su");
        inputs.setValueAttribute("东方国信");
        //搜索页
        HtmlPage tempPage = btn.click();
        String s = tempPage.asXml();
        HtmlElement element = (HtmlElement)tempPage.getByXPath("//*[@id=\"container\"]/div[2]/div/div[2]/div/i").get(0);
        //搜索工具页
        HtmlPage ssgjPage = element.click();
        String ssglXml = ssgjPage.asXml();
        //时间不限页
        HtmlElement sjbx = (HtmlElement)ssgjPage.getByXPath("//*[@id=\"container\"]/div[2]/div/div[1]/span[2]/i").get(0);
        HtmlPage sjbxPage = sjbx.click();
        String s1 = sjbxPage.asXml();
        //获取到一天内的a标签
        HtmlElement oneDay = (HtmlElement)sjbxPage.getByXPath("//*[@id=\"c-tips-container\"]/div[1]/div/div/ul/li[2]/a").get(0);
        HtmlPage resultPage = oneDay.click();
        String result = resultPage.asXml();
        System.out.println(true);
    }

    private static void test1() throws IOException {
        WebClient webClient = ReptileUtil.webClientBuilder();
        HtmlPage rootPage = webClient.getPage("https://www.baidu.com/");
        //设置一个运行JavaScript的时间
        webClient.waitForBackgroundJavaScript(5*1000);
        String html = rootPage.asXml();
        Document document = Jsoup.parse(html);
        //获取表单
        HtmlForm form = rootPage.getFormByName("f");
        //获取搜索输入框并赋值
        HtmlInput wd = form.getInputByName("wd");
        wd.setValueAttribute("东方国信");
        //获取表单提交（搜索）按钮 提交表单
        HtmlInput baidu = form.getInputByValue("百度一下");
        Page page = baidu.click();
        HtmlPage htmlPage = (HtmlPage) page;
        String xml = htmlPage.asXml();
        Document documentNext = Jsoup.parse(xml);
        System.out.println(1);
    }

    private static void test2() throws IOException {
        WebClient webClient = ReptileUtil.webClientBuilder();
        HtmlPage rootPage = webClient.getPage("https://www.baidu.com/");
        //设置一个运行JavaScript的时间
        webClient.waitForBackgroundJavaScript(5*1000);
        //获取输入框 并填入值
        HtmlInput kw = (HtmlInput) rootPage.getElementById("kw");
        kw.setValueAttribute("东方国信");
        //获取百度一下按钮 并点击
        HtmlInput su = (HtmlInput) rootPage.getElementById("su");
        HtmlPage click = su.click();
        String s = click.asXml();
        System.out.println(s);
    }

}
