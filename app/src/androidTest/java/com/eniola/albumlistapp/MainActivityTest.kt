package com.eniola.albumlistapp

import androidx.test.espresso.Espresso.onData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.hasEntry
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 18-May-2021
 */


@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainActivityTest {


    @Test
    fun mainActivityTest() {
        onData(allOf(`is`(instanceOf(Map::class.java)), hasEntry(equalTo("quidem molestiae enim"),
            `is`("item: 100"))))
    }

/*
    @Test(expected = PerformException::class)
    fun itemText_doesNotExist() {
        // Attempt to scroll to an item that contains the special text.
        onView(ViewMatchers.withId(R.id.album_list_recyclerview))
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollTo(
                    hasDescendant(withText("not in the list"))
                )
            )
    }
*/


}
