package com.gildedrose;

import java.util.function.Function;

public class ItemWrapper {

    private final Item item;
    private final Function<Item, Item> getAgedItem;
    private static final AgeItemFunctionFactory AGE_ITEM_FUNCTION_FACTORY = new AgeItemFunctionFactory();

    public ItemWrapper(Item item) {
        this.item = item;
        getAgedItem = AGE_ITEM_FUNCTION_FACTORY.build(item);
    }

    public Item get() {
        return item;
    }

    public ItemWrapper age() {
        return new ItemWrapper(getAgedItem.apply(item));
    }

}
