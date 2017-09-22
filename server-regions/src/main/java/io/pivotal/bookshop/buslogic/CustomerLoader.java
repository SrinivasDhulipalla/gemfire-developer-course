package io.pivotal.bookshop.buslogic;

import io.pivotal.bookshop.domain.Address;
import io.pivotal.bookshop.domain.Customer;
import lombok.extern.log4j.Log4j2;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;

@Log4j2
public class CustomerLoader {

  public static void main(String[] args) {

    try (ClientCache cache = new ClientCacheFactory().create()) {
      Region<Long, Customer> customerRegion = cache.getRegion("Customer");

      Customer cust1 = Customer.builder().customerNumber(5598)
          .firstName("Kari").lastName("Powell")
          .bookOrder(17699L).bookOrder(18009L).bookOrder(18049L)
          .address(Address.builder().postalCode("44444").build()).build();
      addCustomer(customerRegion, cust1);

      Customer cust2 = Customer.builder().customerNumber(5543)
          .firstName("Lula").lastName("Wax")
          .bookOrder(17699L)
          .address(Address.builder().postalCode("12345").build()).build();
      addCustomer(customerRegion, cust2);

      Customer cust3 = Customer.builder().customerNumber(6024)
          .firstName("Trenton").lastName("Garcia")
          .address(Address.builder().postalCode("88888").build()).build();
      addCustomer(customerRegion, cust3);
    }
  }

  private static void addCustomer(Region<Long, Customer> customerRegion, Customer customer) {
    long key = customer.getCustomerNumber();
    customerRegion.put(key, customer);
    log.info("Inserted a customer: " + customer);
  }

}
