package com.lmj;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Date;

/**
 * @author lmj
 * @create 2021/6/16 19:49
 */
@Mojo( name= "myversion" , defaultPhase= LifecyclePhase. PACKAGE)
public class MyVersion extends AbstractMojo {

    @Parameter
    private String version;
    @Parameter
    private String updateinfo;
    @Parameter
    private String patch;
    @Parameter
    private String fileName;
    @Parameter
    private String gitversion;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //获取项目路径
        //创建文件并写入内容
        System.out.println("项目的根路径。。。。"+patch);
        try {
            //判断是否已经存在了
            String filePatch = patch + fileName;
            FileWriter fileWriter = null;
            if(!FileUtil.exist(filePatch)){
                File touch = FileUtil.touch(filePatch);
                fileWriter = new FileWriter(touch);
            }else{
                fileWriter = new FileWriter(filePatch);
            }
            fileWriter.write("");


            fileWriter.append("#项目版本"+"\r\n");
            String versionStr = version!=null?version:"";
            fileWriter.append("version="+versionStr+"\r\n");

            fileWriter.append("#git版本号"+"\r\n");
            String gitversionStr = gitversion!=null?gitversion:"";
            fileWriter.append("gitversion="+gitversionStr+"\r\n");

            fileWriter.append("#打包信息"+"\r\n");
            String updateinfoStr = updateinfo!=null?updateinfo:"";
            fileWriter.append("updateinfo="+updateinfoStr+"\r\n");

            fileWriter.append("#打包时间"+"\r\n");
            fileWriter.append("buildtime="+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"\r\n");


        } catch (Exception e) {
            System.out.println("创建打包信息文件失败，失败原因===="+e);
        }

    }

    public static void main(String[] args) {

    }
}
