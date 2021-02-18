package cn.practice.classification.util;

import org.deeplearning4j.iterator.CnnSentenceDataSetIterator;
import org.deeplearning4j.iterator.LabeledSentenceProvider;
import org.deeplearning4j.iterator.provider.FileLabeledSentenceProvider;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.ConvolutionMode;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.GlobalPoolingLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * CNN工具类
 */
public class CnnUtil {

    /**
     * 创建计算图（卷积神经网络）
     * @param cnnLayerFeatureMaps 卷积核的数量(=词向量维度)
     * @return 计算图
     */
    public static ComputationGraph  createComputationGraph(int cnnLayerFeatureMaps){
        //训练模型
        int vectorSize = 300;               //向量大小
        //int cnnLayerFeatureMaps = 100;      ////每种大小卷积层的卷积核的数量=词向量维度
        ComputationGraphConfiguration config = new NeuralNetConfiguration.Builder()
                .convolutionMode(ConvolutionMode.Same)// 设置卷积模式
                .graphBuilder()
                .addInputs("input")
                .addLayer("cnn1", new ConvolutionLayer.Builder()//卷积层
                        .kernelSize(3,vectorSize)//卷积区域尺寸
                        .stride(1,vectorSize)//卷积平移步幅
                        .nIn(1)
                        .nOut(cnnLayerFeatureMaps)
                        .build(), "input")
                .addLayer("cnn2", new ConvolutionLayer.Builder()
                        .kernelSize(4,vectorSize)
                        .stride(1,vectorSize)
                        .nIn(1)
                        .nOut(cnnLayerFeatureMaps)
                        .build(), "input")
                .addLayer("cnn3", new ConvolutionLayer.Builder()
                        .kernelSize(5,vectorSize)
                        .stride(1,vectorSize)
                        .nIn(1)
                        .nOut(cnnLayerFeatureMaps)
                        .build(), "input")
                .addVertex("merge", new MergeVertex(), "cnn1", "cnn2", "cnn3")//全连接层
                .addLayer("globalPool", new GlobalPoolingLayer.Builder()//池化层
                        .build(), "merge")
                .addLayer("out", new OutputLayer.Builder()//输出层
                        .nIn(3*cnnLayerFeatureMaps)
                        .nOut(5)
                        .build(), "globalPool")
                .setOutputs("out")
                .build();
        ComputationGraph net = new ComputationGraph(config);
        net.init();
        return net;
    }

    /**
     * 获取训练数据集
     * @param path 分词语料库根目录
     * @param childPaths 分词语料库子文件夹
     * @param vecModel 词向量模型
     * @return
     */
    public static DataSetIterator getDataSetIterator(String path, String[] childPaths, String vecModel ){
        //加载词向量模型
        WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(vecModel));
        //词标记分类比标签
        Map<String,List<File>> reviewFilesMap = new HashMap<>();

        for( String childPath: childPaths){
            reviewFilesMap.put(childPath, Arrays.asList(new File(path+"/"+ childPath ).listFiles()));
        }
        //标记跟踪
        LabeledSentenceProvider sentenceProvider = new FileLabeledSentenceProvider(reviewFilesMap, new Random(12345));
        return new CnnSentenceDataSetIterator.Builder()
                .sentenceProvider(sentenceProvider)
                .wordVectors(wordVectors)
                .minibatchSize(32)
                .maxSentenceLength(256)
                .useNormalizedWordVectors(false)
                .build();

    }


    /**
     * 预言
     * @param vecModel 词向量模型
     * @param cnnModel 卷积神经网络模型
     * @param dataPath 分词语料库根目录
     * @param childPaths 分词语料库文件夹
     * @param content 预言的文本内容
     * @return
     * @throws IOException
     */
    public static Map<String, Double> predictions(String vecModel,String cnnModel,String dataPath,String[] childPaths,String content) throws IOException {
        Map<String, Double> map = new HashMap<>();
        //模型应用
        ComputationGraph model = ModelSerializer.restoreComputationGraph(cnnModel);//通过cnn模型获取计算图对象
        //加载数据集
        DataSetIterator dataSet = CnnUtil.getDataSetIterator(dataPath,childPaths, vecModel);
        //通过句子获取概率矩阵对象
        INDArray featuresFirstNegative = ((CnnSentenceDataSetIterator) dataSet).loadSingleSentence(content);
        INDArray predictionsFirstNegative  =model.outputSingle(featuresFirstNegative);
        List<String> labels = dataSet.getLabels();


        int max = 0;

        for (int i = 0; i < labels.size(); i++) {
            if(predictionsFirstNegative.getDouble(i) > max){
                max = i;
            }
        }
        map.put(labels.get(max) + "", predictionsFirstNegative.getDouble(max));
        return map;
    }



}
