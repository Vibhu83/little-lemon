package com.vibhu.littlelemon.ui.navigation


interface Destination {
    val route: String
}

object Destinations {

    object Home: Destination{
        override val route: String = "Home"
    }

    object Profile: Destination{
        override val route: String = "Profile"
    }

    object Onboarding: Destination{
        override val route: String = "Onboarding"
    }
}

