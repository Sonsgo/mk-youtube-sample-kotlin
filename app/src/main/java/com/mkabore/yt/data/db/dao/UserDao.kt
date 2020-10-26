package com.mkabore.yt.data.db.dao

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mkabore.yt.data.db.entity.User



@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insertAll(users: List<User>)

    @Delete
    suspend fun delete(user: User)

}