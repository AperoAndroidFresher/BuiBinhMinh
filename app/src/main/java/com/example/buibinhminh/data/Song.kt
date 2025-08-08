package com.example.buibinhminh.data

import android.net.Uri
import com.example.buibinhminh.database.entity.SongEntity
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import androidx.core.net.toUri
import kotlinx.serialization.descriptors.PrimitiveKind

@Serializable
data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val albumId: Long,
    @Serializable(with = UriSerializer::class)
    val contentUri: Uri
)

fun Song.toSongEntity(): SongEntity {
    return SongEntity(
        id = this.id,
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        albumId = this.albumId,
        contentUri = this.contentUri
    )
}

object UriSerializer : KSerializer<Uri> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uri {
        return decoder.decodeString().toUri()
    }
}