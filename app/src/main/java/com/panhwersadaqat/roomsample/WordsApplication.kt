package com.panhwersadaqat.roomsample

import android.app.Application
import com.panhwersadaqat.roomsample.room.WordRepository
import com.panhwersadaqat.roomsample.room.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}