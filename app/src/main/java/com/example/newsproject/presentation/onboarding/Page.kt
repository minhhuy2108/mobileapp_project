package com.example.newsproject.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.newsproject.R


data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "ABC News",
        description = "ABC News is a leading news organization that provides comprehensive coverage" +
                " of current events and breaking news from around the world. With a commitment to " +
                "delivering accurate and timely information, ABC News offers in-depth reporting and " +
                "analysis on a wide range of topics, including politics, global affairs, business, technology," +
                " and entertainment. ",
        image = R.drawable.abc
    ),Page(
        title = "BBC News",
        description = "BBC News is a globally recognized news organization known for its " +
                "comprehensive and impartial reporting. As the news division of the British " +
                "Broadcasting Corporation (BBC), it delivers in-depth coverage of international " +
                "and local events, spanning a wide array of topics including politics, business, " +
                "technology, culture, and more.",
        image = R.drawable.bbc
    ),Page(
        title = "Al Jazeera English",
        description = "\n" +
                "Al Jazeera English is a prominent international news channel that offers " +
                "comprehensive coverage of global events with a unique perspective. " +
                "As part of the Al Jazeera Media Network, it is known for its in-depth reporting," +
                " investigative journalism, and commitment to presenting diverse viewpoints. " +
                "Covering a wide range of topics including politics, economics, culture, " +
                "and human rights, Al Jazeera English aims to provide a voice to underreported" +
                " regions and stories." ,
        image = R.drawable.jazeera
    )
)