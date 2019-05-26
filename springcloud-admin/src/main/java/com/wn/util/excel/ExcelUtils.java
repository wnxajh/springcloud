package com.wn.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wn.util.DateUtils;

/**
 * 反射方式excel导出
 * 
 * @author wnxaj
 *
 */
public class ExcelUtils {

	public static final String mimeType = "application/msexcel; charset=UTF-8";

	/**
	 * 多个sheet
	 * @param req
	 * @param res
	 * @param fileName
	 * @param sheetName
	 * @param sheetlist
	 * @throws UnsupportedEncodingException
	 * @throws InstantiationException
	 * @author wunan
	 * @version 2019年1月29日 下午4:36:36
	 */
	public static void exportExcel(HttpServletRequest req, HttpServletResponse res, String fileName, String[] sheetName,
			List<?>... sheetlist) throws UnsupportedEncodingException, InstantiationException {
		exportExcel(req, res, fileName, mimeType, sheetName, sheetlist);
	}

	public static void exportExcel(HttpServletRequest req, HttpServletResponse res, String fileName, String mimeType,
			String[] sheetName, List<?>... sheetlist) throws UnsupportedEncodingException, InstantiationException {
		// 初始化头信息
		setResponseHeader(req, res, mimeType, fileName);
		// 初始化工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		try {
			for (int n = 0; n < sheetName.length; n++) {
				Sheet sheet = workbook.createSheet(sheetName[n]);
				// 获取目标参数
				List<?> objList = sheetlist[n];
				// 列头名称列表
				List<String> titleList = new ArrayList<String>();
				List<Integer> colWidthList = new ArrayList<>();
				if(objList == null || objList.size() == 0)continue;
				Object obj = objList.get(0);
				// 获取标题
				getTitlesAndColWidth(obj, titleList, colWidthList);
				// 初始化标题行
				initTitleValueAndColWidth(sheet, workbook, titleList, colWidthList);
				int startRow = 1;
				int startCol = 0;
				for (Object object : objList) {
					AtomicInteger mergeNum = new AtomicInteger(0);
					createRow(object, sheet, startRow, startCol, true, null, mergeNum);
					if (mergeNum.intValue() > 1) {
						Class<?> cls = object.getClass();
						Field[] fields = cls.getDeclaredFields();
						AtomicInteger colNum = new AtomicInteger(0);
						for (Field field : fields) {
							ExcelField excelField = field.getAnnotation(ExcelField.class);
							if (excelField == null)
								continue;
							if (excelField.isList()) {
								handelDeptList(object, field, sheet, startRow, startCol, colNum, mergeNum);
							}
							ExcelMergeColum excelMergeColum = field.getAnnotation(ExcelMergeColum.class);
							if (excelMergeColum == null)
								continue;
							addMergedRegion(sheet, startRow, (startRow - 1) + mergeNum.intValue(), colNum.intValue(), colNum.intValue());
							colNum.incrementAndGet();
						}
					}
					startRow += mergeNum.intValue() == 0 ? 1 : mergeNum.intValue();
				}
			}
			OutputStream ouputStream;
			ouputStream = res.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 递归处理合并格
	 * @param object
	 * @param field
	 * @param sheet
	 * @param startRow
	 * @param startCol
	 * @param colNum
	 * @param mergeNum
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @author wunan
	 * @version 2019年2月26日 下午8:21:24
	 */
	public static void handelDeptList(Object object,Field field,Sheet sheet,Integer startRow
			,Integer startCol,AtomicInteger colNum,AtomicInteger mergeNum) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> cls = object.getClass();	
		String fieldName = field.getName();
		String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method getMethod = cls.getMethod(getMethodName, new Class[] {});
		List<?> targetList = (List<?>) getMethod.invoke(object, new Object[] {});
		if (targetList == null || targetList.size() == 0) return;
		Object result = targetList.get(0);
		Class<?> childCls = result.getClass();
		Field[] childFields = childCls.getDeclaredFields();
		for (Field field2 : childFields) {
			ExcelField exField = field2.getAnnotation(ExcelField.class);
			ExcelMergeColum excelMergeColum = field2.getAnnotation(ExcelMergeColum.class);
			if (exField != null && exField.isList()) {
				String curfieldName = field2.getName();
				String curGetMethodName = "get" + curfieldName.substring(0, 1).toUpperCase() + curfieldName.substring(1);
				Method curGetMethod = childCls.getMethod(curGetMethodName, new Class[] {});
				List<?> curTargetList = (List<?>) curGetMethod.invoke(result, new Object[] {});
				if (curTargetList == null || curTargetList.size() == 0)
					continue;
				handelDeptList(result, field2, sheet, startRow, startCol, colNum, mergeNum);
			}else if(exField != null) {
				if(excelMergeColum != null && excelMergeColum.merge()) {
					addMergedRegion(sheet, startRow, (startRow - 1) + mergeNum.intValue(), colNum.intValue(), colNum.intValue());
				}
				colNum.incrementAndGet();
			}
		}
	}
	

	public static void exportExcel(HttpServletRequest req, HttpServletResponse res, String fileName, String sheetName,
			List<?> objList) throws UnsupportedEncodingException, InstantiationException {
		exportExcel(req, res, fileName, mimeType, sheetName, objList);
	}

	public static void exportExcel(HttpServletRequest req, HttpServletResponse res, String fileName, String mimeType,
			String sheetName, List<?> list) throws UnsupportedEncodingException, InstantiationException {
		// 初始化头信息
		setResponseHeader(req, res, mimeType, fileName);
		// 初始化工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		Sheet sheet = workbook.createSheet(sheetName);
		// 获取目标参数
		List<?> objList = list;
		// 列头名称列表
		List<String> titleList = new ArrayList<String>();
		List<Integer> colWidthList = new ArrayList<>();
		Object obj = objList == null ? null : objList.get(0);
		try {
			// 获取标题
			getTitlesAndColWidth(obj, titleList, colWidthList);
			// 初始化标题行
			initTitleValueAndColWidth(sheet, workbook, titleList, colWidthList);
			// 数据行
			int startRow = 1;
			int startCol = 0;
			
			for (Object object : objList) {
				AtomicInteger mergeNum = new AtomicInteger(0);
				createRow(object, sheet, startRow, startCol, true, null, mergeNum);
				if (mergeNum.intValue() > 1) {
					Class<?> cls = object.getClass();
					Field[] fields = cls.getDeclaredFields();
					int colNum = 0;
					for (Field field : fields) {
						ExcelField excelField = field.getAnnotation(ExcelField.class);
						if (excelField == null)
							continue;
						if (excelField.isList()) {
							String fieldName = field.getName();
							String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
									+ fieldName.substring(1);
							Method getMethod = cls.getMethod(getMethodName, new Class[] {});
							List<?> targetList = (List<?>) getMethod.invoke(object, new Object[] {});
							if (targetList == null || targetList.size() == 0)
								continue;
							Object result = targetList.get(0);
							Class<?> childCls = result.getClass();
							Field[] childFields = childCls.getDeclaredFields();
							for (Field field2 : childFields) {
								if (field2.getAnnotation(ExcelField.class) != null)
									colNum += 1;
							}
						}
						ExcelMergeColum excelMergeColum = field.getAnnotation(ExcelMergeColum.class);
						if (excelMergeColum == null)
							continue;
						addMergedRegion(sheet, startRow, (startRow - 1) + mergeNum.intValue(), colNum, colNum);
						colNum++;
					}
				}
				startRow += mergeNum.intValue() == 0 ? 1 : mergeNum.intValue();
			}
			OutputStream ouputStream;
			ouputStream = res.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param startRow
	 * @param endRow
	 * @param startCol
	 * @param endCol
	 * @author wunan
	 * @version 2019年1月18日 下午5:11:44
	 */
	public static void addMergedRegion(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
		CellRangeAddress region = new CellRangeAddress((short) startRow, (short) endRow, (short) startCol,
				(short) endCol);
		sheet.addMergedRegion(region);
	}

	/**
	 * 创建excel行
	 * 
	 * @param object
	 * @param sheet
	 * @param originStartRow
	 * @param startCol
	 * @param newRow
	 * @param oldRow
	 * @param mergeNum
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @author wunan
	 * @version 2019年1月18日 下午5:11:55
	 */
	public static Map<String, Integer> createRow(Object object, Sheet sheet, int originStartRow, int startCol,
			boolean newRow, Row oldRow, AtomicInteger mergeNum) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Row curRow = oldRow;
		if (newRow)
			curRow = sheet.createRow(originStartRow);
		Class<?> cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		int colnum = startCol;
		for (Field field : fields) {
			ExcelField excelField = field.getAnnotation(ExcelField.class);
			if (excelField == null)
				continue;
			String fieldName = field.getName();
			String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method getMethod = cls.getMethod(getMethodName, new Class[] {});
			Object target = getMethod.invoke(object, new Object[] {});
			if (excelField.isList() && StringUtils.isEmpty(excelField.title())) {
				List<?> targetList = (List<?>) target;
				if (targetList == null)
					continue;
				int curSize = targetList.size();
				if (curSize > mergeNum.intValue())
					mergeNum.set(curSize);
				for (int a = 0; a < targetList.size(); a++) {
					Object curObj = targetList.get(a);
					map = createRow(curObj, sheet, originStartRow++, colnum, a == 0 ? false : true, curRow,
							mergeNum);
					if (a == targetList.size() - 1)
						colnum = map.get("colnum");
				}
			} else if (StringUtils.isNotEmpty(excelField.title())) {
				Cell cell = curRow.createCell(colnum);
				ExcelEnum excelEnum = field.getAnnotation(ExcelEnum.class);
				if (excelEnum != null) {
					setEnumValue(cell, target, field);
				} else {
					setCellValue(cell, target);
				}
				colnum++;
			}
		}
		map.put("colnum", colnum);
		return map;
	}

	/**
	 * 设置表格值
	 * 
	 * @param cell
	 * @param target
	 * @author wunan
	 * @version 2019年1月18日 下午3:57:32
	 */
	public static void setCellValue(Cell cell, Object target) {
		if(target == null) {
			cell.setCellValue("");
			return;
		}
		if (target instanceof Integer) {
			cell.setCellValue((Integer) target);
		} else if (target instanceof String) {
			cell.setCellValue(target == null ? "" : target.toString());
		} else if (target instanceof Date) {
			cell.setCellValue(target == null ? "" : DateUtils.convertDate((Date) target));
		} else if (target instanceof Double) {
			cell.setCellValue((Double) target);
		} else if (target instanceof Long) {
			cell.setCellValue((Long) target);
		} else if (target instanceof BigDecimal) {
			cell.setCellValue(((BigDecimal) target).doubleValue());
		}
	}

	/**
	 * 枚举值设置
	 * 
	 * @param cell
	 * @param target
	 * @param field
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @author wunan
	 * @version 2019年1月18日 下午3:54:29
	 */
	public static void setEnumValue(Cell cell, Object target, Field field) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(target == null) {
			cell.setCellValue("");
			return;
		}
		Class<?> typeClazz = null;
		if (target instanceof String) {
			typeClazz = String.class;
		} else if (target instanceof Integer) {
			typeClazz = Integer.class;
		} else if (target instanceof Short) {
			typeClazz = Short.class;
		} else if (target instanceof Date) {
			typeClazz = Date.class;
		} else if (target instanceof Double) {
			typeClazz = Double.class;
		} else if (target instanceof Long) {
			typeClazz = Long.class;
		} else if (target instanceof BigDecimal) {
			typeClazz = BigDecimal.class;
		}
		ExcelEnum excelEnum = field.getAnnotation(ExcelEnum.class);
		Class<?> enumClazz = excelEnum.enumClass();
		String methodName = excelEnum.methodName();
		Method enumMethod = enumClazz.getMethod(methodName, new Class[] { typeClazz });
		Object value = enumMethod.invoke(null, new Object[] { target });
		cell.setCellValue((String) value);
	}

	/**
	 * 获取标题
	 * @param object 目标对象
	 * @param titleList 标题列表
	 * @param colWidthList 列宽列表
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @author wunan
	 * @version 2019年1月26日 下午5:56:51
	 */
	public static void getTitlesAndColWidth(Object object,List<String> titleList,List<Integer> colWidthList) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (object == null)return;
		Class<?> cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			ExcelField excelField = field.getAnnotation(ExcelField.class);
			if (excelField == null || !excelField.open())
				continue;
			if (StringUtils.isNotEmpty(excelField.title())) {
				titleList.add(excelField.title());
				colWidthList.add(excelField.colWidth());
			}
			if (!excelField.isList()) continue;
			String fieldName = field.getName();
			String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method getMethod = cls.getMethod(getMethodName, new Class[] {});
			List<?> targetList = (List<?>) getMethod.invoke(object, new Object[] {});
			if (targetList == null || targetList.size() == 0)
				continue;
			Object result = targetList.get(0);
			getTitlesAndColWidth(result,titleList,colWidthList);
		}
	}

	/**
	 * 设置下载请求头和文件名
	 * 
	 * @param request
	 * @param response
	 * @param mimeType
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String mimeType,
			String fileName) throws UnsupportedEncodingException {
		String userAgent = request.getHeader("USER-AGENT");
		String finalFileName;
		if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
			finalFileName = URLEncoder.encode(fileName, "UTF8");
		} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
			finalFileName = new String(fileName.getBytes(), "ISO8859-1");
		} else {
			finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
		}
		response.reset(); // 清空输出流
		response.setContentType(mimeType); // 定义输出类型
		response.setHeader("Content-disposition", "attachment; filename=\"" + finalFileName + "\"");// 设定输出文件头
		response.setCharacterEncoding("UTF-8");
	}

	/**
	 * excel 标题普通样式
	 * 
	 * @param wb
	 * @return
	 * @author wunan
	 * @version 2019年1月18日 下午4:45:46
	 */
	public static XSSFCellStyle getCommonTitleStyle(XSSFWorkbook wb) {
		// 创建字体样式
		XSSFFont font = wb.createFont();
		font.setFontName("Verdana");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) 200);
		font.setColor(HSSFColor.BLACK.index);
		// 创建单元格样式
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置边框
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);// 设置字体
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置为垂直居中
		return style;
	}

	/**
	 * 初始化excel 标题栏
	 * 
	 * @param sheet
	 * @param workbook
	 * @param titleList
	 * @author wunan
	 * @version 2019年1月18日 下午5:16:07
	 */
	public static void initTitleValueAndColWidth(Sheet sheet, XSSFWorkbook workbook, 
			List<String> titleList, List<Integer> colWidthList) {
		Row row = sheet.createRow(0);
		for (int i = 0; i < titleList.size(); i++) {
			sheet.setColumnWidth(i, colWidthList.get(i));
			Cell cell = row.createCell(i);
			XSSFRichTextString text = new XSSFRichTextString(titleList.get(i));
			XSSFCellStyle style = getCommonTitleStyle(workbook);
			cell.setCellStyle(style);
			cell.setCellValue(text);
		}
	}

	/**
	 * 获得普通样式
	 * @return
	 */
	public static XSSFCellStyle getNormalStyle(XSSFWorkbook wb) {
		// 创建字体样式
		XSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeight((short) 200);
		font.setColor(HSSFColor.BLACK.index);
		// 创建单元格样式
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		// 设置边框
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);// 设置字体
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置为垂直居中
		return style;
	}
}
