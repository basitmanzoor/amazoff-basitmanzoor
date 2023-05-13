package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServices {

    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
       return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        String arr[] = time.split(":");
        int hh = Integer.parseInt(arr[0]);
        int mm = Integer.parseInt(arr[1]);

        int IntegerTime = (hh*60)+mm;

        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(IntegerTime, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {

        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);

        int hh = time/60;
        int mm = time%60;

        String HH = String.valueOf(hh);
        if(HH.length()==1){
            HH = '0'+HH;
        }

        String MM = String.valueOf(mm);
        if(MM.length()==1){
            MM = '0'+MM;
        }

        return HH + ":" + MM;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
