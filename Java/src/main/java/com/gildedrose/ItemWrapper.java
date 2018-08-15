package com.gildedrose;

public class ItemWrapper {

    private final Item item;

    public ItemWrapper(Item item) {
        this.item = item;
    }

    public Item get() {
        return item;
    }
}
