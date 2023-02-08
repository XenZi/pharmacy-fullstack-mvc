package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.wrappers.MedicineOrderItemWithIDWrapper;
import com.example.owppharmacy.models.wrappers.MedicineOrderItemWrapper;
import com.example.owppharmacy.service.impl.MedicineOrderService;
import com.example.owppharmacy.service.impl.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/order")
public class MedicineOrderController {

    @Autowired
    private MedicineOrderService medicineOrderService;
    @Autowired
    private MedicineService medicineService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public String createMedicineOrderItem(@RequestBody MedicineOrderItemWrapper[] medicineOrderArray, HttpSession session, HttpServletResponse response) throws IOException {
        try {
            medicineOrderService.createMedicineOrderItem(medicineOrderArray, session);
            return "Order created!";
        } catch (Exception ex) {
            return "Error! Something happened!";
        }
    }


    @GetMapping("/create")
    public String getCreateOrder(Model model) {
        model.addAttribute("medicines", medicineService.findAll());
        return "pharmacist/create-order";
    }

    @GetMapping("/view")
    public String getViewOrder(@RequestParam String orderID, Model model) {
        model.addAttribute("items", medicineOrderService.findOrdersByOrderID(orderID));
        model.addAttribute("order", medicineOrderService.findOrderByOrderID(orderID));
        model.addAttribute("medicines", medicineService.findAll());
        return "pharmacist/view-order";
    }

    @PostMapping(value = "/update", consumes = "application/json")
    @ResponseBody
    public String postUpdate(@RequestBody MedicineOrderItemWithIDWrapper[] updateWrapperArray) {
        try {
            medicineOrderService.updateMedicineOrderItem(updateWrapperArray, updateWrapperArray[0].getOrderID());
            return "Order updated!";
        } catch (Exception ex) {
            return "Error!";
        }
    }

    @GetMapping("/view-as-admin")
    public String getViewOrderAsAdmin(@RequestParam String orderID, Model model) {
        model.addAttribute("items", medicineOrderService.findOrdersByOrderID(orderID));
        model.addAttribute("order", medicineOrderService.findOrderByOrderID(orderID));
        model.addAttribute("medicines", medicineService.findAll());
        return "admin/view-order-as-admin";
    }

    @PostMapping("/updateFeedback")
    public void postUpdateFeedback(@RequestParam String feedback, @RequestParam String orderID) {
        medicineOrderService.updateOrderFeedback(feedback, orderID);
    }

    @PostMapping("/reject")
    public void postReject(@RequestParam String feedback, @RequestParam String orderID) {
        medicineOrderService.rejectOrder(feedback, orderID);
    }

    @PostMapping("/approve")
    public void postApprove(@RequestParam String orderID) {
        medicineOrderService.approveOrder(orderID);
    }
}
