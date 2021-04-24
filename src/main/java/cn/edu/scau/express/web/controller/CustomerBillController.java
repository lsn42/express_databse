package cn.edu.scau.express.web.controller;

import com.google.gson.Gson;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.scau.express.service.CustomerBillService;

public class CustomerBillController {

    @ResponseBody
    @GetMapping(value = "/query/bill/personal/{id}/")
    public String ChargeBill(@PathVariable("id") String id) {
        //
        return (new Gson()).toJson(CustomerBillService.FindBill(id));
    }

    // Serach Type? By Time?
    @ResponseBody
    @GetMapping(value = "/query/bill/search/{id}/")
    public String IndivialBill(@PathVariable("id") String id) {
        //
        return (new Gson()).toJson(CustomerBillService.FindListsBill(id));
    }
}
