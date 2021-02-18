package cn.practice.crawler.task;

import cn.practice.crawler.pipeline.ArticleTextPipeline;
import cn.practice.crawler.processor.ArticleProcessor;
import us.codecraft.webmagic.Spider;

public class ArticleTask {

    private ArticleProcessor articleProcessor = new ArticleProcessor();

    private ArticleTextPipeline articleTextPipeline = new ArticleTextPipeline();



    /**
     * 爬取web文章
     */
    public void webTask(String url,String tag){
        System.out.println("爬取"+tag+"文章");
        Spider spider =Spider.create(articleProcessor);
        //spider.addUrl("https://www.cnblogs.com/cate/web/");
        spider.addUrl(url);
        articleTextPipeline.setChannelId(tag);
        spider.addPipeline(articleTextPipeline);
        spider.start();//问题：爬到什么时候结束？
    }


}
