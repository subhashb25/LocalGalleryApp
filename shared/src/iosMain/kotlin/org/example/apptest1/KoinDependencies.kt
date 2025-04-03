package org.example.apptest1

import org.example.apptest1.data.MuseumRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies : KoinComponent {
    val museumRepository: MuseumRepository by inject()
}