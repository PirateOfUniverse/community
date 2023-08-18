package com.community.app.mapper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface MailMapper {

    // 메일 내용 작성
    MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;

    // 비밀번호 변경 메일
    MimeMessage createPasswdMessage(String to) throws MessagingException, UnsupportedEncodingException;

    // 랜덤 인증코드 생성
    String createKey();

    // 메일 발송
    String sendSimpleMessage(String to) throws Exception;

    String sendPasswdMessage(String to) throws Exception;

}
