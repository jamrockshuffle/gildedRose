package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GildedRoseTest {

    // Quality and sell-in should decrease by 1 at the end of each day
    @Test
    public void qualityDecreasesByOneAtTheEndOfEachDay() {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }

    // Quality of several items should lower by 1 at the end of each day
    @Test
    public void qualityDecreasesByOneAtTheEndOfEachDayForMultipleItems() {
        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Elixir of the Mongoose", 5, 7)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertAll("Multiple items degrade",
                () -> assertEquals(19, app.items[0].quality),
                () -> assertEquals(6, app.items[1].quality)
        );
    }

    // Item quality should decrease twice as fast after sell-in
    @Test
    public void afterSellInQualityDecreasesTwofold() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 0, 7) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
    }

    // Item quality should decrease by 1 where there is only one day left of sell-in
    @Test
    public void oneSellInDayDecreasesQualityByOne() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 1, 7) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
    }

    // Aged Brie should increase its quality over time
    @Test
    public void brieQualityIncreasesOverTime() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
    }

    // After sell in Aged Brie should increase in quality twice as fast
    @Test
    public void brieQualityIncreasesTwofoldOverTime() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12, app.items[0].quality);
    }

    // Under no circumstances should Legendary items decrease their SellIn attribute (in this case, when day passes)
    @Test
    public void legendaryItemSellInIsAlwaysTheSame() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
    }

    // Under no circumstances should Legendary items drop in quality (in this case, when day passes)
    @Test
    public void legendaryItemQualityIsAlwaysTheSame() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    // Backstage pass quality increases during sell-in
    @Test
    public void backstagePassDuringSellInIsIncreased() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, app.items[0].quality);
    }

    // Backstage pass quality lowers to 0 after a concert
    @Test
    public void backstagePassAfterConcertIsZero() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

}
