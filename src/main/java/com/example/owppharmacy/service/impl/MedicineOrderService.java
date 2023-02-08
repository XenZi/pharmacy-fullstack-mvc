package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.MedicineOrderRepository;
import com.example.owppharmacy.enums.EOrderStatus;
import com.example.owppharmacy.models.MedicineOrder;
import com.example.owppharmacy.models.MedicineOrderItem;
import com.example.owppharmacy.models.Pharmacist;
import com.example.owppharmacy.models.wrappers.MedicineOrderItemWithIDWrapper;
import com.example.owppharmacy.models.wrappers.MedicineOrderItemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MedicineOrderService {

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private MedicineOrderRepository repository;
    public void createMedicineOrderItem(MedicineOrderItemWrapper[] medicineOrderArrayOfWrapper, HttpSession session) {
        MedicineOrder order = new MedicineOrder();
        Pharmacist pharmacist = (Pharmacist) session.getAttribute("pharmacist");
        for (MedicineOrderItemWrapper wrapperItem : medicineOrderArrayOfWrapper) {
            MedicineOrderItem item = new MedicineOrderItem();
            item.setMedicine(medicineService.findOne(wrapperItem.getMedicineID()));
            item.setDescription(wrapperItem.getDescription());
            item.setQuantity(wrapperItem.getQuantity());
            order.getOrderedItems().add(item);
        }
        order.setPharmacist(pharmacist.getAccount());
        order.setId(UUID.randomUUID().toString());
        order.setOrderFeedback(null);
        order.setStatus(EOrderStatus.PENDING_REVIEW);
        order.setCreationDate(LocalDateTime.now());
        repository.saveOrder(order);
        for (MedicineOrderItem item : order.getOrderedItems()) {
            repository.saveItem(item, order.getId());
        }
    }

    public void updateMedicineOrderItem(MedicineOrderItemWithIDWrapper[] medicineOrderItemWrappersArray, String orderID) {
        MedicineOrder order = this.findOrderByOrderID(orderID);
        for (MedicineOrderItemWithIDWrapper medicineOrderItemWithIDWrapper : medicineOrderItemWrappersArray) {
            MedicineOrderItem item = order.getOrderedItems().stream().filter(el -> el.getId() == medicineOrderItemWithIDWrapper.getId()).findFirst().orElse(null);
            item.setMedicine(medicineService.findOne(medicineOrderItemWithIDWrapper.getMedicineID()));
            item.setDescription(medicineOrderItemWithIDWrapper.getDescription());
            item.setQuantity(medicineOrderItemWithIDWrapper.getQuantity());
            repository.updateOrderItems(item);
        }
        this.updateOrderStatusToPendingReview(orderID);
    }

    public void updateOrderStatusToPendingReview(String orderID) {
        repository.updateOrderStatusToPendingReview(orderID);
    }
    public List<MedicineOrder> findOrdersByPharmacistID(int pharmacistID) {
        return repository.findOrdersByPharmacistID(pharmacistID);
    }

    public List<MedicineOrderItem> findOrdersByOrderID(String orderID) {
        return repository.findOrderItemsByOrderID(orderID);
    }

    public MedicineOrder findOrderByOrderID(String orderID) {
        return repository.findOrderByOrderID(orderID);
    }

    public List<MedicineOrder> findOrdersByStatusPendingReview() {
        return repository.findOrdersByStatusPendingReview();
    }

    public void updateOrderFeedback(String feedback, String orderID) {
        repository.updateOrderFeedback(feedback, orderID);
    }

    public void rejectOrder(String feedback, String orderID) {
        repository.rejectOrder(feedback, orderID);
    }

    public void approveOrder(String orderID) {
        MedicineOrder order = this.findOrderByOrderID(orderID);
        repository.approveOrder(orderID);
        for (MedicineOrderItem item : order.getOrderedItems()) {
            medicineService.updateQuantity(item.getMedicine().getId(), item.getQuantity());
        }
    }
}
