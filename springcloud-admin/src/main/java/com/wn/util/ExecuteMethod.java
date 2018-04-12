package com.wn.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecuteMethod {
	/** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
          
        KrpanoEntity krEntity = new KrpanoEntity();  
        krEntity.setSourcePath("D:\\程序安装目录\\apache-tomcat-8.5.20\\webapps\\360");  
//        krEntity.setTargetPath("D:\\程序安装目录\\apache-tomcat-8.5.20\\webapps\\360");  
        krEntity.setKrpanoPath("D:\\krpano-1.19-pr14\\krpano-1.19-pr14");  
        krEntity.setFileName("201803291619460544");
        ExecuteMethod.executeKrpanoExe(krEntity);  
          
    }  
      
      
    /** 
     * 执行krpano应用程序进行输出,输出地址为：sourcePath\\vtour 
     * @param krpanoPath 
     * @param sourcePath 
     * @throws InterruptedException  
     */  
    public static void executeKrpanoExe(KrpanoEntity krEntity) throws Exception{  
       File sourceFile = new File(krEntity.getSourcePath());  
       if(!sourceFile.exists()) return ;  
       String ex = "krpanotools64.exe makepano -config=\\templates\\normal.config "  
               + krEntity.getSourcePath() + File.separator + "*.jpg";  
       Runtime runtime = Runtime.getRuntime();  
       Process p = null;  
       try {  
           p = runtime.exec("cmd /c start " + krEntity.getKrpanoPath() + File.separator + ex);  
           p.waitFor();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }finally{  
//           InputStream is1 =  p.getInputStream();  
//            new Thread() {  
//                public void run() {  
//                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));  
//                    try {  
//                        String line1 = null;  
//                        while ((line1 = br1.readLine()) != null) {  
//                            if (line1 != null) {  
//                            }  
//                        }  
//                        is1.close();  
//                        ExecuteMethod em = new ExecuteMethod();  
//                        UnitedMethod um = new UnitedMethod();  
//                        //复制文件
//                        em.copyVtourVrFilesAndPreview(krEntity.getSourcePath(),krEntity.getTargetPath(),krEntity.getFileName());  
//                        //递归删除临时文件夹  
//                        um.delFile(krEntity.getSourcePath() + File.separator + KrpanoContants.VTOUR_NAME);  
//                        try {  
//                            um.updateVtourTitle(krEntity.getTargetPath(), krEntity.getTitle());  
//                            um.updateVtourSceneTitle(krEntity.getTargetPath(), krEntity.getSceneTitle());  
//                            um.updateVtourSkinAddr(krEntity.getTargetPath());  
//                            um.addLoadingAnimation(krEntity.getTargetPath(), krEntity.getLoadingPath());  
//                            um.addMusicElement(krEntity.getTargetPath(), krEntity.getMusicPath());  
//                            um.addNadirLogo(krEntity.getTargetPath(), krEntity.getLogoPath());  
//                        } catch (Exception e) {  
//                            e.printStackTrace();  
//                        }  
//  
//                    } catch (IOException e) {  
//                        e.printStackTrace();  
//                    }  
//                }  
//            }.start();  
           p.destroy();  
       }  
    }  
      
    /** 
     * 需要拷贝3个文件，如：tour.js,tour.swf,tour.xml，还有panos文件夹下的所有文件内容 
     * @param sourcePath 
     * @param targetPath 
     * to 根据需要拷贝panos下vr 文件夹 和 预览图
     */  
    private void copyVtourFiles(String sourcePath,String targetPath){  
        UnitedMethod um = new UnitedMethod();  
        File targetFile = new File(targetPath);  
        if(!targetFile.exists()) targetFile.mkdir();  
        File sourceFile = new File(sourcePath);  
        if(!sourceFile.exists()) return ;  
        try {  
            um.copyFile(sourcePath + File.separator + KrpanoContants.TOUR_XML_NAME, targetPath);  
            um.copyFile(sourcePath + File.separator + KrpanoContants.TOUR_JS_NAME, targetPath);  
            um.copyFile(sourcePath + File.separator + KrpanoContants.TOUR_SWF_NAME, targetPath);  
            um.copyFolder(sourcePath + File.separator + KrpanoContants.PANOS_FOLDER_NANME, targetPath + File.separator + "panos");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
    
    /**
     * to 根据需要拷贝panos下vr 文件夹 和 预览图
     * @param sourcePath
     * @param targetPath
     * @author wun
     */
    private void copyVtourVrFilesAndPreview(String sourcePath,String targetPath,String imageName){  
        UnitedMethod um = new UnitedMethod();  
        File targetFile = new File(targetPath);  
        if(!targetFile.exists()) targetFile.mkdir();  
        File sourceFile = new File(sourcePath);  
        if(!sourceFile.exists()) return ;  
        try { 
        	//copy vr 
        	String vr = sourcePath + File.separator + KrpanoContants.PANOS_FOLDER_NANME + File.separator
        			    + imageName + KrpanoContants.PANOS_IMAGE_FOLDER_SUFFIX + File.separator
        			    + KrpanoContants.PANOS_VR_FOLDER_NAME;
        	um.copyFolder(vr, targetPath + File.separator + imageName);  
        	
        	//copy preview
        	String preview =  sourcePath + File.separator + KrpanoContants.PANOS_FOLDER_NANME + File.separator
    			    + imageName + KrpanoContants.PANOS_IMAGE_FOLDER_SUFFIX + File.separator + KrpanoContants.PANOS_VR_PREVIEW;
        	um.copyFile(preview, targetPath + File.separator + imageName);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
