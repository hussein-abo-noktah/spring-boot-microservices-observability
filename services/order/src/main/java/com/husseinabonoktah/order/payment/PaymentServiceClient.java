package com.husseinabonoktah.order.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceClient {

  private final PaymentClient paymentClient;

  public void requestOrderPayment(PaymentRequest request) {
    paymentClient.requestOrderPayment(request);
  }


}
