package com.example.spring_shopping.service;

import com.example.spring_shopping.domain.item.Book;
import com.example.spring_shopping.domain.item.Item;
import com.example.spring_shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void updateItem(Long id, Book param){
        Item findItem = itemRepository.findOne(id);
//        findItem.setId();
//        findItem.setName();
//        findItem.setPrice();
//        findItem.setStockQuantity();
//        findItem.setCategories();
    }


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findItem(Long id){
        return itemRepository.findOne(id);
    }
}
