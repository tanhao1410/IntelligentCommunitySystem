package cn.practice.crawler.pipeline;

import cn.practice.crawler.utils.HTMLUtil;
import cn.practice.utils.IKUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class ArticleTextPipeline implements Pipeline {

    private String dataPath = "D:/crawler";


    private String channelId;//频道ID

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        String title=resultItems.get("title");//获取标题
        String content= HTMLUtil.delHTMLTag( resultItems.get("content"));//获取正文

        try {
            //分词
            PrintWriter printWriter=new PrintWriter(new File(dataPath+"/" +channelId+"/"+ UUID.randomUUID()+".txt"));
            //
            printWriter.print(IKUtil.spilt(title + content, " "));
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
