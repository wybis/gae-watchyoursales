package io.vteial.wys.web.customer

import io.vteial.wys.model.Customer

List<Customer> customers = Customer.findAll()

response.contentType = 'application/json'
jsonObjectMapper.writeValue(out, customers)
