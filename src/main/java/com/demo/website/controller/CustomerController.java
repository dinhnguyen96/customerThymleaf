package com.demo.website.controller;

import com.demo.website.model.Customer;
import com.demo.website.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/")
    public String index(Model model) {

        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers", customerList);
        return "/index";
    }
    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") int id)
    {
        Customer customer = customerService.findById(id);
        ModelAndView model = new ModelAndView();
        model.addObject("status", 1);
        model.addObject("customer", customer);
        model.setViewName("/edit");
        return model;
    }

    @PostMapping("/updateCustomer")
    public String update(@ModelAttribute("customer") Customer customer)
    {
        Customer updateCustomer = customerService.findById(customer.getId());

        if (updateCustomer == null)
        {
            updateCustomer = customer;
            customerService.save(updateCustomer);
        }
        else
        {
            updateCustomer.setName(customer.getName());
            updateCustomer.setAddress(customer.getAddress());
            updateCustomer.setEmail(customer.getEmail());
            customerService.update(updateCustomer.getId(), updateCustomer);
        }
        return "redirect:/customer/";
    }
    @GetMapping("/{id}/delete")
    public String remove(@PathVariable("id") int id)
    {
        customerService.remove(id);
        return "redirect:/customer/";
    }
    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("status", 0);
        return "/edit";
    }

}