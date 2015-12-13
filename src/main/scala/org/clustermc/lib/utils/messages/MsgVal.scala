package org.clustermc.lib.utils.messages

case class MsgVal(key: String, parameters: List[MsgVar]) {

    def this(key: String, parameters: MsgVar*) =
        this(key, parameters)

    def node(leaf: String): String =
        key + "." + leaf

    def join(msg: MsgVal, withParam: Boolean = false): MsgVal =
        new MsgVal(key + "." + msg.key, if(withParam) msg.parameters ::: parameters.toList else Nil)

    def join(msg: String, withParam: Boolean = false): MsgVal =
        new MsgVal(key + "." + msg, if(withParam) parameters else Nil)

    def get: String = Messages(key, parameters)
}

object MsgVal {
    def apply(key: String, parameters: MsgVar*) = apply(key, parameters.toList)
    def apply(key: String) = apply(key, Nil)
    def apply(key: String, parameters: List[MsgVar]): MsgVal = new MsgVal(key, parameters)
}
