package com.sdercolin.shogicore

import com.sdercolin.shogicore.Piece.Type.*

sealed class Piece(val type: Type) {
    abstract val color: Color
    abstract val position: Position
    internal abstract val id: Int

    enum class Type {
        KING, // 王將, 玉將
        ROOK, // 飛車
        DRAGON, // 龍王
        BISHOP, // 角行
        HORSE, // 龍馬
        GOLD, // 金將
        SILVER, // 銀將
        P_SILVER, // 成銀
        KNIGHT, // 桂馬
        P_KNIGHT, // 成桂
        LANCE, // 香車
        P_LANCE, // 成香
        PAWN, // 歩兵
        P_PAWN, // と金
    }
}

data class King(override val color: Color, override val position: Position, override val id: Int) : Piece(KING) {

}

data class Rook(override val color: Color, override val position: Position, override val id: Int) : Piece(ROOK) {

}

data class Dragon(override val color: Color, override val position: Position, override val id: Int) : Piece(DRAGON) {

}

data class Bishop(override val color: Color, override val position: Position, override val id: Int) : Piece(BISHOP) {

}

data class Horse(override val color: Color, override val position: Position, override val id: Int) : Piece(HORSE) {

}

data class Gold(override val color: Color, override val position: Position, override val id: Int) : Piece(GOLD) {

}

data class Silver(override val color: Color, override val position: Position, override val id: Int) : Piece(SILVER) {

}

data class PromotedSilver(override val color: Color, override val position: Position, override val id: Int) :
    Piece(P_SILVER) {

}

data class Knight(override val color: Color, override val position: Position, override val id: Int) : Piece(KNIGHT) {

}

data class PromotedKnight(override val color: Color, override val position: Position, override val id: Int) :
    Piece(P_KNIGHT) {

}

data class Lance(override val color: Color, override val position: Position, override val id: Int) : Piece(LANCE) {

}

data class PromotedLance(override val color: Color, override val position: Position, override val id: Int) :
    Piece(P_LANCE) {

}

data class Pawn(override val color: Color, override val position: Position, override val id: Int) : Piece(PAWN) {

}

data class PromotedPawn(override val color: Color, override val position: Position, override val id: Int) :
    Piece(P_PAWN) {

}