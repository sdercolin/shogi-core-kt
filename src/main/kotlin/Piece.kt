package com.sdercolin.shogicore

import com.sdercolin.shogicore.Piece.Type.BISHOP
import com.sdercolin.shogicore.Piece.Type.DRAGON
import com.sdercolin.shogicore.Piece.Type.GOLD
import com.sdercolin.shogicore.Piece.Type.HORSE
import com.sdercolin.shogicore.Piece.Type.KING
import com.sdercolin.shogicore.Piece.Type.KNIGHT
import com.sdercolin.shogicore.Piece.Type.LANCE
import com.sdercolin.shogicore.Piece.Type.PAWN
import com.sdercolin.shogicore.Piece.Type.P_KNIGHT
import com.sdercolin.shogicore.Piece.Type.P_LANCE
import com.sdercolin.shogicore.Piece.Type.P_PAWN
import com.sdercolin.shogicore.Piece.Type.P_SILVER
import com.sdercolin.shogicore.Piece.Type.ROOK
import com.sdercolin.shogicore.Piece.Type.SILVER
import com.sdercolin.shogicore.exception.UnreachablePositionException
import kotlin.math.abs

/**
 * The base class of all types of pieces
 */
sealed class Piece(val type: Type) {
    abstract val color: Color
    abstract val currentColor: Color
    abstract val position: Position
    internal abstract val id: Int

    internal fun beTaken(): Piece {
        val newColor = currentColor.next()
        val handPosition = Position.getHandPosition(newColor)
        val taken = type.instantiator(color, newColor, handPosition, id)
        return taken.demote() ?: taken
    }

    internal fun move(target: Position, promote: Boolean): Piece {
        val moved = type.instantiator(color, currentColor, target, id)
        return if (!promote) moved
        else moved.promote()!!
    }

    /**
     * Type of pieces defined by their functions
     */
    enum class Type(internal val instantiator: (Color, Color, Position, Int) -> Piece) {
        /**
         * 王將, 玉將
         */
        KING({ color, currentColor, position, id ->
            King(color, currentColor, position, id)
        }),
        /**
         * 飛車
         */
        ROOK({ color, currentColor, position, id ->
            Rook(color, currentColor, position, id)
        }),
        /**
         * 龍王
         */
        DRAGON({ color, currentColor, position, id ->
            Dragon(color, currentColor, position, id)
        }),
        /**
         * 角行
         */
        BISHOP({ color, currentColor, position, id ->
            Bishop(color, currentColor, position, id)
        }),
        /**
         * 龍馬
         */
        HORSE({ color, currentColor, position, id ->
            Horse(color, currentColor, position, id)
        }),
        /**
         * 金將
         */
        GOLD({ color, currentColor, position, id ->
            Gold(color, currentColor, position, id)
        }),
        /**
         * 銀將
         */
        SILVER({ color, currentColor, position, id ->
            Silver(color, currentColor, position, id)
        }),
        /**
         * 成銀
         */
        P_SILVER({ color, currentColor, position, id ->
            PromotedSilver(color, currentColor, position, id)
        }),
        /**
         * 桂馬
         */
        KNIGHT({ color, currentColor, position, id ->
            Knight(color, currentColor, position, id)
        }),
        /**
         * 成桂
         */
        P_KNIGHT({ color, currentColor, position, id ->
            PromotedKnight(color, currentColor, position, id)
        }),
        /**
         * 香車
         */
        LANCE({ color, currentColor, position, id ->
            Lance(color, currentColor, position, id)
        }),
        /**
         * 成香
         */
        P_LANCE({ color, currentColor, position, id ->
            PromotedLance(color, currentColor, position, id)
        }),
        /**
         * 歩兵
         */
        PAWN({ color, currentColor, position, id ->
            Pawn(color, currentColor, position, id)
        }),
        /**
         * と金
         */
        P_PAWN({ color, currentColor, position, id ->
            PromotedPawn(color, currentColor, position, id)
        }),
    }

    internal abstract val movablePositions: List<Position>
    internal open fun getRouteTo(target: Position): List<Position> {
        ensureCanMoveTo(target)
        return listOf()
    }

    protected fun ensureCanMoveTo(position: Position) {
        if (!movablePositions.contains(position)) throw UnreachablePositionException(this, position)
    }

    protected val upwardFactor get() = if (currentColor == Color.Black) -1 else 1

    internal open fun promote(): Piece? = null
    internal open fun demote(): Piece? = null
}

internal data class King(
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

internal data class Rook(
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
            (position.y towards target.y).map { y -> Position(target.x, y) }
        } else {
            (position.x towards target.x).map { x -> Position(x, target.y) }
        }
        return route
            .minus(position)
            .minus(target)
    }

    override fun promote(): Piece? = Dragon(color, currentColor, position, id)
}

internal data class Dragon(
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
            target.x == this.position.x -> (position.y towards target.y).map { y -> Position(target.x, y) }
            target.y == this.position.y -> (position.x towards target.x).map { x -> Position(x, target.y) }
            else -> listOf(position, target)
        }
        return route
            .minus(position)
            .minus(target)
    }

    override fun demote(): Piece? = Rook(color, currentColor, position, id)
}

internal data class Bishop(
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
        val xRoute = position.x towards target.x
        val yRoute = position.y towards target.y
        return xRoute.zip(yRoute)
            .map { (x, y) -> Position(x, y) }
            .minus(position)
            .minus(target)
    }

    override fun promote(): Piece? = Horse(color, currentColor, position, id)
}

internal data class Horse(
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
        val xRoute = position.x towards target.x
        val yRoute = position.y towards target.y
        return xRoute
            .zip(yRoute) { x, y -> Position(x, y) }
            .minus(position)
            .minus(target)
    }

    override fun demote(): Piece? = Bishop(color, currentColor, position, id)
}

internal sealed class GoldLike(type: Type) : Piece(type) {

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

internal data class Gold(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(GOLD)

internal data class Silver(
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

    override fun promote(): Piece? = PromotedSilver(color, currentColor, position, id)
}

internal data class PromotedSilver(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_SILVER) {

    override fun demote(): Piece? = Silver(color, currentColor, position, id)
}

internal data class Knight(
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

    override fun promote(): Piece? = PromotedKnight(color, currentColor, position, id)
}

internal data class PromotedKnight(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_KNIGHT) {

    override fun demote(): Piece? = Knight(color, currentColor, position, id)
}

internal data class Lance(
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
        return (position.y towards target.y)
            .map { y -> Position(position.x, y) }
            .minus(position)
            .minus(target)
    }

    override fun promote(): Piece? = PromotedLance(color, currentColor, position, id)
}

internal data class PromotedLance(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_LANCE) {

    override fun demote(): Piece? = Lance(color, currentColor, position, id)
}

internal data class Pawn(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : Piece(PAWN) {

    override val movablePositions: List<Position>
        get() = listOf(position + (0 to 1 * upwardFactor))
            .filter { it.isOnBoard }

    override fun promote(): Piece? = PromotedPawn(color, currentColor, position, id)
}

internal data class PromotedPawn(
    override val color: Color,
    override val currentColor: Color,
    override val position: Position,
    override val id: Int
) : GoldLike(P_PAWN) {

    override fun demote(): Piece? = Pawn(color, currentColor, position, id)
}
