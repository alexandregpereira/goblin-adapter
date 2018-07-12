package com.bano.goblin.adapter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestAdapter {

    @Test
    public void whenAdapterIsEmpty() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItems(items);

        assertEquals(5, adapter.getItemCount());
        assertEquals(1, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(3, adapter.getItems().get(2).id);
        assertEquals(4, adapter.getItems().get(3).id);
        assertEquals(5, adapter.getItems().get(4).id);
    }

    @Test
    public void whenIsAllRefreshAndCameLessItems() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItems(items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(20));
        items.add(new GoblinAdapter.Item(21));
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(43));

        adapter.setItems(items);

        assertEquals(4, adapter.getItemCount());
        assertEquals(20, adapter.getItems().get(0).id);
        assertEquals(21, adapter.getItems().get(1).id);
        assertEquals(1, adapter.getItems().get(2).id);
        assertEquals(43, adapter.getItems().get(3).id);
    }

    @Test
    public void whenIsAllRefreshAndCameLessItems2() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItems(items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));

        adapter.setItems(items);

        assertEquals(3, adapter.getItemCount());
        assertEquals(1, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(3, adapter.getItems().get(2).id);
    }

    @Test
    public void whenIsAllRefreshAndCameMoreItems() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItems(items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(20));
        items.add(new GoblinAdapter.Item(21));
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(43));
        items.add(new GoblinAdapter.Item(431));
        items.add(new GoblinAdapter.Item(432));
        items.add(new GoblinAdapter.Item(433));

        adapter.setItems(items);

        assertEquals(7, adapter.getItemCount());
        assertEquals(20, adapter.getItems().get(0).id);
        assertEquals(21, adapter.getItems().get(1).id);
        assertEquals(1, adapter.getItems().get(2).id);
        assertEquals(43, adapter.getItems().get(3).id);
        assertEquals(431, adapter.getItems().get(4).id);
        assertEquals(432, adapter.getItems().get(5).id);
        assertEquals(433, adapter.getItems().get(6).id);
    }

    @Test
    public void whenIsAllRefreshAndCameMoreItems2() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItems(items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));
        items.add(new GoblinAdapter.Item(6));
        items.add(new GoblinAdapter.Item(7));

        adapter.setItems(items);

        assertEquals(7, adapter.getItemCount());
        assertEquals(1, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(3, adapter.getItems().get(2).id);
        assertEquals(4, adapter.getItems().get(3).id);
        assertEquals(5, adapter.getItems().get(4).id);
        assertEquals(6, adapter.getItems().get(5).id);
        assertEquals(7, adapter.getItems().get(6).id);
    }

    @Test
    public void whenIsAllRefreshWithDifferentOrder() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItems(items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(5));
        items.add(new GoblinAdapter.Item(4));

        adapter.setItems(items);

        assertEquals(5, adapter.getItemCount());
        assertEquals(2, adapter.getItems().get(0).id);
        assertEquals(3, adapter.getItems().get(1).id);
        assertEquals(1, adapter.getItems().get(2).id);
        assertEquals(5, adapter.getItems().get(3).id);
        assertEquals(4, adapter.getItems().get(4).id);
    }

    @Test
    public void whenIsRangeRefreshWithDifferentOrder() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(0, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(5));
        items.add(new GoblinAdapter.Item(4));

        adapter.setItemsInRange(0, 5, items);

        assertEquals(5, adapter.getItemCount());
        assertEquals(2, adapter.getItems().get(0).id);
        assertEquals(3, adapter.getItems().get(1).id);
        assertEquals(1, adapter.getItems().get(2).id);
        assertEquals(5, adapter.getItems().get(3).id);
        assertEquals(4, adapter.getItems().get(4).id);
    }

    @Test
    public void whenIsRangeRefreshAndItemCameDifferentOrder() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(0, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(20));
        items.add(new GoblinAdapter.Item(21));
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(43));
        items.add(new GoblinAdapter.Item(431));

        adapter.setItemsInRange(5, 5, items);

        assertEquals(9, adapter.getItemCount());
        assertEquals(2, adapter.getItems().get(0).id);
        assertEquals(3, adapter.getItems().get(1).id);
        assertEquals(4, adapter.getItems().get(2).id);
        assertEquals(5, adapter.getItems().get(3).id);
        assertEquals(20, adapter.getItems().get(4).id);
        assertEquals(21, adapter.getItems().get(5).id);
        assertEquals(1, adapter.getItems().get(6).id);
        assertEquals(43, adapter.getItems().get(7).id);
        assertEquals(431, adapter.getItems().get(8).id);
    }

    @Test
    public void whenIsRangeRefreshAndItemCameDifferentOrder2() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(0, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(5, 5, items);

        assertEquals(5, adapter.getItemCount());
        assertEquals(3, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(1, adapter.getItems().get(2).id);
        assertEquals(4, adapter.getItems().get(3).id);
        assertEquals(5, adapter.getItems().get(4).id);
    }

    @Test
    public void whenIsRangeRefreshWithLessItems() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(0, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(5));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(1));

        adapter.setItemsInRange(0, 5, items);

        assertEquals(3, adapter.getItemCount());
        assertEquals(5, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(1, adapter.getItems().get(2).id);
    }

    @Test
    public void whenIsRangeRefreshWithLessItems2() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(0, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(6));
        items.add(new GoblinAdapter.Item(7));
        items.add(new GoblinAdapter.Item(8));
        items.add(new GoblinAdapter.Item(9));
        items.add(new GoblinAdapter.Item(10));
        adapter.setItemsInRange(5, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(6));
        items.add(new GoblinAdapter.Item(7));
        items.add(new GoblinAdapter.Item(10));

        adapter.setItemsInRange(5, 5, items);

        assertEquals(8, adapter.getItemCount());
        assertEquals(1, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(3, adapter.getItems().get(2).id);
        assertEquals(4, adapter.getItems().get(3).id);
        assertEquals(5, adapter.getItems().get(4).id);
        assertEquals(6, adapter.getItems().get(5).id);
        assertEquals(7, adapter.getItems().get(6).id);
        assertEquals(10, adapter.getItems().get(7).id);
    }

    @Test
    public void whenIsRangeRefresh_removeTheLastPage() {
        GoblinAdapter adapter = new GoblinAdapter(new ArrayList<GoblinAdapter.Item>());

        ArrayList<GoblinAdapter.Item> items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(1));
        items.add(new GoblinAdapter.Item(2));
        items.add(new GoblinAdapter.Item(3));
        items.add(new GoblinAdapter.Item(4));
        items.add(new GoblinAdapter.Item(5));

        adapter.setItemsInRange(0, 5, items);

        items = new ArrayList<>();
        items.add(new GoblinAdapter.Item(6));
        items.add(new GoblinAdapter.Item(7));
        items.add(new GoblinAdapter.Item(8));
        items.add(new GoblinAdapter.Item(9));
        items.add(new GoblinAdapter.Item(10));
        adapter.setItemsInRange(5, 5, items);

        items = new ArrayList<>();
        adapter.setItemsInRange(5, 5, items);

        assertEquals(5, adapter.getItemCount());
        assertEquals(1, adapter.getItems().get(0).id);
        assertEquals(2, adapter.getItems().get(1).id);
        assertEquals(3, adapter.getItems().get(2).id);
        assertEquals(4, adapter.getItems().get(3).id);
        assertEquals(5, adapter.getItems().get(4).id);
    }
}
