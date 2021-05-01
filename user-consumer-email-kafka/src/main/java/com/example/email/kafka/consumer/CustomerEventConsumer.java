package com.example.email.kafka.consumer;//package com.example.user.kafka.producer;


import com.example.email.entity.*;
import com.example.email.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Khairul Islam Azam
 * @created 26/04/2021 - 9:19 AM
 * @project IntelliJ IDEA
 */
@Component
@Slf4j
public class CustomerEventConsumer {

    @Autowired
    private EmailService emailService;
    private final ObjectMapper mapper;

    public CustomerEventConsumer() {
        mapper = new ObjectMapper();
    }

    @KafkaListener(topics = {"customer-mail-events","message"})
    public void getUser(ConsumerRecord<String, String> record) {

        log.info("the consumer record of is {} " + record.value());

        Customer customer = new Customer();
        EmailRequest emailRequest = new EmailRequest();

        if(!record.topic().equalsIgnoreCase("message")){
            try {
                customer = mapper.readValue(record.value(), Customer.class);
                System.out.println(customer.toString());
                emailRequest = getEmailRequest(customer.getEmail());

                EmailResponse message = emailService.sendMail(emailRequest.getSenderName(), emailRequest.getTo(), emailRequest.getFrom(),
                        emailRequest.getSubject(),"Dear "+customer.getUsername()+ emailRequest.getBody(), emailRequest.getTemplateEnable(),
                        emailRequest.getTemplateInfo().getTemplateName(),emailRequest.getTemplateInfo().getTemplateModel()
                        , emailRequest.getAttachmentInfo());

                log.info("{} " + message.getMessage());
            } catch (JsonProcessingException e) {
                log.error("json parsing problem in mail");
            }
        }

    }

    private EmailRequest getEmailRequest(String to) {

        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setAttachment("");
        attachmentInfo.setExtension("");
        attachmentInfo.setFileName("");

        List<AttachmentInfo> attachmentInfoList = new ArrayList<>();
        attachmentInfoList.add(attachmentInfo);

        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.setTemplateName("");
        templateInfo.setTemplateModel(null);

        EmailRequest emailRequest1 = new EmailRequest();
        emailRequest1.setSubject("Microsoft Azure Virtual Training Day: Fundamentals event link");
        emailRequest1.setBody(", \nIt is almost time for your virtual training event! Please use the links below to join online. The link to join each part is unique by registrant and should not be forwarded.\n" +
                "\n" +
                "\n" +
                "Microsoft Azure Virtual Training Day: Fundamentals Part 1\n" +
                "20 April 2021 10:00 AM - 12:00 PM | (GMT+08:00) Kuala Lumpur, Singapore\n" +
                "Click the link to join Part 1\n" +
                "\n" +
                "\n" +
                "Microsoft Azure Virtual Training Day: Fundamentals Part 2\n" +
                "21 April 2021 10:00 AM - 12:30 PM | (GMT+08:00) Kuala Lumpur, Singapore\n" +
                "Click the link to join Part 2\n" +
                "\n" +
                "We’re excited to show you how you can use Microsoft Azure to continue to make an impact as your organization adapts to new ways of working and achieving more. This is your chance to guide the transition of your company and open the door to new opportunities.See you soon!");
        emailRequest1.setFrom("azamblackps606862@gmail.com");
        emailRequest1.setTo(to);
        emailRequest1.setSenderName("Microsoft Azure Team");
        emailRequest1.setTemplateEnable(false);
        emailRequest1.setAttachmentInfo(attachmentInfoList);
        emailRequest1.setTemplateInfo(templateInfo);

        return emailRequest1;

    }
}
