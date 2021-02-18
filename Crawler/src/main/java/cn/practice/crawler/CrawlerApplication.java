package cn.practice.crawler;

import cn.practice.crawler.task.ArticleTask;

/**
 * 爬虫模块启动类
 */
public class CrawlerApplication {

    public static void main(String[] args) {

        ArticleTask articleTask = new ArticleTask();

        articleTask.webTask("https://www.cnblogs.com/cate/javascript/","javascript");

        articleTask.webTask("https://www.cnblogs.com/cate/mysql/","数据库");

        articleTask.webTask("https://www.cnblogs.com/cate/linux/","操作系统");

        articleTask.webTask("https://www.cnblogs.com/cate/java/","java");

        articleTask.webTask("https://www.cnblogs.com/cate/bigdata/","大数据");

    }


}
