package com.mkabore.yt.data.db.entity
/**
 * Created by @author mkabore
 * 2020-10-25
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "token") val token: String?,
    @ColumnInfo(name = "auth_code") val authCode: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?
)