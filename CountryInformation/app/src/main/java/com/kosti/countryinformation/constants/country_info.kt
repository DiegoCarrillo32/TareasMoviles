package com.kosti.countryinformation.constants
class Country (var name:String, var population:Int, var capital:String) {

}
val countries_info: Map<String, Country> = mapOf(
    "Costa Rica" to Country("Costa Rica", 10, "San Jose"),
    "Colombia" to Country("Colombia", 1000, "Medellin"),
    "San Salvador" to Country("San Salvador", 1234, "San Salvador"),
    "Estados Unidos" to Country("Estados Unidos", 1123124, "Washington"),
    "Republica Dominicana" to Country("Republica Dominicana", 24014, "Santo Domingo"),

)