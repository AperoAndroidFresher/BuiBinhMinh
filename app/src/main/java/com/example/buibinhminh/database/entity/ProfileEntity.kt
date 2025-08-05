package com.example.buibinhminh.database.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProfileEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val name: String = "",
    val phoneNumber: String = "",
    val universityName: String = "",
    val description : String = "",
    val imageUri: Uri? = null
)