package com.sharapov.core_ui.theme.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


val CustomIcons.Filled.Bookmark: ImageVector
    get() {
        if (_BookmarkFilled != null) return _BookmarkFilled!!

        _BookmarkFilled = ImageVector.Builder(
            name = "bookmark_filled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(19f, 21f)
                lineToRelative(-7f, -5f)
                lineToRelative(-7f, 5f)
                verticalLineTo(5f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, -2f)
                horizontalLineToRelative(10f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, 2f)
                close()
            }
        }.build()

        return _BookmarkFilled!!
    }

private var _BookmarkFilled: ImageVector? = null



val CustomIcons.Filled.KeyboardArrowDown: ImageVector
    get() {
        if (_KeyboardArrowDown != null) return _KeyboardArrowDown!!

        _KeyboardArrowDown = ImageVector.Builder(
            name = "chevron-down",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(6f, 9f)
                lineTo(12f, 15f)
                lineTo(18f, 9f)
            }
        }.build()

        return _KeyboardArrowDown!!
    }

private var _KeyboardArrowDown: ImageVector? = null



val CustomIcons.Filled.Search: ImageVector
    get() {
        if (_Search != null) return _Search!!

        _Search = ImageVector.Builder(
            name = "search",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(380f, 640f)
                quadToRelative(-109f, 0f, -184.5f, -75.5f)
                reflectiveQuadTo(120f, 380f)
                quadToRelative(0f, -109f, 75.5f, -184.5f)
                reflectiveQuadTo(380f, 120f)
                quadToRelative(109f, 0f, 184.5f, 75.5f)
                reflectiveQuadTo(640f, 380f)
                quadToRelative(0f, 44f, -14f, 83f)
                reflectiveQuadToRelative(-38f, 69f)
                lineToRelative(224f, 224f)
                quadToRelative(11f, 11f, 11f, 28f)
                reflectiveQuadToRelative(-11f, 28f)
                quadToRelative(-11f, 11f, -28f, 11f)
                reflectiveQuadToRelative(-28f, -11f)
                lineTo(532f, 588f)
                quadToRelative(-30f, 24f, -69f, 38f)
                reflectiveQuadToRelative(-83f, 14f)
                close()
                moveToRelative(0f, -80f)
                quadToRelative(75f, 0f, 127.5f, -52.5f)
                reflectiveQuadTo(560f, 380f)
                quadToRelative(0f, -75f, -52.5f, -127.5f)
                reflectiveQuadTo(380f, 200f)
                quadToRelative(-75f, 0f, -127.5f, 52.5f)
                reflectiveQuadTo(200f, 380f)
                quadToRelative(0f, 75f, 52.5f, 127.5f)
                reflectiveQuadTo(380f, 560f)
                close()
            }
        }.build()

        return _Search!!
    }

private var _Search: ImageVector? = null



val CustomIcons.Filled.Close: ImageVector
    get() {
        if (_Close != null) return _Close!!

        _Close = ImageVector.Builder(
            name = "close",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(256f, 760f)
                lineToRelative(-56f, -56f)
                lineToRelative(224f, -224f)
                lineToRelative(-224f, -224f)
                lineToRelative(56f, -56f)
                lineToRelative(224f, 224f)
                lineToRelative(224f, -224f)
                lineToRelative(56f, 56f)
                lineToRelative(-224f, 224f)
                lineToRelative(224f, 224f)
                lineToRelative(-56f, 56f)
                lineToRelative(-224f, -224f)
                lineToRelative(-224f, 224f)
                close()
            }
        }.build()

        return _Close!!
    }

private var _Close: ImageVector? = null



val CustomIcons.Filled.Done: ImageVector
    get() {
        if (_Done != null) return _Done!!

        _Done = ImageVector.Builder(
            name = "check",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(20f, 6f)
                lineTo(9f, 17f)
                lineTo(4f, 12f)
            }
        }.build()

        return _Done!!
    }

private var _Done: ImageVector? = null



val CustomIcons.Filled.ArrowDropUp: ImageVector
    get() {
        if (_ArrowDropUp != null) return _ArrowDropUp!!

        _ArrowDropUp = ImageVector.Builder(
            name = "arrow_drop_up",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent)
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(24f)
                verticalLineToRelative(24f)
                horizontalLineTo(0f)
                verticalLineTo(0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(7f, 14f)
                lineToRelative(5f, -5f)
                lineToRelative(5f, 5f)
                horizontalLineTo(7f)
                close()
            }
        }.build()

        return _ArrowDropUp!!
    }

private var _ArrowDropUp: ImageVector? = null



val CustomIcons.Filled.ArrowDropDown: ImageVector
    get() {
        if (_ArrowDropDown != null) return _ArrowDropDown!!

        _ArrowDropDown = ImageVector.Builder(
            name = "arrow_drop_down",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent)
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(24f)
                verticalLineToRelative(24f)
                horizontalLineTo(0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(7f, 10f)
                lineToRelative(5f, 5f)
                lineToRelative(5f, -5f)
                close()
            }
        }.build()

        return _ArrowDropDown!!
    }

private var _ArrowDropDown: ImageVector? = null


val CustomIcons.Filled.Home: ImageVector
    get() {
        if (_Home != null) return _Home!!

        _Home = ImageVector.Builder(
            name = "home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent)
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(24f)
                verticalLineToRelative(24f)
                horizontalLineTo(0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(10f, 20f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(-8f)
                horizontalLineToRelative(3f)
                lineTo(12f, 3f)
                lineTo(2f, 12f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(8f)
                close()
            }
        }.build()

        return _Home!!
    }

private var _Home: ImageVector? = null


val CustomIcons.Filled.Person: ImageVector
    get() {
        if (_BootstrapPerson != null) return _BootstrapPerson!!

        _BootstrapPerson = ImageVector.Builder(
            name = "person",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(3f, 14f)
                reflectiveCurveToRelative(-1f, 0f, -1f, -1f)
                reflectiveCurveToRelative(1f, -4f, 6f, -4f)
                reflectiveCurveToRelative(6f, 3f, 6f, 4f)
                reflectiveCurveToRelative(-1f, 1f, -1f, 1f)
                close()
                moveToRelative(5f, -6f)
                arcToRelative(3f, 3f, 0f, true, false, 0f, -6f)
                arcToRelative(3f, 3f, 0f, false, false, 0f, 6f)
            }
        }.build()

        return _BootstrapPerson!!
    }

private var _BootstrapPerson: ImageVector? = null



val CustomIcons.Filled.Star: ImageVector
    get() {
        if (_LucideStar != null) return _LucideStar!!

        _LucideStar = ImageVector.Builder(
            name = "star",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(11.525f, 2.295f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.95f, 0f)
                lineToRelative(2.31f, 4.679f)
                arcToRelative(2.123f, 2.123f, 0f, false, false, 1.595f, 1.16f)
                lineToRelative(5.166f, 0.756f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.294f, 0.904f)
                lineToRelative(-3.736f, 3.638f)
                arcToRelative(2.123f, 2.123f, 0f, false, false, -0.611f, 1.878f)
                lineToRelative(0.882f, 5.14f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, -0.771f, 0.56f)
                lineToRelative(-4.618f, -2.428f)
                arcToRelative(2.122f, 2.122f, 0f, false, false, -1.973f, 0f)
                lineTo(6.396f, 21.01f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, -0.77f, -0.56f)
                lineToRelative(0.881f, -5.139f)
                arcToRelative(2.122f, 2.122f, 0f, false, false, -0.611f, -1.879f)
                lineTo(2.16f, 9.795f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.294f, -0.906f)
                lineToRelative(5.165f, -0.755f)
                arcToRelative(2.122f, 2.122f, 0f, false, false, 1.597f, -1.16f)
                close()
            }
        }.build()

        return _LucideStar!!
    }

private var _LucideStar: ImageVector? = null



