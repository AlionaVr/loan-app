package com.example.loanservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDto {
    @NotNull(message = "Сумма кредита обязательна")
    @DecimalMin(value = "1000.0", message = "Минимальная сумма кредита 1000")
    private BigDecimal loanAmount;

    @NotNull(message = "Срок кредита обязателен")
    @Min(value = 1, message = "Минимальный срок кредита 1 месяц")
    @Max(value = 360, message = "Максимальный срок кредита 360 месяцев")
    private Integer loanTermMonths;

    @NotNull(message = "Доход пользователя обязателен")
    @DecimalMin(value = "0.0", message = "Доход не может быть отрицательным")
    private BigDecimal userIncome;

    @NotNull(message = "Текущая кредитная нагрузка обязательна")
    @DecimalMin(value = "0.0", message = "Кредитная нагрузка не может быть отрицательной")
    private BigDecimal currentDebt;

    @NotNull(message = "Кредитный рейтинг обязателен")
    @Min(value = 300, message = "Минимальный кредитный рейтинг 300")
    @Max(value = 850, message = "Максимальный кредитный рейтинг 850")
    private Integer creditRating;
}