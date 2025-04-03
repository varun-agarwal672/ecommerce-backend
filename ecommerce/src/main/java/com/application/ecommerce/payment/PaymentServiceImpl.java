package com.application.ecommerce.payment;

import com.application.ecommerce.model.Order;
import com.application.ecommerce.model.Payment;
import com.application.ecommerce.order.OrderRepository;
import com.application.ecommerce.payment.dto.PaymentRequest;
import com.application.ecommerce.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus(paymentRequest.getStatus());
        payment.setPaymentDate(paymentRequest.getPaymentDate());

        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentResponse(savedPayment.getId(), paymentRequest.getOrderId(), savedPayment.getPaymentMethod(), savedPayment.getStatus());
    }
}
