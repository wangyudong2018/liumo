package com.yiyun.utils;


import java.io.*;
import java.net.*;
import java.util.Map;

public class GetDataUtil {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static final String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String getOrPost(String strUrl, Map params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	 //将map型转为请求参数型
   public static String urlencode(Map<String,String> data) {
       StringBuilder sb = new StringBuilder();
       for (Map.Entry i : data.entrySet()) {
           try {
               sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
       }
       return sb.toString();
   }
	
	 public static final byte[] input2byte(InputStream inStream)  
	            throws IOException {  
	        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
	        byte[] buff = new byte[100];  
	        int rc = 0;  
	        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
	            swapStream.write(buff, 0, rc);  
	        }  
	        byte[] in2b = swapStream.toByteArray();  
	        return in2b;  
	    }
	public static String sendPostData(String path, String item) {
		ResultBean resultBean = null;
		String result ="";
		try {
			String encoding="UTF-8";
			byte[] data =item.getBytes(encoding);
	        //byte[] data = item.getBytes(encoding);
	        URL url =new URL(path);
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset="+ encoding);
	        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	        conn.setConnectTimeout(5*60*1000);//5分钟超时
	        OutputStream outStream = conn.getOutputStream();
	        outStream.write(data);
	        outStream.flush();
	        outStream.close();
	        if(conn.getResponseCode()==200){
	            InputStream inStream = conn.getInputStream();
	            byte[] retData = input2byte(inStream);
	            result = new String(retData, "UTF-8");
	            return result;
	        }else{
	        	resultBean=new ResultBean("-1", "访问失败", "");
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
			result="-2";//url错误
			resultBean=new ResultBean(result, "访问url错误", "");
		} catch (ProtocolException e) {
			result="-3";//数据过大
			resultBean=new ResultBean(result, "访问数据过大", "");
		} catch (UnsupportedEncodingException e) {
			result="-4";//编码异常
			resultBean=new ResultBean(result, "访问编码异常", "");
		} catch (IOException e) {
			result="-5";//流传输异常
			resultBean=new ResultBean(result, "访问流传输异常", "");
		} catch (Exception e) {
			e.printStackTrace();
			result="-1";//其他异常
			resultBean=new ResultBean(result, "访问其他异常", "");
		}
		return JsonPluginsUtil.beanToJson(resultBean);
	}  
	public static void main(String[] args) {
		sendPostData("http://localhost:8080/tp/push","sssssss");
		
	}
}
