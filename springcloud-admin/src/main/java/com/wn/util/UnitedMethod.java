package com.wn.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 全景图 文件操作方法类
 * @author wun
 * TODO
 * 2018年3月29日
 */
public class UnitedMethod {
	public boolean copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		if (!oldFile.exists())
			return false;

		File newFile = new File(newPath);
		// 判断是否有这个文件有不管没有创建
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		fileCopy(oldFile.getPath(), newPath + File.separator + oldFile.getName());// 继续调用复制方法
		// 递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
		return true;

	}

	/**
	 * 复制文件或者文件夹下的所有内容
	 * 
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public boolean copyFolder(String oldPath, String newPath) throws IOException {

		File oldFile = new File(oldPath);
		if (!oldFile.exists())
			return false;

		File newFile = new File(newPath);
		// 判断是否有这个文件有不管没有创建
		if (!newFile.exists()) {
			newFile.mkdirs();
		}

		// 遍历文件及文件夹
		for (File file : oldFile.listFiles()) {
			if (file.isFile()) {
				// 文件
				fileCopy(file.getPath(), newPath + File.separator + file.getName()); // 调用文件拷贝的方法
			} else if (file.isDirectory()) {
				// 文件夹
				copyFolder(file.getPath(), newPath + File.separator + file.getName());// 继续调用复制方法
				// 递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
			}
		}

		return true;
	}

	private void fileCopy(String src, String des) throws IOException {
		// io流固定格式
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
		int i = -1;// 记录获取长度
		byte[] bt = new byte[2014];// 缓冲区
		while ((i = bis.read(bt)) != -1) {
			bos.write(bt, 0, i);
		}
		bis.close();
		bos.close();
		// 关闭流
	}

	/**
	 * 删除文件或者文件夹下的所有内容
	 * 
	 * @param src
	 */
	public void delFile(String src) {
		File file = new File(src);
		if (!file.exists())
			return;
		delDir(file);
	}

	/**
	 * 递归删除文件
	 * 
	 * @param f
	 */
	private static void delDir(File f) {
		// 判断是否是一个目录, 不是的话跳过, 直接删除; 如果是一个目录, 先将其内容清空.
		if (f.isDirectory()) {
			// 获取子文件/目录
			File[] subFiles = f.listFiles();
			// 遍历该目录
			for (File subFile : subFiles) {
				// 递归调用删除该文件: 如果这是一个空目录或文件, 一次递归就可删除. 如果这是一个非空目录, 多次
				// 递归清空其内容后再删除
				delDir(subFile);
			}
		}
		// 删除空目录或文件
		f.delete();
	}

	public Document readeXMLDemo(String sourcePath) throws DocumentException {

		File file = new File(sourcePath);
		if (!file.exists())
			return null;

		// 创建saxReader对象
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象
		Document document = reader.read(file);

		return document;
		// 遍历所有的元素节点
		// listNodes(node);
	}

	/**
	 * 修改vr标题
	 * 
	 * @param fileFolder
	 * @param title
	 * @throws Exception
	 */
	public void updateVtourTitle(String fileFolder, String title) throws Exception {

		if (null == title || StringUtils.isEmpty(title))
			return;

		String tour_filePath = fileFolder + File.separator + KrpanoContants.TOUR_NAME;
		Document tour_doc = this.readeXMLDemo(tour_filePath);
		if (null == tour_doc)
			return;

		Element tour_root = tour_doc.getRootElement();
		Attribute title_attr = tour_root.attribute("title");
		title_attr.setValue(title);
		this.writer(tour_doc, tour_filePath);
	}

	/**
	 * 修改每个场景的标题值
	 * 
	 * @param fileFolder
	 * @param sceneTitles
	 * @throws Exception
	 */
	public void updateVtourSceneTitle(String fileFolder, String[] sceneTitles) throws Exception {

		if (null == sceneTitles || sceneTitles.length == 0)
			return;
		String tour_filePath = fileFolder + File.separator + KrpanoContants.TOUR_NAME;
		Document tour_doc = this.readeXMLDemo(tour_filePath);
		if (null == tour_doc)
			return;

		Element tour_root = tour_doc.getRootElement();

		List<Element> sceneTitle_list = tour_root.elements("scene");
		for (int i = 0, size = sceneTitle_list.size(); i < size; i++) {
			sceneTitle_list.get(i).attribute("title").setValue(sceneTitles[i]);
		}
		this.writer(tour_doc, tour_filePath);

	}

	/**
	 * 修改vtour.xml中的vtourskin路径地址
	 * 
	 * @param fileFolder
	 * @throws Exception
	 */
	public void updateVtourSkinAddr(String fileFolder) throws Exception {
		String tour_filePath = fileFolder + File.separator + KrpanoContants.TOUR_NAME;
		Document tour_doc = this.readeXMLDemo(tour_filePath);
		if (null == tour_doc)
			return;

		Element tour_root = tour_doc.getRootElement();
		Element vtourskin_element = tour_root.element("include");
		Attribute attr = vtourskin_element.attribute("url");
		attr.setValue("../" + attr.getValue());
		this.writer(tour_doc, tour_filePath);
	}

	/**
	 * 增加vr背景音乐和控制按钮
	 * 
	 * @param fileFolder
	 * @param musicPath
	 * @throws Exception
	 */
	public void addMusicElement(String fileFolder, String musicPath) throws Exception {

		// tour.xml文件解析的根节点
		String tour_filePath = fileFolder + File.separator + KrpanoContants.TOUR_NAME;
		Document tour_doc = this.readeXMLDemo(tour_filePath);
		if (null == tour_doc)
			return;

		File music_file = new File(musicPath);
		if (!music_file.exists())
			return;

		Element tour_root = tour_doc.getRootElement();
		Element plugin_element = tour_root.addElement("plugin");
		plugin_element.addAttribute("name", "soundinterface");
		plugin_element.addAttribute("url.flash", "%HTMLPATH%/plugins/soundinterface.swf");
		plugin_element.addAttribute("url.html5", "%HTMLPATH%/plugins/soundinterface.js");
		plugin_element.addAttribute("rootpath", "");
		plugin_element.addAttribute("preload", "true");
		plugin_element.addAttribute("keep", "true");

		// 拷贝声音文件到目录下

		this.copyFile(musicPath, fileFolder);
		Element action_element = tour_root.addElement("action");
		action_element.addAttribute("name", "bgsnd_action");
		action_element.addAttribute("autorun", "onstart");
		action_element.addText("playsound(bgsnd, '%SWFPATH%/" + music_file.getName() + "', 0);");

		this.writer(tour_doc, tour_filePath);

		// skin/vtourskin.xml文件解析的根节点,增加声音控制按钮
		// String vtourskin_filePath =fileFolder + File.separator +
		// VTOURSKIN_NAME;
		// Document vtourskin_doc = this.readeXMLDemo(vtourskin_filePath);
		// if(null == vtourskin_doc) return;
		// Element vtourskin_root = vtourskin_doc.getRootElement();
		// //查找bar_button节点
		// List<Element> list_1 = vtourskin_root.elements();
		// Element skin_layer_element = null;
		// for(Element ee : list_1){
		// if(null != ee.attributeValue("name") &&
		// "skin_layer".equals(ee.attributeValue("name").toString())){
		// skin_layer_element = ee;
		// break;
		// }
		// }
		// List<Element> list_2 = skin_layer_element.elements();
		// Element control_bar_element = null;
		// for(Element ee : list_2){
		// if(null != ee.attributeValue("name") &&
		// "skin_control_bar".equals(ee.attributeValue("name").toString())){
		// control_bar_element = ee;
		// break;
		// }
		// }
		//
		// Element bar_button_element = control_bar_element.addElement("layer");
		// bar_button_element.addAttribute("name", "skin_btn_sound");
		// bar_button_element.addAttribute("style", "skin_base|skin_glow");
		// bar_button_element.addAttribute("crop", "64|704|64|64");
		// bar_button_element.addAttribute("align", "right");
		// bar_button_element.addAttribute("x", "130");
		// bar_button_element.addAttribute("y", "0");
		// bar_button_element.addAttribute("scale", "0.5");
		// bar_button_element.addAttribute("onclick",
		// "pausesoundtoggle(bgsnd);switch(crop,64|704|40|64,64|704|64|64);switch(alpha,1,0.25);switch(ox,0,-12)");
		// this.writer(vtourskin_doc, vtourskin_filePath);
	}

	/**
	 * 新增底部logo图片
	 * 
	 * @param fileFolder
	 * @param logoFloder
	 *            存放logo和配置文件的文件夹目录
	 * @throws Exception
	 */
	public void addNadirLogo(String fileFolder, String logoFloder) throws Exception {
		File file = new File(fileFolder);
		if (!file.exists())
			return;

		File logoFile = new File(fileFolder + File.separator + KrpanoContants.LOGO_NAME);
		if (!logoFile.exists())
			logoFile.mkdir();
		// 拷贝logo文件到vr目录下
		this.copyFolder(logoFloder, logoFile.getPath());

		// tour.xml文件解析的根节点
		String tour_filePath = fileFolder + File.separator + KrpanoContants.TOUR_NAME;
		Document tour_doc = this.readeXMLDemo(tour_filePath);
		if (null == tour_doc)
			return;

		Element tour_root = tour_doc.getRootElement();
		Element plugin_element = tour_root.addElement("include");
		plugin_element.addAttribute("url", "logo/nadir-logo.xml");

		this.writer(tour_doc, tour_filePath);
	}

	/**
	 * 增加进度条动画
	 * 
	 * @param fileFolder
	 * @param loadingFloder
	 * @throws Exception
	 */
	public void addLoadingAnimation(String fileFolder, String loadingFloder) throws Exception {
		File file = new File(fileFolder);
		if (!file.exists())
			return;

		File logoFile = new File(fileFolder + File.separator + KrpanoContants.LOADING_NAME);
		if (!logoFile.exists())
			logoFile.mkdir();
		// 拷贝logo文件到vr目录下
		this.copyFolder(loadingFloder, logoFile.getPath());

		// tour.xml文件解析的根节点
		String tour_filePath = fileFolder + File.separator + KrpanoContants.TOUR_NAME;
		Document tour_doc = this.readeXMLDemo(tour_filePath);
		if (null == tour_doc)
			return;

		Element tour_root = tour_doc.getRootElement();

		Element animation_element = tour_root.addElement("include");
		animation_element.addAttribute("url", "loading/loadinganimation.xml");

		Element text_element = tour_root.addElement("include");
		text_element.addAttribute("url", "loading/loadingpercenttext.xml");

		Element bar_element = tour_root.addElement("include");
		bar_element.addAttribute("url", "loading/loadingbar.xml");

		this.writer(tour_doc, tour_filePath);
	}

	/**
	 * 把document对象写入新的文件
	 * 
	 * @param document
	 * @throws Exception
	 */
	public void writer(Document document, String filePath) throws Exception {
		// 紧凑的格式
		// OutputFormat format = OutputFormat.createCompactFormat();
		// 排版缩进的格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置编码
		format.setEncoding("UTF-8");
		// 创建XMLWriter对象,指定了写出文件及编码格式
		// XMLWriter writer = new XMLWriter(new FileWriter(new
		// File("src//a.xml")),format);
		XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8"),
				format);
		// 写入
		writer.write(document);
		// 立即写入
		writer.flush();
		// 关闭操作
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		UnitedMethod um = new UnitedMethod();

		String filePath = "E:\\works\\krpano\\test\\target";

		// um.addMusicElement(filePath, "E:\\one.mp3");

		// um.addNadirLogo(filePath,
		// "E:\\works\\krpano\\krpano_test\\vtour\\logo");

		// um.addLoadingAnimation(filePath,
		// "E:\\works\\krpano\\krpano_test\\vtour\\loading");

		// um.updateVtourSkinAddr(filePath);

		um.updateVtourSceneTitle(filePath, new String[] { "千年香榧-1", "千年香榧-2" });
	}

}
