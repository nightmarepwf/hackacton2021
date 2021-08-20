package com.qavan.app.utils

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.qavan.app.data.model.Item
import com.qavan.app.data.model.Tag
import com.qavan.app.extensions.uppercaseFirst
import timber.log.Timber

object PreviewUtil {

    val imageUrls by lazy {
        listOf(
            "https://unsplash.com/photos/MNXVgXPu8O0/download?force=true&w=1920",
            "https://unsplash.com/photos/uy_V12d-VXU/download?force=true&w=1920",
            "https://unsplash.com/photos/0Xfqeumm_Ok/download?force=true&w=1920",
            "https://unsplash.com/photos/YNliXm_hMn8/download?force=true&w=1920",
            "https://unsplash.com/photos/Yn0l7uwBrpw/download?force=true&w=1920",
            "https://unsplash.com/photos/DjGHuzUh_84/download?force=true&w=1920",
            "https://unsplash.com/photos/7rga6j5vsag/download?force=true&w=1920",
            "https://unsplash.com/photos/bcGl3g3WLoY/download?force=true&w=1920",
            "https://unsplash.com/photos/Gi3iUJ1FwxI/download?force=true&w=1920",
            "https://unsplash.com/photos/3oR1IXTOSTU/download?force=true&w=1920",
            "https://unsplash.com/photos/md37aLkm7Ys/download?force=true&w=1920",
            "https://unsplash.com/photos/49UMO9cbSlI/download?force=true&w=1920",
            "https://unsplash.com/photos/9pO3LgH-9-Y/download?force=true&w=1920",
        )
    }
    fun nextImage() = imageUrls.random()

    private val videosYouTube by lazy {
        listOf(
            "https://www.youtube.com/watch?v=Gh6y3wUzKGQ",//BASSBOOSTED | Смешарики-От винта |
            "https://www.youtube.com/watch?v=FuPvNK9CeQE",//SCP: Секретные лаборатории
            "https://www.youtube.com/watch?v=EpKSV5ihuYM",//Sugar
            "https://www.youtube.com/watch?v=jP-QvRmoJds",//Худший игровой движок
            "https://www.youtube.com/watch?v=SFzARZGA1oM",//Заказ последней видеокарты в DNS
        )
    }
    private fun videoYouTube() = videosYouTube.random()

    private val loremIpsum by lazy { LoremIpsum() }
    val loremIpsumWords by lazy {
        loremIpsum.values
            .toList()[0]
            .split(
                " ",
                ". ",
                ", ",
                "\n",
            )
    }

    fun words(count: Int): List<String> {
        return loremIpsumWords.subList(0, count)
    }

    inline val word: String
        get() = loremIpsumWords.random().uppercaseFirst

    fun generateItems(): ArrayList<Item> {
        Timber.d("generating items")
        val items = ArrayList<Item>()
        (1..56).forEach { _ ->
            val tags = ArrayList<Tag>()
            (0..(0..6).random()).forEach {
                tags.add(
                    Tag(
                        id = it,
                        name = word,
                        color = Color.Transparent.value,
                        paddingStart = if (it == 0) 8 else 0
                    )
                )
            }
            items.add(
                Item(
                    name = words((2..4).random()).joinToString(separator = " "),
                    description = words((2..14).random()).joinToString(separator = " "),
                    images = arrayListOf(Uri.parse(nextImage())),
                    tags = tags,
                )
            )
        }
        return items

    }
}