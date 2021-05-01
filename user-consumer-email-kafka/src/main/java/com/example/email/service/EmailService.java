package com.example.email.service;

import com.example.email.entity.AttachmentInfo;
import com.example.email.entity.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import freemarker.template.*;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author Khairul Islam Azam
 * @since 1.0.0
 */

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration config;


    public EmailResponse sendMail(String senderName, String to, String from,
                                  String subject, String body,
                                  boolean templateEnable, String templateName, Map<String, Object> model,
                                  List<AttachmentInfo> attachmentInfos) {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        EmailResponse response = new EmailResponse();

        try {
            helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            for (AttachmentInfo attachmentInfo1 : attachmentInfos) {
                if (templateEnable) {
                    Template t = config.getTemplate(templateName);
                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

                    mailInformationSet(senderName, to, from, subject, helper, attachmentInfo1.getAttachment(),
                            attachmentInfo1.getFileName(), attachmentInfo1.getExtension());

                    helper.setText(html, true);

                } else {

                    mailInformationSet(senderName, to, from, subject, helper, attachmentInfo1.getAttachment(),
                            attachmentInfo1.getFileName(), attachmentInfo1.getExtension());

                    helper.setText(body);
                }
            }

            response.setMessage("mail send to : " + to);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            response.setMessage("Mail Sending failure : " + e.getMessage());
        } catch (IOException e) {
            response.setMessage("IO Exception : " + e.getMessage());
        } catch (TemplateException e) {
            response.setMessage("Template error : " + e.getMessage());

        }
        return response;
    }

    private void mailInformationSet(String senderName, String toAddress, String fromAddress,
                                    String subject, MimeMessageHelper helper,
                                    String attachment, String fileName, String extension)
            throws MessagingException, UnsupportedEncodingException {

        String ext;
        ByteArrayDataSource dSource = null;

        if (attachment != null && !attachment.isEmpty()) {
            ext = getExtension(extension);
            byte[] imgBytes = Base64.getDecoder().decode(attachment);
            dSource = new ByteArrayDataSource(imgBytes, ext);
        }

        helper.setTo(toAddress);
        helper.setFrom(fromAddress, senderName);
        helper.setSubject(subject);
//        helper.setReplyTo("khairulislam.azam@gmail.com");

        if (attachment != null && !attachment.isEmpty()) {
            helper.addAttachment(fileName + "." + extension, dSource);
        }


    }

    private String getExtension(String extension) {
        String ext = null;
        if (extension.equalsIgnoreCase("pdf")) {
            ext = "application/pdf; charset=UTF-8";
        }
        if (extension.equalsIgnoreCase("csv")) {
            ext = "text/csv; charset=UTF-8";
        }
        if (extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("xlsx")) {
            ext = "application/octet-stream; charset=UTF-8";
        }
        if (extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpg")) {
            ext = "image/*; charset=UTF-8";
        }

        return ext;
    }
}