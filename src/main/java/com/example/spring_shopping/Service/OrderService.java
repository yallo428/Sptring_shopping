package com.example.spring_shopping.Service;


import com.example.spring_shopping.domain.Delivery;
import com.example.spring_shopping.domain.Member;
import com.example.spring_shopping.domain.Order;
import com.example.spring_shopping.domain.OrderItem;
import com.example.spring_shopping.domain.item.Item;
import com.example.spring_shopping.repository.ItemRepository;
import com.example.spring_shopping.repository.MemberRepository;
import com.example.spring_shopping.repository.OrderRepository;
import com.example.spring_shopping.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }


    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
    /*
        이처럼 서비스에서 엔티티에 대한 요청만하고
        로직을 엔티티 안에서 구현하는 것을 도메인 모델 패턴이라고 한다
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}

