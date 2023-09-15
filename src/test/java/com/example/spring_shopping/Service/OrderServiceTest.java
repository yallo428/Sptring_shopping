package com.example.spring_shopping.Service;

import com.example.spring_shopping.domain.Address;
import com.example.spring_shopping.domain.Member;
import com.example.spring_shopping.domain.Order;
import com.example.spring_shopping.domain.OrderStatus;
import com.example.spring_shopping.domain.item.Book;
import com.example.spring_shopping.domain.item.Item;
import com.example.spring_shopping.exception.NotEnoughStockException;
import com.example.spring_shopping.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    com.example.spring_shopping.Service.OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문(){
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int count = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), count);

        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문 상품 수가 같아야 한다");
        Assertions.assertEquals(10000 * count, getOrder.getTotalPrice(), "가격은 가격 * 수량이다");
        Assertions.assertEquals(8, book.getStockQuantity(), "주문 수량만큼 감소");

    }

    private Book createBook(String name, int price, int count) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(count);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과(){
        Member member1 = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        orderService.order(member1.getId(), item.getId(), orderCount);

        fail("재고 수량 부족 오류가 발생해야 한다");
    }


    @Test
    public void 주문취소(){
        Member member1 = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member1.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 캔슬", OrderStatus.CANCEL, order.getStatus());
        assertEquals("취소 시 그만큼 재고 증가", 10, item.getStockQuantity());
    }
}