package com.example.buibinhminh.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.buibinhminh.database.entity.ProfileEntity
import com.example.buibinhminh.database.entity.UserEntity

data class UserProfile(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val profile: ProfileEntity?
)