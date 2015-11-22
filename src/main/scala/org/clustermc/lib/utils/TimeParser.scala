package org.clustermc.lib.utils

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.regex.{Matcher, Pattern}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object TimeParser {

  val pattern: Pattern = Pattern.compile("(\\d+[wdhms])", Pattern.CASE_INSENSITIVE)

  @throws[NumberFormatException]
  def apply(input: String): Duration = {
    var duration: Duration = Duration.ZERO
    val matcher: Matcher = pattern.matcher(input)

    if(!input.matches(pattern.pattern())){
      throw new NumberFormatException
    }

    while (matcher.find()) {
      val pat: String = matcher.group()
      var length: Long = pat.substring(0, pat.length - 1).toLong

      val unit: ChronoUnit = pat.substring(pat.length - 1) match {
        case "w" =>
          length = length * 7
          ChronoUnit.DAYS
        case "d" => ChronoUnit.DAYS
        case "h" => ChronoUnit.HOURS
        case "m" => ChronoUnit.MINUTES
      }

      duration = duration.plus(length, unit)
    }
    duration
  }


}
