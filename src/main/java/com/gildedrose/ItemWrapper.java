package com.gildedrose;

import java.util.function.Function;

public class ItemWrapper {

    private final Item item;
    private final Function<Item, Item> getAgedItem;
    private static final AgeItemFunctionFactory AGE_ITEM_FUNCTION_BUILDER = new AgeItemFunctionFactory();

    public ItemWrapper(Item item) {
        this.item = item;
        getAgedItem = AGE_ITEM_FUNCTION_BUILDER.build(item);
    }

    public Item get() {
        return item;
    }

    public Item age() {
        return getAgedItem.apply(item);
    }

}
