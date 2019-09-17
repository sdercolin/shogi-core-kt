package com.sdercolin.shogicore

import com.sdercolin.shogicore.Piece.Type.*
import com.sdercolin.shogicore.exception.UnreachablePositionException
import kotlin.math.abs

sealed class Piece(val type: Type) {
    abstract val color: Color
    abstract val currentColor: Color
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

    internal abstract val movablePositions: List<Position>
    internal open fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        return listOf()
    }

    protected fun ensureCanMoveTo(position: Position) {
        if (!movablePositions.contains(position)) throw UnreachablePositionException(this, position)
    }

    protected val upwardFactor get() = if (currentColor == Color.BLACK) -1 else 1
}

data class King(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(KING) {

    override val movablePositions: List<Position>
        get() = Position.wholeBoard
            .filter { abs(it.x - position.x) <= 1 && abs(it.y - position.y) <= 1 }
            .minus(position)
}

data class Rook(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(ROOK) {

    override val movablePositions: List<Position>
        get() = Position.wholeBoard
            .filter { it.x == position.x || it.y == position.y }
            .minus(position)

    override fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        val route = if (target.x == this.position.x) {
            (position.y..target.y).map { y -> Position(target.x, y) }
        } else {
            (position.x..target.x).map { x -> Position(x, target.y) }
        }
        return route
            .minus(position)
            .minus(target)
    }
}

data class Dragon(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(DRAGON) {

    override val movablePositions: List<Position>
        get() {
            val basic = Position.wholeBoard
                .filter { it.x == position.x || it.y == position.y }
            val extra = Position.wholeBoard
                .filter { abs(it.x - position.x) <= 1 && abs(it.y - position.y) <= 1 }
            return (basic + extra)
                .distinct()
                .minus(position)
        }

    override fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        val route = when {
            target.x == this.position.x -> (position.y..target.y).map { y -> Position(target.x, y) }
            target.y == this.position.y -> (position.x..target.x).map { x -> Position(x, target.y) }
            else -> listOf(position, target)
        }
        return route
            .minus(position)
            .minus(target)
    }
}

data class Bishop(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(BISHOP) {

    override val movablePositions: List<Position>
        get() = Position.wholeBoard
            .filter { abs(it.x - position.x) == abs(it.y - position.y) }
            .minus(position)

    override fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        val xRoute = position.x..target.x
        val yRoute = position.y..target.y
        return xRoute
            .zip(yRoute) { x, y -> Position(x, y) }
            .minus(position)
            .minus(target)
    }
}

data class Horse(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(HORSE) {

    override val movablePositions: List<Position>
        get() {
            val basic = Position.wholeBoard
                .filter { abs(it.x - position.x) == abs(it.y - position.y) }
            val extra = Position.wholeBoard
                .filter { abs(it.x - position.x) <= 1 && abs(it.y - position.y) <= 1 }
            return (basic + extra)
                .distinct()
                .minus(position)
        }

    override fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        if (abs(target.x - position.x) <= 1 && abs(target.y - position.y) <= 1) return listOf()
        val xRoute = position.x..target.x
        val yRoute = position.y..target.y
        return xRoute
            .zip(yRoute) { x, y -> Position(x, y) }
            .minus(position)
            .minus(target)
    }
}

sealed class GoldLike(type: Type) : Piece(type) {

    override val movablePositions: List<Position>
        get() {
            val basic = Position.wholeBoard
                .filter { abs(it.x - position.x) <= 1 && abs(it.y - position.y) <= 1 }
            val exceptions = listOf(
                position + (-1 to -1 * upwardFactor),
                position + (1 to -1 * upwardFactor)
            )
            return (basic - exceptions)
                .distinct()
                .minus(position)
        }
}

data class Gold(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(GOLD)

data class Silver(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(SILVER) {

    override val movablePositions: List<Position>
        get() {
            val basic = Position.wholeBoard
                .filter { abs(it.x - position.x) <= 1 && abs(it.y - position.y) <= 1 }
            val exceptions = listOf(
                position + (-1 to 0),
                position + (1 to 0),
                position + (0 to -1 * upwardFactor)
            )
            return (basic - exceptions)
                .distinct()
                .minus(position)
        }
}

data class PromotedSilver(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_SILVER)

data class Knight(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(KNIGHT) {

    override val movablePositions: List<Position>
        get() = listOf(
            position + (-1 to 2 * upwardFactor),
            position + (1 to 2 * upwardFactor)
        ).filter { it.isOnBoard }
}

data class PromotedKnight(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_KNIGHT)

data class Lance(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(LANCE) {

    override val movablePositions: List<Position>
        get() = Position.wholeBoard
            .filter { it.x == position.x }
            .filter { (it.y - position.y) * upwardFactor > 0 }

    override fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        return (position.y..target.y)
            .map { y -> Position(position.x, y) }
            .minus(position)
            .minus(target)
    }
}

data class PromotedLance(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_LANCE)

data class Pawn(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(PAWN) {

    override val movablePositions: List<Position>
        get() = listOf(position + (0 to 1 * upwardFactor))
            .filter { it.isOnBoard }
}

data class PromotedPawn(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_PAWN)