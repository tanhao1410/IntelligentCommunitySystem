package cn.practice.community.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class FileService {

    @Value("${pic-server.location}")
    private String picServerLocaton;//C:\\Users\\tanhao\\Desktop\\麻将论坛\\pic-server\\

    @Value("${pic-server.path}")
    private String picServerPath;//example:http://127.0.0.1/

    private static String AVATAR_DIC_NAME = "avatar/";
    private static String PIC_DIC_NAME = "pic/";

    public String saveAvatar(MultipartFile file) {

        //检查保存头像的目录
        File avatarDic = new File(picServerLocaton+AVATAR_DIC_NAME);
        if(!avatarDic.isDirectory()){
            avatarDic.mkdir();
        }

        String newFileName = System.currentTimeMillis()+file.getOriginalFilename();
        //保存头像文件
        String fileName = picServerLocaton+AVATAR_DIC_NAME +File.separator+  newFileName;
        File newFile = new File(fileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回url
        String avatarUrl = picServerPath + AVATAR_DIC_NAME+newFileName;
        return avatarUrl;
    }

    public String savePicture(MultipartFile file) {

        //创建保存问题图片的目录，按日期创建目录
        Date today = new Date(System.currentTimeMillis());
        String picDir = PIC_DIC_NAME+today.getYear() + File.separator + today.getMonth();
        File picDic = new File(picServerLocaton+picDir);
        if(!picDic.isDirectory()){
            picDic.mkdirs();
        }

        String newFileName = System.currentTimeMillis()+file.getOriginalFilename();
        //保存图片文件
        String fileName = picServerLocaton+picDir+File.separator +newFileName;
        File newFile = new File(fileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回url
        String avatarUrl = picServerPath +picDir.replaceAll("\\\\","/")+"/"+newFileName;
        return avatarUrl;

    }
}
