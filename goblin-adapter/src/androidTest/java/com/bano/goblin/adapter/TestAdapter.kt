package com.bano.goblin.adapter


import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class TestAdapter {

    @Test
    fun whenAdapterIsEmpty() {
        val adapter = GoblinAdapter(ArrayList())

        val items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.items = items

        assertEquals(5, adapter.itemCount)
        assertEquals(1, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(3, adapter.items[2].id)
        assertEquals(4, adapter.items[3].id)
        assertEquals(5, adapter.items[4].id)
    }

    @Test
    fun whenIsAllRefreshAndCameLessItems() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.items = items

        items = ArrayList()
        items.add(GoblinAdapter.Item(20))
        items.add(GoblinAdapter.Item(21))
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(43))

        adapter.items = items

        assertEquals(4, adapter.itemCount)
        assertEquals(20, adapter.items[0].id)
        assertEquals(21, adapter.items[1].id)
        assertEquals(1, adapter.items[2].id)
        assertEquals(43, adapter.items[3].id)
    }

    @Test
    fun whenIsAllRefreshAndCameLessItems2() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.items = items

        items = ArrayList()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))

        adapter.items = items

        assertEquals(3, adapter.itemCount)
        assertEquals(1, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(3, adapter.items[2].id)
    }

    @Test
    fun whenIsAllRefreshAndCameMoreItems() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.items = items

        items = ArrayList()
        items.add(GoblinAdapter.Item(20))
        items.add(GoblinAdapter.Item(21))
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(43))
        items.add(GoblinAdapter.Item(431))
        items.add(GoblinAdapter.Item(432))
        items.add(GoblinAdapter.Item(433))

        adapter.items = items

        assertEquals(7, adapter.itemCount)
        assertEquals(20, adapter.items[0].id)
        assertEquals(21, adapter.items[1].id)
        assertEquals(1, adapter.items[2].id)
        assertEquals(43, adapter.items[3].id)
        assertEquals(431, adapter.items[4].id)
        assertEquals(432, adapter.items[5].id)
        assertEquals(433, adapter.items[6].id)
    }

    @Test
    fun whenIsAllRefreshAndCameMoreItems2() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.items = items

        items = ArrayList()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))
        items.add(GoblinAdapter.Item(6))
        items.add(GoblinAdapter.Item(7))

        adapter.items = items

        assertEquals(7, adapter.itemCount)
        assertEquals(1, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(3, adapter.items[2].id)
        assertEquals(4, adapter.items[3].id)
        assertEquals(5, adapter.items[4].id)
        assertEquals(6, adapter.items[5].id)
        assertEquals(7, adapter.items[6].id)
    }

    @Test
    fun whenIsAllRefreshWithDifferentOrder() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.items = items

        items = ArrayList()
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(5))
        items.add(GoblinAdapter.Item(4))

        adapter.items = items

        assertEquals(5, adapter.itemCount)
        assertEquals(2, adapter.items[0].id)
        assertEquals(3, adapter.items[1].id)
        assertEquals(1, adapter.items[2].id)
        assertEquals(5, adapter.items[3].id)
        assertEquals(4, adapter.items[4].id)
    }

    @Test
    fun whenIsRangeRefreshWithDifferentOrder() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(0, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(5))
        items.add(GoblinAdapter.Item(4))

        adapter.setItemsInRange(0, 5, items)

        assertEquals(5, adapter.itemCount)
        assertEquals(2, adapter.items[0].id)
        assertEquals(3, adapter.items[1].id)
        assertEquals(1, adapter.items[2].id)
        assertEquals(5, adapter.items[3].id)
        assertEquals(4, adapter.items[4].id)
    }

    @Test
    fun whenIsRangeRefreshAndItemCameDifferentOrder() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(0, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(20))
        items.add(GoblinAdapter.Item(21))
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(43))
        items.add(GoblinAdapter.Item(431))

        adapter.setItemsInRange(5, 5, items)

        assertEquals(9, adapter.itemCount)
        assertEquals(2, adapter.items[0].id)
        assertEquals(3, adapter.items[1].id)
        assertEquals(4, adapter.items[2].id)
        assertEquals(5, adapter.items[3].id)
        assertEquals(20, adapter.items[4].id)
        assertEquals(21, adapter.items[5].id)
        assertEquals(1, adapter.items[6].id)
        assertEquals(43, adapter.items[7].id)
        assertEquals(431, adapter.items[8].id)
    }

    @Test
    fun whenIsRangeRefreshAndItemCameDifferentOrder2() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(0, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(5, 5, items)

        assertEquals(5, adapter.itemCount)
        assertEquals(3, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(1, adapter.items[2].id)
        assertEquals(4, adapter.items[3].id)
        assertEquals(5, adapter.items[4].id)
    }

    @Test
    fun whenIsRangeRefreshWithLessItems() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(0, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(5))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(1))

        adapter.setItemsInRange(0, 5, items)

        assertEquals(3, adapter.itemCount)
        assertEquals(5, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(1, adapter.items[2].id)
    }

    @Test
    fun whenIsRangeRefreshWithLessItems2() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(0, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(6))
        items.add(GoblinAdapter.Item(7))
        items.add(GoblinAdapter.Item(8))
        items.add(GoblinAdapter.Item(9))
        items.add(GoblinAdapter.Item(10))
        adapter.setItemsInRange(5, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(6))
        items.add(GoblinAdapter.Item(7))
        items.add(GoblinAdapter.Item(10))

        adapter.setItemsInRange(5, 5, items)

        assertEquals(8, adapter.itemCount)
        assertEquals(1, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(3, adapter.items[2].id)
        assertEquals(4, adapter.items[3].id)
        assertEquals(5, adapter.items[4].id)
        assertEquals(6, adapter.items[5].id)
        assertEquals(7, adapter.items[6].id)
        assertEquals(10, adapter.items[7].id)
    }

    @Test
    fun whenIsRangeRefresh_removeTheLastPage() {
        val adapter = GoblinAdapter(ArrayList())

        var items = ArrayList<GoblinAdapter.Item>()
        items.add(GoblinAdapter.Item(1))
        items.add(GoblinAdapter.Item(2))
        items.add(GoblinAdapter.Item(3))
        items.add(GoblinAdapter.Item(4))
        items.add(GoblinAdapter.Item(5))

        adapter.setItemsInRange(0, 5, items)

        items = ArrayList()
        items.add(GoblinAdapter.Item(6))
        items.add(GoblinAdapter.Item(7))
        items.add(GoblinAdapter.Item(8))
        items.add(GoblinAdapter.Item(9))
        items.add(GoblinAdapter.Item(10))
        adapter.setItemsInRange(5, 5, items)

        items = ArrayList()
        adapter.setItemsInRange(5, 5, items)

        assertEquals(5, adapter.itemCount)
        assertEquals(1, adapter.items[0].id)
        assertEquals(2, adapter.items[1].id)
        assertEquals(3, adapter.items[2].id)
        assertEquals(4, adapter.items[3].id)
        assertEquals(5, adapter.items[4].id)
    }
}
