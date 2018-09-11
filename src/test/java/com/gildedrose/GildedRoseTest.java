package com.gildedrose;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

    @Test
    public void sellInAndQualityReduceByOnePerDay() {

        givenItem().withName("foo").withSellIn(9).withQuality(8)
                .whenNextDay()
                .thenExpectSellIn(8).andExpectQuality(7);
    }

    @Test
    public void expiredItemsDegradeTwiceAsFast() {
        givenItem().withName("foo").withSellIn(0).withQuality(7)
                .whenNextDay()
                .thenExpectSellIn(-1).andExpectQuality(5);
    }

    @Test
    public void qualityIsNeverNegative() {
        givenItem().withName("foo").withSellIn(10).withQuality(0)
                .whenNextDay()
                .thenExpectSellIn(9).andExpectQuality(0);
    }

    @Test
    public void agedBrieIncreasesInQuality() {
        givenItem().withName("Aged Brie").withSellIn(10).withQuality(5)
                .whenNextDay()
                .thenExpectSellIn(9).andExpectQuality(6);
    }

    @Test
    public void itemQualityIsNeverMoreThan50() {
        givenItem().withName("Aged Brie").withSellIn(3).withQuality(50)
                .whenNextDay()
                .thenExpectSellIn(2).andExpectQuality(50);
    }

    @Test
    public void legendaryItemsDoNotHaveToBeSoldAndDoNotDecreaseInQuality() {
        givenItem().withName("Sulfuras, Hand of Ragnaros").withSellIn(3).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(3).andExpectQuality(20);
    }

    @Test
    public void backstagePassesIncreaseByOneWhen11DaysOrMore() {
        givenItem().withName("Backstage passes to a TAFKAL80ETC concert").withSellIn(11).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(10).andExpectQuality(21);
    }

    @Test
    public void backstagePassesIncreaseByTwoWhen10Days() {
        givenItem().withName("Backstage passes to a TAFKAL80ETC concert").withSellIn(10).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(9).andExpectQuality(22);
    }

    @Test
    public void backstagePassesIncreaseByTwoWhenSixToTenDays() {
        givenItem().withName("Backstage passes to a TAFKAL80ETC concert").withSellIn(6).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(5).andExpectQuality(22);
    }

    @Test
    public void backstagePassesIncreaseByThreeWhenFiveDays() {
        givenItem().withName("Backstage passes to a TAFKAL80ETC concert").withSellIn(5).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(4).andExpectQuality(23);
    }

    @Test
    public void backstagePassesIncreaseByThreeWhenOneToFiveDays() {
        givenItem().withName("Backstage passes to a TAFKAL80ETC concert").withSellIn(1).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(0).andExpectQuality(23);
    }

    @Test
    public void backstagePassesHaveNoValueAfterTheShow() {
        givenItem().withName("Backstage passes to a TAFKAL80ETC concert").withSellIn(0).withQuality(20)
                .whenNextDay()
                .thenExpectSellIn(-1).andExpectQuality(0);
    }

    @Test
    public void conjuredItemsDegradeByTwoDaily() {
        givenItem().withName("Conjured").withSellIn(3).withQuality(5)
                .whenNextDay()
                .thenExpectSellIn(2).andExpectQuality(3);
    }

    private TestDsl givenItem() {
        return new TestDsl();
    }

}
