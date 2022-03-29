package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.form.OrderSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.jpabook.jpashop.service
 * fileName       : OrderServiceTest
 * author         : Jihun Park
 * date           : 2022/03/18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/18        Jihun Park       최초 생성
 */
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember("회원1");

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals( 10000 * 2, getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(),"주문 가격은 가격 * 수량이다.");
    }



    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember("회원1");
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //when
        //then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember("회원1");
        Book book = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"상품 주문시 상태는 CANCEL");
        assertEquals(10, book.getStockQuantity(),"주문이 취소된 상태는 수량 복구가 되어야한다.");

    }

    @Test
    public void 주문전체조회() throws Exception{
        //given
        Member member = createMember("회원1");
        Book book1 = createBook("시골 JPA1", 10000, 10);
        Book book2 = createBook("시골 JPA2", 10000, 10);
        Book book3 = createBook("시골 JPA3", 10000, 10);

        orderService.order(member.getId(), book1.getId(), 2);
        orderService.order(member.getId(), book2.getId(), 2);
        orderService.order(member.getId(), book3.getId(), 2);

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName(member.getName());

        //when
        List<Order> orders = orderService.findOrders(orderSearch);

        //then
        assertEquals(3, orders.size(),"총 주문은 3건");

    }


    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }




}