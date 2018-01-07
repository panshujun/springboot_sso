package com.sunfund.biBabel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTest {
    @Test
    public void testAsset(){
        String result = "";
        // 发送请求
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(
                "http://localhost:8181/api/userTest");
        String value = "{\"header\":{\"clientInfo\":\"26200,wapRa,3\",\"faceCode\":\"api.user\",\"interVersion\":\"1.0.5\","
                + "\"userId\":\"110532298138\"},\"body\":{\"userName\":\"admin\"}}";
        try{
//			String data = new String(Base64DesEncrypt.getInstance().encode(
//					value.getBytes(Charsets.UTF_8)));
            // 封装请求参数
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//			formParams.add(new BasicNameValuePair("data",value));
            formParams.add(new BasicNameValuePair("userName","admin"));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                    formParams, "UTF-8");


            httpPost.setEntity(urlEncodedFormEntity);
            HttpResponse response = client.execute(httpPost);

            // 获取响应码
            int status = response.getStatusLine().getStatusCode();
            if (200 == status) {
                // 获取响应内容
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        entity.getContent()));
                String line;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                br.close();
            }else{

            }
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch(HttpHostConnectException e) {
            e.printStackTrace();
            // 链接服务器时被拒绝
            System.out.println("链接服务器时被拒绝");
        } catch (IOException e) {
            e.printStackTrace();
            // 请求超时的处理
            System.out.println("请求超时");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);

        // 释放资源
        if (httpPost.isAborted()) {
            httpPost.abort();
        }
        client.getConnectionManager().shutdown();
    }
}
