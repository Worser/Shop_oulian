package tst.project.utils.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tst.project.bean.member.MemberBean;
import tst.project.bean.order.OrderGoodsBean;

public class RefuseOrderForMember extends Thread
{
  private MemberBean memberBean2 = null;
  private List<OrderGoodsBean> orderGoodsBean1 = null;

  public RefuseOrderForMember(MemberBean memberBean2, List<OrderGoodsBean> orderGoodsBean1)
  {
    this.memberBean2 = memberBean2;
    this.orderGoodsBean1 = orderGoodsBean1;
  }

  public void run()
  {
    Properties p = new Properties();

    p.setProperty("mail.host", "smtp.qq.com");

    p.setProperty("mail.smtp.auth", "true");

    p.setProperty("mail.transport.protocol", "smtp");

    MailSSLSocketFactory sf = null;
    try {
      sf = new MailSSLSocketFactory();
    }
    catch (GeneralSecurityException e1) {
      e1.printStackTrace();
    }
    sf.setTrustAllHosts(true);
    p.put("mail.smtp.ssl.enable", "true");
    p.put("mail.smtp.ssl.socketFactory", sf);

    Session session = Session.getDefaultInstance(p, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        PasswordAuthentication pa = new PasswordAuthentication(
          "592370098@qq.com", "xbmgartlrmsdbchh");

        return pa;
      }

    });
    try
    {
      MimeMessage msg = new MimeMessage(session);

      msg.setFrom(new InternetAddress("592370098@qq.com"));

      msg.setRecipient(Message.RecipientType.TO, 
        new InternetAddress(this.memberBean2.getEmail_account()));

      msg.setSubject(this.memberBean2.getCompany_name() + ",订单提醒！！");

      StringBuilder sbd = new StringBuilder();
      sbd.append("【ACH】亲爱的" + this.memberBean2.getCompany_name() + "，您好!<br/>");
      sbd.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;非常抱歉，您购买的商品");
      for (int i = 0; i < this.orderGoodsBean1.size(); i++) {
        sbd.append("【" + ((OrderGoodsBean)this.orderGoodsBean1.get(i)).getGoods_name() + "】");
      }
      sbd.append("由于卖家的原因，拒绝了您的订单。您可以重新登陆平台进行采购，祝您采购顺利！<br/><br/><br/>");
      sbd.append("------------------------------------------------<br/>");
      sbd.append("<img src='images/home/ACH.png'/><br/>");
      sbd.append("连接之家--专注于连接器的平台！<br/>");
      sbd.append("电话：021-64809788 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传真:021-64809788<br/>");

      sbd.append("手机:86-13701969912<br/>");
      sbd.append("ACH实时采购&销售QQ群:25039782<br/>");
      sbd.append("平台网址:www.automotive-connector.com<br/>");
      sbd.append("邮箱:penghui.li@automotive-connector.onaliyun.com<br/>");
      sbd.append("地址:上海市闵行区春申路1985弄15号福克斯大厦6幢116室");
      msg.setContent(sbd.toString(), "text/html;charset=utf-8");

      Transport.send(msg);
      System.out.println("给" + this.memberBean2.getEmail_account() + "发送邮件成功。");
    } catch (AddressException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}