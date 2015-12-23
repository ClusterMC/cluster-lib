package org.clustermc.lib.xserver.receivers

import java.util.UUID

import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.xserver.XMessageReceiver

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object SaveReceiver extends XMessageReceiver{
    override def receive(message: String): Unit = {
        val uuid = UUID.fromString(message)
        if(LibPlayer.loaded(uuid)){
            LibPlayer(uuid).save(LibPlayer.collection())
        }else{
            System.out.println(
                "WELL FUCK. \n" +
                "SOMETHING WENT WRONG HERE... \n" +
                "BLAME RICH.. \n" +
                "...FUCK")
        }
    }
}
