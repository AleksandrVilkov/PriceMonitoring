package com.vilkov.PriceMonitoring.controller;

import com.vilkov.PriceMonitoring.model.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Работа с продуктами.
 * /product/add/{url} - добавит объект в лист монитора.
 * В дальнейшем, по url объекта будет получать информацию о цене на момент запроса, и повторно сохранять в БД с заданным интервалом.
 * <p>
 * <p>
 * /product/delete/{url} - удалит url из листа монитора. Прайсинг производиться не будет
 */
@org.springframework.stereotype.Controller
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/add/{url}")
    public Product addNewProduct(@PathVariable String url) {
        return null;
    }
    // @GetMapping("/delete/{url}")
    //public boolean deleteProduct(@PathVariable String url) {
    //   return ProductHelper.deleteProductInDataBase(url);
}

