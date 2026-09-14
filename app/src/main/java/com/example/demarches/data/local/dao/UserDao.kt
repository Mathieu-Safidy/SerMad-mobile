package com.example.demarches.data.local.dao

import androidx.room.*
import com.example.demarches.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM User_ LIMIT 1")
    suspend fun getCurrentUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM User_")
    suspend fun deleteAll()
}
