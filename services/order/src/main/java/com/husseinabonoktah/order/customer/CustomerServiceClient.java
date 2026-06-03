package com.husseinabonoktah.order.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceClient {

  private final CustomerClient customerClient;

  public Optional<CustomerResponse> findById(String customerId) {
    return customerClient.findById(customerId);
  }


}
