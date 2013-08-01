package oliver.app.batch.service;

import static oliver.app.batch.Configuration.getProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Sending main service
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-25 AM8:37
 */
@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private Configuration freemarkerMailConfiguration;

    @PostConstruct
    public void init() {
        mailSender.setUsername(getProperty("batch.mail.username"));
        mailSender.setPassword(getProperty("batch.mail.password"));
        mailSender.setHost(getProperty("batch.mail.host"));
        Properties properties = new Properties();
        // auth required?
        if (getProperty("batch.mail.auth") != null) {
            properties.setProperty("mail.smtp.auth", "true");
        }
        mailSender.setJavaMailProperties(properties);
    }

    /**
     * 
     * send a email
     * 
     * @author lichengwu
     * @created 2012-12-25
     * 
     * @param template
     *            mail template located in /WEB-INF/mail
     * @param data
     *            data for render the template
     */
    public void send(String template, Object data) {

        String templateFile = template.endsWith(".ftl") ? template : template + ".ftl";

        try {
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerMailConfiguration.getTemplate(templateFile), data);
            BufferedReader reader = new BufferedReader(new StringReader(content));
            String line;

            StringBuilder mailFromAddress = new StringBuilder();
            StringBuilder mailFromName = new StringBuilder();
            StringBuilder mailTo = new StringBuilder();
            StringBuilder mailCc = new StringBuilder();
            StringBuilder mailBcc = new StringBuilder();
            StringBuilder mailSubject = new StringBuilder();
            StringBuilder themeTemplateName = new StringBuilder();

            // deal with header
            while (!"".equals(line = reader.readLine())) {
                if (line.startsWith(MailField.MAIL_FROM.getName())) {
                    mailFromAddress.append(line.substring(
                            MailField.MAIL_FROM.getName().length() + 1).trim());
                } else if (line.startsWith(MailField.MAIL_FROM_NAME.getName())) {
                    mailFromName.append(line.substring(
                            MailField.MAIL_FROM_NAME.getName().length() + 1).trim());
                } else if (line.startsWith(MailField.MAIL_TO.getName())) {
                    mailTo.append(line.substring(MailField.MAIL_TO.getName().length() + 1).trim());
                } else if (line.startsWith(MailField.MAIL_CC.getName())) {
                    mailCc.append(line.substring(MailField.MAIL_CC.getName().length() + 1).trim());
                } else if (line.startsWith(MailField.MAIL_BCC.getName())) {
                    mailBcc.append(line.substring(MailField.MAIL_BCC.getName().length() + 1).trim());
                } else if (line.startsWith(MailField.MAIL_SUBJECT.getName())) {
                    mailSubject.append(line
                            .substring(MailField.MAIL_SUBJECT.getName().length() + 1).trim());
                } else if (line.startsWith(MailField.TEMPLATE.getName())) {
                    themeTemplateName.append(line.substring(
                            MailField.TEMPLATE.getName().length() + 1).trim());
                }
            }

            // get mail body
            StringBuilder mailBody = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                mailBody.append(line);
            }

            reader.close();

            if (themeTemplateName.length() == 0) {
                themeTemplateName.append("defaultThemeTemplate.ftl");
            }

            Map<String, Object> renderData = new HashMap<String, Object>();
            renderData.put("content", mailBody.toString());

            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerMailConfiguration.getTemplate(themeTemplateName.toString()),
                    renderData);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false,
                    "utf-8");

            // set from
            messageHelper.setFrom(mailFromAddress.toString(), mailFromName.toString());
            String DELIMITERS = ",; \t";
            // set to
            messageHelper.setTo(StringUtils.tokenizeToStringArray(mailTo.toString(), DELIMITERS));
            // set cc
            if (mailCc.length() != 0) {
                messageHelper
                        .setCc(StringUtils.tokenizeToStringArray(mailCc.toString(), DELIMITERS));
            }
            // set bcc
            if (mailBcc.length() != 0) {
                messageHelper.setBcc(StringUtils.tokenizeToStringArray(mailBcc.toString(),
                        DELIMITERS));
            }
            // set subject
            messageHelper.setSubject(mailSubject.toString());

            // set body
            messageHelper.setText(htmlBody, true);

            // new thread send mail
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mailSender.send(messageHelper.getMimeMessage());
                }
            }).start();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (TemplateException e) {
            log.error(e.getMessage(), e);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * mail field enum
     */
    public enum MailField {

        MAIL_TO("to"), MAIL_CC("cc"), MAIL_BCC("bcc"), MAIL_SUBJECT("subject"), MAIL_FROM_NAME(
                "name"), MAIL_FROM("from"), TEMPLATE("template");

        private String name;

        MailField(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

}
