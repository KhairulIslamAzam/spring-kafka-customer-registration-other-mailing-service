package com.example.email.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * @author Md. Rezaul Hasan
 * @since 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

  private String senderName;
  private String to;
  private String from;
  private String subject;
  private String body;
  private Boolean templateEnable;
  private List<AttachmentInfo> attachmentInfo;
  private TemplateInfo templateInfo;

}
