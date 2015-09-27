package com.grailstdd

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Customer)
class CustomerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Test that Customer has properties customerName and email"() {
        when: "A customer is created with the given customerName and email"
            String customerName = 'Test Customer'
            String email  = 'test@customer.com'
            Customer customer = new Customer(customerName:customerName, email:email)

        then: "customerName and email are set to the customer"
            customer.customerName == customerName
            customer.email == email
    }

    void "Test that customerName should allow letters and spaces only"() {
        expect: "Customer instance is valid/invalid"
            new Customer(customerName:validCustomerName, email:"valid@mail.com").validate()
            !new Customer(customerName:invalidCustomerName, email:"valid@mail.com").validate()

        where: "Given valid/invalid customerNames"
            validCustomerName << ["customer", "    customer   ", "customer name"]
            invalidCustomerName << ["123customer456", "customer-test", "customer@email.com"]
    }

    void "Test that customerName should be unique"() {
        when: "The first customer is saved"
            String firstName = 'Unique Name'
            String firstEmail  = 'test@customer.com'
            Customer firstCustomer = new Customer(customerName: firstName, email: firstEmail)
            firstCustomer.save(flush:true)

        then: "Another customer with same customerName is invalid"
            Customer.count() == 1
            String secondEmail = 'second@customer.com'
            Customer customerWithSameCustomerName = new Customer(customerName: firstName, email: secondEmail)
            !customerWithSameCustomerName.validate()
    }

    void "Test that email should have proper format"() {
        expect: "Customer instance is valid/invalid"
            new Customer(customerName:"customer", email:validEmail).validate()
            !new Customer(customerName:"customer", email:invalidEmail).validate()

        where: "Given valid/invalid emails"
            validEmail << ["customer@email.com", "1234567890@example.com", "email@test.museum"]
            invalidEmail << ["plainaddress", "@example.com", "#@%^%x!.com"]
    }

    void "Test that email should be unique"() {
        when: "The first customer is saved"
            String firstName = 'First Name'
            String firstEmail  = 'first@email.com'
            Customer firstCustomer = new Customer(customerName: firstName, email: firstEmail)
            firstCustomer.save(flush:true)

        then: "Another customer with same customerName is invalid"
            Customer.count() == 1
            String secondName = 'Second Name'
            Customer customerWithSameEmail = new Customer(customerName: secondName, email: firstEmail)
            !customerWithSameEmail.save(flush:true)
    }
}
