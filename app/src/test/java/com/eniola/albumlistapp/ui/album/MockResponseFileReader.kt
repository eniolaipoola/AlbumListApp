package com.eniola.albumlistapp.ui.album

import java.io.InputStreamReader

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 18-May-2021
 */

class MockResponseFileReader(path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}