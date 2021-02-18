package cn.practice.classification;

import cn.practice.classification.service.ClassificationService;
import cn.practice.classification.service.Word2VecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//@Component
public class TrainTask implements ApplicationRunner {

    @Autowired
    private Word2VecService word2VecService;

    @Autowired
    private ClassificationService service;

    public void trainModel(){
        System.out.println("合并分词语料库开始");
        word2VecService.mergeWord();
        System.out.println("合并分词语料库结束");

        System.out.println("构建词向量模型开始");
        word2VecService.build();
        System.out.println("构建词向量模型结束");

        System.out.println("构建卷积模型开始");
        service.build();
        System.out.println("构建卷积模型结束");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        trainModel();
    }
}
