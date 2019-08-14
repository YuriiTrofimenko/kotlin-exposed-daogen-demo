package org.tyaa.kotlin.exposed.daogen

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.dao.*
import kotlinx.serialization.*
import kotlinx.serialization.internal.HexConverter
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.internal.SerialClassDescImpl

class Team(id: EntityID<Int>) : IntEntity(id) {

    @Serializer(Team::class)
    companion object : IntEntityClass<Team>(Teams), KSerializer<Team>{
        override val descriptor: SerialDescriptor = object : SerialClassDescImpl("Team") {
            init{
                addElement("id")
                addElement("name")
            }
        }

        override fun serialize(output: Encoder, obj: Team) {
            val compositeOutput: CompositeEncoder = output.beginStructure(descriptor)
            compositeOutput.encodeStringElement(descriptor, 0, HexConverter.printHexBinary(obj.id.toString().toByteArray()))
            compositeOutput.encodeStringElement(descriptor, 1, HexConverter.printHexBinary(obj.name.toString().toByteArray()))
            compositeOutput.endStructure(descriptor)
        }

        override fun deserialize(input: Decoder): Team {
            val inp: CompositeDecoder = input.beginStructure(descriptor)
            var id: Int? = null
            loop@ while (true) {
                when (val i = inp.decodeElementIndex(descriptor)) {
                    CompositeDecoder.READ_DONE -> break@loop
                    0 -> id = HexConverter.parseHexBinary(inp.decodeStringElement(descriptor, i)).toString().toInt()
                    else -> if (i < descriptor.elementsCount) continue@loop else throw SerializationException("Unknown index $i")
                }
            }

            inp.endStructure(descriptor)
            if(id == null)
                throw SerializationException("Id 'id' @ index 0 not found")
            else
                return Team[id]
        }

        fun serializer(): KSerializer<Team> = this
    }

    // Database Columns

    // val id by Teams.id
    val name by Teams.name


    // Helper Methods

    override fun equals(other: Any?): Boolean {
        if(other == null || other !is Team)
            return false
        return id == other.id
    }


    // override fun hashCode() = id


    override fun toString() = name
}