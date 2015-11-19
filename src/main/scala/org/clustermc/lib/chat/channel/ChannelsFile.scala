package org.clustermc.lib.chat.channel

import java.io.{File, FileReader}
import java.lang.reflect.Type

import com.google.gson._
import com.google.gson.reflect.TypeToken
import org.bukkit.plugin.java.JavaPlugin


/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

@throws[IllegalArgumentException] //TODO Cleanup
class ChannelsFile(val plugin: JavaPlugin, val name: String) {

    val file = new File(plugin.getDataFolder, s"$name.json")
    val exists = classOf[Channel].getClassLoader.getResource(file.getName) != null
    if(!file.exists) {
        if(exists) {
            plugin.getLogger.info("Attempting to save resource: " + file.getName)
            plugin.saveResource(file.getName, true)
        } else {
            file.createNewFile()
        }
    }
    val builder = new GsonBuilder().setPrettyPrinting()
    OptionSerializer.register(builder)
    val gson = builder.create()
    val channelType = new TypeToken[Array[Channel]]() {}.getType

    def load(): Array[Channel] = {
        gson.fromJson[Array[Channel]](new FileReader(file), classOf[Array[Channel]])
    }
}

class OptionSerializer extends JsonSerializer[Option[Any]] with JsonDeserializer[Option[Any]] {
    def serialize(src: Option[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
        val jsonObject = new JsonObject
        if(src.isDefined) {
            def value = src.get
            jsonObject.addProperty("class", value.asInstanceOf[Object].getClass.getName)
            jsonObject.add("value", context.serialize(value))
        }
        jsonObject
    }

    def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Option[Any] = {
        if(json.isJsonNull) {
            None
        } else if(json.isJsonObject && json.getAsJsonObject.entrySet().size() == 0) {
            None
        } else {
            val className = json.getAsJsonObject.get("class").getAsString
            Option(context.deserialize(json.getAsJsonObject.get("value"), Class.forName(className)))
        }
    }
}

object OptionSerializer {
    def register(builder: GsonBuilder) = {
        builder.registerTypeAdapter(classOf[Option[Any]], new OptionSerializer)
    }
}
