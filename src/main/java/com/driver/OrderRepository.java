package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderDB = new HashMap<>();

    HashMap<String, DeliveryPartner> partnerDB = new HashMap<>();

    HashMap<String, List<String>> orderPartnerDB = new HashMap<>();

    HashSet<String> unAssignedOrder = new HashSet<>();

    public void addOrder(Order order) {
        String id = order.getId();
        orderDB.put(id, order);
        unAssignedOrder.add(id);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerDB.put(partnerId, deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {

        List<String> list = orderPartnerDB.getOrDefault(partnerId, new ArrayList<>());
        list.add(orderId);

        DeliveryPartner partner = partnerDB.get(partnerId);
        int noOfOrders = partner.getNumberOfOrders();
        partner.setNumberOfOrders(noOfOrders+1);

        orderPartnerDB.put(partnerId, list);
        unAssignedOrder.remove(orderId);
    }

    public Order getOrderById(String orderId) {

        return orderDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        DeliveryPartner partner = partnerDB.get(partnerId);

        return partner.getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {

        return orderPartnerDB.get(partnerId);
    }

    public List<String> getAllOrders() {

       List<String> list = new ArrayList<>();

        for(Map.Entry<String, Order> order: orderDB.entrySet())
       {
            list.add(order.getKey());
       }

        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        return unAssignedOrder.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(Integer time, String partnerId) {

        List<String> list = orderPartnerDB.get(partnerId);

        int count = 0;

        for(String orderId: list)
        {
            Order order = orderDB.get(orderId);
            if(time < order.getDeliveryTime()){
                count++;
            }
        }

        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {

        List<String> list = orderPartnerDB.get(partnerId);

        int time = 0;

        for(String orderId: list)
        {
            Order order = orderDB.get(orderId);
            if(order.getDeliveryTime() > time){
                time = order.getDeliveryTime();
            }
        }

        return time;
    }

    public void deletePartnerById(String partnerId) {

        if(orderPartnerDB.isEmpty()) {
        }

        else
        {
            unAssignedOrder.addAll(orderPartnerDB.get(partnerId));
        }

        orderPartnerDB.remove(partnerId);
        partnerDB.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {

        if(orderDB.containsKey(orderId))
        {
            orderDB.remove(orderId);
        }

        if(unAssignedOrder.contains(orderId)){
            unAssignedOrder.remove(orderId);
        }

        for(String partnerId: orderPartnerDB.keySet())
        {
            List<String> list = orderPartnerDB.get(partnerId);
            if(list.contains(orderId)){
                list.remove(orderId);
            }
        }
    }
}
