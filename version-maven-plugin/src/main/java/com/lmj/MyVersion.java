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

    /**
     * 项目版本
     **/
    @Parameter
    private String version;
    /**
     * 自定义更新信息
     **/
    @Parameter
    private String updateInfo;

    /**
     * 项目resource目录
     **/
    @Parameter
    private String patch;
    /**
     * 第三方代码托管工具版本号
     **/
    @Parameter
    private String codeVersionNumber;
    /**
     * 是否允许使用自定义文件名
     **/
    @Parameter
    private Boolean isCoverFileName =false;
    /**
     * 创建的自定义信息文件文件名
     **/
    @Parameter
    private String fileName;
    /**
     * 创建的自定义信息文件属性
     **/
    @Parameter
    private String prefix;
    /**
     * 获取的第三方管理工具属性前缀（用于标识使用什么软件）
     **/
    @Parameter
    private String versionToolPrefix;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //获取项目路径
        //创建文件并写入内容
        try {
            if(isCoverFileName){
                if(StrUtil.hasBlank(fileName)){
                    System.out.println("创建版本信息文件失败，自定义文件名不能为空！！！");
                    return ;
                }
            }else{
                fileName="version.properties";
            }
            //判断是否已经存在了
            String filePatch = patch + fileName;
            FileWriter fileWriter = null;
            if(!FileUtil.exist(patch)){
                File touch = FileUtil.touch(patch +fileName);
                fileWriter = new FileWriter(touch);
            }else{
                fileWriter = new FileWriter(filePatch);
            }
            fileWriter.write("");


            fileWriter.append("#项目版本"+"\r\n");
            String versionStr = version!=null?version:"";
            fileWriter.append("version="+versionStr+"\r\n");


            String versionToolPrefixStr =versionToolPrefix!=null?versionToolPrefix+".":"";

            fileWriter.append("#"+versionToolPrefix+"版本号"+"\r\n");
            String codeVersionNumberStr = codeVersionNumber!=null?codeVersionNumber:"";
            fileWriter.append(versionToolPrefixStr+"codeVersionNumber="+codeVersionNumberStr+"\r\n");

            fileWriter.append("#打包信息"+"\r\n");
            String updateinfoStr = updateInfo!=null?updateInfo:"";

            fileWriter.append(versionToolPrefixStr+"updateinfo="+updateinfoStr+"\r\n");

            fileWriter.append("#打包时间"+"\r\n");
            fileWriter.append("buildtime="+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"\r\n");

        } catch (Exception e) {
            System.out.println("创建打包信息文件失败，失败原因===="+e.getMessage());
        }

    }

}
