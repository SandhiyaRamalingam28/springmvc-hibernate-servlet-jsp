package com.kgisl.springmvc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kgisl.springmvc.entity.Customer;
import com.kgisl.springmvc.exception.ResourceNotFoundException;
import com.kgisl.springmvc.service.CustomerService;

@Controller
public class CustomerController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listCustomers(Model theModel) {
		List<Customer> theCustomers = customerService.getCustomers();
		theModel.addAttribute("customers", theCustomers);
		return "list-customers";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showFormForAdd(Model theModel) {
		LOG.debug("inside show customer-form handler method");
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);
		return "customer-form";
	}
	
	@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		customerService.saveCustomer(theCustomer);	
		return "redirect:/list";
	}
	
	@RequestMapping(value = "/updateForm", method = RequestMethod.GET)
	public String showFormForUpdate(@RequestParam("customerId") int theId,
									Model theModel) throws ResourceNotFoundException {
		Customer theCustomer = customerService.getCustomer(theId);	
		theModel.addAttribute("customer", theCustomer);
		return "customer-form";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteCustomer(@RequestParam("customerId") int theId) throws ResourceNotFoundException {
		customerService.deleteCustomer(theId);
		return "redirect:/list";
	}
}
