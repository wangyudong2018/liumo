package com.yiyun.utils;

import org.apache.commons.io.output.FileWriterWithEncoding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * csv导入导出
 * @Title
 * @Description
 * @author bin
 * @createDate 2016年6月3日 下午4:11:08
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class CSVUtil {
    
    /**
     * 导出
     *
     * @param dataList 数据
     * @return
     */
    public static void exportCsv(List<String> dataList, HttpServletRequest request,HttpServletResponse httpServletResponse){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
	    String fileName = "渠道统计-";
	    fileName += dateFormat.format(new Date());
	    fileName += ".csv";
	    BufferedInputStream bis = null;
	    BufferedOutputStream out = null;
	    try { 
	    	fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");// 解决中文
		    httpServletResponse.setContentType("application/octet-stream");
		    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		    String path = System.getProperty("java.io.tmpdir") + "\\tmp.csv";
		    File file = new File(path);
		    FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"UTF-8");
		    BufferedWriter bw = new BufferedWriter(fwwe);
		    if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
		    bw.close();
		    fwwe.close();
	        bis = new BufferedInputStream(new FileInputStream(file));
	        out = new BufferedOutputStream(httpServletResponse.getOutputStream());
	        byte[] buff = new byte[2048];
	        while (true) {
	          int bytesRead;
	          if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
	              break;
	          }
	          out.write(buff, 0, bytesRead);
	        }
	        file.deleteOnExit();
	    }
	    catch (IOException e) {
	    	
	    }
	    finally{
	        try {
	            if(bis != null){
	                bis.close();
	            }
	            if(out != null){
	                out.flush();
	                out.close();
	            }
	        }
	        catch (IOException e) {
	        }
	    }
    }
    /**
     * 导出
     * 
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
    /**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String> importCsv(File file){
        List<String> dataList=new ArrayList<String>();
        
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new FileReader(file));
            String line = ""; 
            while ((line = br.readLine()) != null) { 
                dataList.add(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return dataList;
    }
}