package cn.practice.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.sound.midi.Soundbank;

/**
 * 文章爬取类
 */
public class ArticleProcessor implements PageProcessor {
    @Override
    public void process(Page page) {

        //https://www.cnblogs.com/zhai1997/p/13233816.html
        page.addTargetRequests(  page.getHtml().links().regex("https://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{8}.html").all());

        //文章标题 //*[@id="cb_post_title_url"]/span
        String title=page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/span").get();
        System.out.println(title+"-----------------");
        System.out.println(page.getHtml());
        String content=page.getHtml().xpath("/html/body/div/div[2]/div[1]/div/div/div/div[1]/div[2]/div[1]").get();
        System.out.println(content+"-----------------");
        if(title!=null && content!=null){
            page.putField("title" ,title );
            page.putField("content",content);
        }else{
            page.setSkip(true);//跳过
        }


    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3000).setSleepTime(1000);
    }
}
