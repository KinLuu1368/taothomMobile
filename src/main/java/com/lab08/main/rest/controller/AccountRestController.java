package com.lab08.main.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab08.main.Entity.Account;
import com.lab08.main.service.AccountService;
import com.lab08.main.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/accounts")
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @GetMapping()
    public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return accountService.getAdministrators();
        } else {
            return accountService.findAll();
        }
    }
    @PostMapping()
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }   

    @Autowired
    EmailService emailService;

    private final Map<String, String> verificationCodes = new HashMap<>();

    @PostMapping("/send-code")
public Map<String, Object> sendVerificationCode(@RequestParam String email) {
    String code = String.format("%05d", new Random().nextInt(100000));
    verificationCodes.put(email, code);
    try {
        emailService.sendVerificationCode(email, code);
    } catch (MessagingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }  // Gửi email với mã xác nhận
    
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Mã xác thực đã được gửi tới email!");
    return response;
}


@PostMapping("/register")
public Map<String, Object> register(@RequestBody Account account, @RequestParam String code) {
    Map<String, Object> response = new HashMap<>();
    String savedCode = verificationCodes.get(account.getEmail().toLowerCase());
    if (savedCode != null && savedCode.equals(code)) {
        accountService.create(account);
        verificationCodes.remove(account.getEmail().toLowerCase());
        response.put("success", true);
        response.put("message", "Tài khoản đã được tạo!");
    } else {
        response.put("success", false);
        response.put("message", "Mã xác thực không chính xác!");
    }
    return response;
}

@GetMapping("/check-username")
public Map<String, Object> checkUsername(@RequestParam String username) {
    Map<String, Object> response = new HashMap<>();
    boolean exists = accountService.isUsernameExists(username);
    response.put("exists", exists);
    return response;
}

@PostMapping("/change-password")
    public Map<String, Object> changePassword(@RequestBody Map<String, String> requestData, @RequestParam String code) {
        String email = requestData.get("email");
        String newPassword = requestData.get("newPassword");

        Map<String, Object> response = new HashMap<>();
        String savedCode = verificationCodes.get(email.toLowerCase());

        if (savedCode != null && savedCode.equals(code)) {
            // Cập nhật mật khẩu
            Optional<Account> accountOpt = accountService.findByEmail(email);
            if (accountOpt.isPresent()) {
                Account account = accountOpt.get();
                account.setPassword(newPassword);  // Bạn có thể thêm mã hóa mật khẩu tại đây
                accountService.update(account);    // Cập nhật tài khoản
                verificationCodes.remove(email.toLowerCase());

                response.put("success", true);
                response.put("message", "Mật khẩu đã được thay đổi thành công!");
            } else {
                response.put("success", false);
                response.put("message", "Tài khoản không tồn tại!");
            }
        } else {
            response.put("success", false);
            response.put("message", "Mã xác thực không chính xác!");
        }
        return response;
    }
}
