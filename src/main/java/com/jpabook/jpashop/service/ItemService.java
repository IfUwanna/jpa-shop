package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.jpabook.jpashop.service
 * fileName       : ItemService
 * author         : Jihun Park
 * date           : 2022/03/18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/03/18        Jihun Park       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId); // 영속상태
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        //findItem.change(price,name,stockQuantity) // 실무에서는 sette말고 엔티티에 비즈니스 로직을 넣어주자 .

        //@Transactional 이 걸린 서비스가 종료되는 시점에 커밋이 나가며 flush()가 되면서 더티체킹 SQL실행!
        return findItem; // merge()는 이 메커니즘을 JPA에서 해준다고 이해하면 될듯
    }

    @Transactional
    public void updateItem(Long id, String name, int price) {
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setPrice(price);
    }

    public List<Item> findItems(){
        return itemRepository.finAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
