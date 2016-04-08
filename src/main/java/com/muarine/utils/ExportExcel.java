package com.muarine.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {
	public void exportExcel(String title , Collection<T> dataset, OutputStream out) {
		exportExcel(title, null, dataset, out, "yyyy-MM-dd HH:mm:ss");
	}

	public void exportExcel(String title , String[] headers, Collection<T> dataset,
			OutputStream out) {
		exportExcel(title, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings({"resource"})
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
	    // 声明一个工作薄 POI apache 组件
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 2.2 生成一个表格 工作薄
        XSSFSheet sheet = null;
        // 产生表格标题行
        XSSFRow row = null;
        // 2.4 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        int sheetIndex = 0;
        while (it.hasNext()) {
            index++;
            // 每个Sheet存放最多10000条记录,能被10000整除时新建sheet
            int tmpIndex = index%10000;
            if(index == 1 || tmpIndex == 0){
                // 新建sheet后，将行数置1，因为第0行是标注
                index = 1;
                sheetIndex++;
                // 创建工作簿
                sheet = workbook.createSheet(title + sheetIndex);
                // 设置行高度
                sheet.setDefaultRowHeight((short)15);
                // 创建第一行第一列
                row =sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
                }
            }
            
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                XSSFCell cell = row.createCell(i);
//              cell.setCellStyle(style2);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class<?> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value == null ? "" : value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            XSSFRichTextString richString = new XSSFRichTextString(
                                    textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (IllegalAccessException e) {
                    //FIXME implement me
                } catch (IllegalArgumentException e) {
                    //FIXME implement me
                } catch (InvocationTargetException e) {
                    //FIXME implement me
                } catch (NoSuchMethodException e) {
                    //FIXME implement me
                } catch (SecurityException e) {
                    //FIXME implement me
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		// 测试学生
//		ExportExcel<Student> ex = new ExportExcel<Student>();
//		String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
//		List<Student> dataset = new ArrayList<Student>();
//		dataset.add(new Student(10000001, "张三", 20, true, new Date()));
//		dataset.add(new Student(20000002, "李四", 24, false, new Date()));
//		dataset.add(new Student(30000003, "王五", 22, true, new Date()));
//		// 测试图书
//		ExportExcel<Book> ex2 = new ExportExcel<Book>();
//		String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN",
//				"图书出版社", "封面图片" };
//		List<Book> dataset2 = new ArrayList<Book>();
//		try {
//			// BufferedInputStream bis = new BufferedInputStream(
//			// new FileInputStream("V://book.bmp"));
//			BufferedInputStream bis = new BufferedInputStream(
//					new FileInputStream("/home/book.bmp"));
//			byte[] buf = new byte[bis.available()];
//			while ((bis.read(buf)) != -1) {
//				//
//			}
//			dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
//					"清华出版社", buf));
//			dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
//					"阳光出版社", buf));
//			dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
//					"清华出版社", buf));
//			dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",
//					"清华出版社", buf));
//			dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",
//					"汤春秀出版社", buf));
//			// OutputStream out = new FileOutputStream("E://export2003_a.xls");
//			// OutputStream out2 = new FileOutputStream("E://export2003_b.xls");
//			OutputStream out = new FileOutputStream("/home/export2003_a.xls");
//			OutputStream out2 = new FileOutputStream("/home/export2003_b.xls");
//			ex.exportExcel(headers, dataset, out);
//			ex2.exportExcel(headers2, dataset2, out2);
//			out.close();
//			JOptionPane.showMessageDialog(null, "导出成功!");
//			System.out.println("excel导出成功！");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}