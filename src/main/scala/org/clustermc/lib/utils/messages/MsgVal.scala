package org.clustermc.lib.utils.messages

final case class MsgVal(key: String) extends AnyVal {

    def join(msg: MsgVal): MsgVal = MsgVal(key + "." + msg.key)

    override def toString = key

    def +(msg1: MsgVal): MsgVal = join(msg1)
}

object MsgVal {

    def join(msgs: MsgVal*) = {
        msgs.mkString(".")
    }


    val test = MsgVal("lol")
    val test1 = MsgVal("san")
    val test2 = MsgVal("knj")

    def main(args: Array[String]) = {
        println(join(test, test1, test2))
        println(test + test1 + test2)
        println(test join test1 join test2)
    }
}
