package com.grailstdd

class Customer {
    String customerName
    String email

    static constraints = {
        customerName matches: "^[a-zA-Z ]+\$", unique: true
        email email: true, unique: true
    }
}
