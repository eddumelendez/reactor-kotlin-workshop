package io.eddumelendez.reactorkotlin.domain

data class User(
        val username: String,
        val firstname: String?,
        val lastname: String?) {

    companion object {

        @JvmStatic
        val SKYLER = User("swhite", "Skyler", "White")

        @JvmStatic
        val JESSE = User("jpinkman", "Jesse", "Pinkman")

        @JvmStatic
        val WALTER = User("wwhite", "Walter", "White")

        @JvmStatic
        val SAUL = User("sgoodman", "Saul", "Goodman")

    }

}
