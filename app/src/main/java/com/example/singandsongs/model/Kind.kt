package com.example.singandsongs.model

enum class Kind (val text: String, val color: String) {
    ADVENT("Pieśni Adwetowe", "#380A2E"),
    CAROLS("Pieśni na Boże Narodzenie", "#ff45ac"),
    THE_GREAT_FAST("Pieśni Wielkopostne", "#ad1111"),
    EASTER("Pieśni  Wielkanocne", "#ffd700"),
    EUCHARISTIC("Pieśni Eucharystyczne", "#FFFFFF"),
    TO_THE_HEART("Pieśni Do Serca Pana Jezusa", "#b81414"),
    ACCIDENTAL("Pieśni Przygodne", "#008000"),
    TO_THE_MOTHER_OF_GOD("Pieśni do Matki Bożej", "#4169e1")
}