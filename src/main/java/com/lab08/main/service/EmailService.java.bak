package com.lab08.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.el.ELException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final String FROM_EMAIL = "support@taothomstore.com";
    private static final String FROM_NAME = "Bộ phận Hỗ trợ - TaoThomStore";

    public void sendVerificationCode(String toEmail, String code) throws MessagingException {
        try {
            // Tạo một đối tượng MimeMessage
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // Sử dụng MimeMessageHelper để hỗ trợ định dạng HTML và UTF-8
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom(FROM_EMAIL, FROM_NAME); // Thiết lập người gửi với tên và email
            helper.setSubject("Mã xác thực đăng ký");

            // Nội dung email với HTML và UTF-8
            String content = """
                        <p>Xin chào,</p>
                        <p>Mã xác thực của bạn là: <b>%s</b>. Vui lòng sử dụng mã này để hoàn tất đăng ký.</p>
                        <p>Trân trọng,<br>Bộ phận Hỗ trợ - TaoThomStore</p>
                    """.formatted(code);

            helper.setText(content, true); // Gửi email dưới dạng HTML

            mailSender.send(mimeMessage); // Gửi email
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
