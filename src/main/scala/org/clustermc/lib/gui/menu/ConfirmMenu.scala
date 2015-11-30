package org.clustermc.lib.gui.menu

import io.mazenmc.menuapi.menu.Menu

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class ConfirmMenu(title: String, item: InvItem) extends Menu(title, 9){

  for(x <- 0 to 8){
    setItem(x, item)
  }

}
