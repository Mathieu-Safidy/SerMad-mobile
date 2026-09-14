package com.example.demarches.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demarches.data.local.dao.*
import com.example.demarches.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        DemandeEntity::class,
        NotificationEntity::class,
        AdministrationEntity::class,
        LocalisationAdmEntity::class,
        DocumentEntity::class,
        StatutDemandeEntity::class,
        ProcedureMereEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun demandeDao(): DemandeDao
    abstract fun notificationDao(): NotificationDao
    abstract fun administrationDao(): AdministrationDao
    abstract fun localisationDao(): LocalisationDao
    abstract fun documentDao(): DocumentDao
}
