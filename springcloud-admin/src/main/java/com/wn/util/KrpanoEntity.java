package com.wn.util;

public class KrpanoEntity {
	 /** 
     * 总标题 
     */  
    private String title;  
    /** 
     * 场景标题 
     */  
    private String[] sceneTitle;  
    /** 
     * krpano exe执行文件地址 
     */  
    private String krpanoPath;  
    /** 
     * 源文件地址 
     */  
    private String sourcePath;  
    /** 
     * 目标文件夹 
     */  
    private String targetPath;  
    /** 
     * 载入动画地址 
     */  
    private String loadingPath;  
    /** 
     * 音乐文件地址 
     */  
    private String musicPath;  
    /** 
     * 底部logo文件地址 
     */  
    private String logoPath;  
    
    /**
     * 文件名
     */
    private String fileName;
  
    public String getKrpanoPath() {  
        return krpanoPath;  
    }  
  
    public String getSourcePath() {  
        return sourcePath;  
    }  
  
    public String getTargetPath() {  
        return targetPath;  
    }  
  
    public String getLoadingPath() {  
        return loadingPath;  
    }  
  
    public String getMusicPath() {  
        return musicPath;  
    }  
  
    public String getLogoPath() {  
        return logoPath;  
    }  
  
    public void setKrpanoPath(String krpanoPath) {  
        this.krpanoPath = krpanoPath;  
    }  
  
    public void setSourcePath(String sourcePath) {  
        this.sourcePath = sourcePath;  
    }  
  
    public void setTargetPath(String targetPath) {  
        this.targetPath = targetPath;  
    }  
  
    public void setLoadingPath(String loadingPath) {  
        this.loadingPath = loadingPath;  
    }  
  
    public void setMusicPath(String musicPath) {  
        this.musicPath = musicPath;  
    }  
  
    public void setLogoPath(String logoPath) {  
        this.logoPath = logoPath;  
    }  
  
    public String getTitle() {  
        return title;  
    }  
  
    public String[] getSceneTitle() {  
        return sceneTitle;  
    }  
  
    public void setTitle(String title) {  
        this.title = title;  
    }  
  
    public void setSceneTitle(String[] sceneTitle) {  
        this.sceneTitle = sceneTitle;  
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}  
}
