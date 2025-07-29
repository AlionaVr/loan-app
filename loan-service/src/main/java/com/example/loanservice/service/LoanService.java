package com.example.loanservice.service;

import com.example.loanservice.dto.LoanRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    public void sendApplication(LoanRequestDto request) {
        // Здесь должна быть логика отправки заявки на кредит
        // Например, сохранение заявки в базу данных или отправка в очередь сообщений
        System.out.println("Заявка на кредит отправлена: " + request);
    }
}
