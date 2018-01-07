package com.sunfund.core.common.utils.Test;

import com.sunfund.core.common.utils.mail.MailUtil;
import com.sunfund.core.common.utils.mail.MimeMessageDTO;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 9douyu on 2017/10/17.
 */
public class mailTest {



        @Test
        public void testSendEmail() {

            // //测试163邮箱
            // 测试qq邮箱
            // 新浪邮箱 13077403326m@sina.cn
            // 139邮箱 13077403326@139.com

            String userName = "15210495787@163.com"; // 用户邮箱地址
            String password = "jj15210495787"; // 密码或者授权码
            String targetAddress = "1191163977@qq.com"; // 接受者邮箱地址

            // 设置邮件内容
            MimeMessageDTO mimeDTO = new MimeMessageDTO();
            mimeDTO.setSentDate(new Date());
            mimeDTO.setSubject("邮件的标题");
            mimeDTO.setText("邮件的内容<a href='http://localhost/user/emai'>请点击" +
                    "</a>"
            );

            // 发送单邮件
            if (MailUtil.sendEmail(userName, password, targetAddress, mimeDTO)) {
                System.out.println("邮件发送成功！");
            } else {
                System.out.println("邮件发送失败!!!");
            }
//		// 发送单邮件(附件)
//		List<String> filepath=new ArrayList<String>();
//		filepath.add("D:/temple.xls");
//		filepath.add("D:/test.xls");
//		if (MailUtil.sendEmailByFile(userName, password, targetAddress, mimeDTO,filepath)) {
//			System.out.println("邮件发送成功！");
//		} else {
//			System.out.println("邮件发送失败!!!");
//		}
            // 群发邮件
            targetAddress = "791120662@qq.com,1466209469@qq.com";
//		if (MailUtil.sendGroupEmail(userName, password, targetAddress, mimeDTO)) {
//			System.out.println("邮件发送成功！");
//		} else {
//			System.out.println("邮件发送失败!!!");
//		}
//		// 群发邮件(附件)
//		if (MailUtil.sendGroupEmailByFile(userName, password, targetAddress, mimeDTO,filepath)) {
//			System.out.println("邮件发送成功！");
//		} else {
//			System.out.println("邮件发送失败!!!");
//		}

        }

    

}
