@file:OptIn(ExperimentalSerializationApi::class)

package com.sharapov.network_anime.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

object FlexibleIntNullableSerializer : KSerializer<Int?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FlexibleIntNullable", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Int? {
        val jsonDecoder = decoder as? JsonDecoder
            ?: return decoder.decodeInt()

        val el = jsonDecoder.decodeJsonElement()
        if (el is JsonNull) return null

        val prim = el.jsonPrimitive
        prim.intOrNull?.let { return it }
        prim.content.toIntOrNull()?.let { return it }

        if (prim.isString && prim.content.isBlank()) return null
        if (prim.isString && prim.content.equals("null", ignoreCase = true)) return null

        throw SerializationException("Expected Int or numeric String, got: $prim")
    }

    override fun serialize(encoder: Encoder, value: Int?) {
        if (value == null) {
            (encoder as? JsonEncoder)?.encodeJsonElement(JsonNull) ?: encoder.encodeNull()
        } else {
            encoder.encodeInt(value)
        }
    }
}
