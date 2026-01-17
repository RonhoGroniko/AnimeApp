package com.sharapov.core_ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CustomIcons.Outlined.Filter: ImageVector
    get() {
        if (_Filter != null) return _Filter!!

        _Filter = ImageVector.Builder(
            name = "filter",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(9.5f, 14f)
                horizontalLineTo(6.5f)
                curveTo(6.224f, 14f, 6f, 13.776f, 6f, 13.5f)
                verticalLineTo(9.329f)
                curveTo(6f, 8.928f, 5.844f, 8.552f, 5.561f, 8.268f)
                lineTo(1.561f, 4.268f)
                curveTo(1.205f, 3.911f, 1f, 3.418f, 1f, 2.914f)
                curveTo(1f, 1.858f, 1.858f, 1f, 2.914f, 1f)
                horizontalLineTo(13.086f)
                curveTo(14.142f, 1f, 15f, 1.858f, 15f, 2.914f)
                curveTo(15f, 3.417f, 14.796f, 3.911f, 14.439f, 4.267f)
                lineTo(10.439f, 8.267f)
                curveTo(10.156f, 8.551f, 10f, 8.927f, 10f, 9.328f)
                verticalLineTo(13.499f)
                curveTo(10f, 13.775f, 9.776f, 13.999f, 9.5f, 13.999f)
                verticalLineTo(14f)
                close()
                moveTo(7f, 13f)
                horizontalLineTo(9f)
                verticalLineTo(9.329f)
                curveTo(9f, 8.661f, 9.26f, 8.033f, 9.732f, 7.561f)
                lineTo(13.732f, 3.561f)
                curveTo(13.902f, 3.391f, 14f, 3.155f, 14f, 2.915f)
                curveTo(14f, 2.411f, 13.59f, 2.001f, 13.086f, 2.001f)
                horizontalLineTo(2.914f)
                curveTo(2.41f, 2.001f, 2f, 2.411f, 2f, 2.915f)
                curveTo(2f, 3.155f, 2.098f, 3.391f, 2.268f, 3.562f)
                lineTo(6.268f, 7.562f)
                curveTo(6.741f, 8.034f, 7f, 8.662f, 7f, 9.33f)
                verticalLineTo(13.001f)
                verticalLineTo(13f)
                close()
            }
        }.build()

        return _Filter!!
    }

private var _Filter: ImageVector? = null


val CustomIcons.Outlined.Settings: ImageVector
    get() {
        if (_Settings != null) return _Settings!!

        _Settings = ImageVector.Builder(
            name = "settings",
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
                moveTo(15f, 12f)
                arcTo(3f, 3f, 0f, false, true, 12f, 15f)
                arcTo(3f, 3f, 0f, false, true, 9f, 12f)
                arcTo(3f, 3f, 0f, false, true, 15f, 12f)
                close()
            }
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(19.4f, 15f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 0.33f, 1.82f)
                lineToRelative(0.06f, 0.06f)
                arcToRelative(2f, 2f, 0f, false, true, 0f, 2.83f)
                arcToRelative(2f, 2f, 0f, false, true, -2.83f, 0f)
                lineToRelative(-0.06f, -0.06f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -1.82f, -0.33f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -1f, 1.51f)
                verticalLineTo(21f)
                arcToRelative(2f, 2f, 0f, false, true, -2f, 2f)
                arcToRelative(2f, 2f, 0f, false, true, -2f, -2f)
                verticalLineToRelative(-0.09f)
                arcTo(1.65f, 1.65f, 0f, false, false, 9f, 19.4f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -1.82f, 0.33f)
                lineToRelative(-0.06f, 0.06f)
                arcToRelative(2f, 2f, 0f, false, true, -2.83f, 0f)
                arcToRelative(2f, 2f, 0f, false, true, 0f, -2.83f)
                lineToRelative(0.06f, -0.06f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 0.33f, -1.82f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -1.51f, -1f)
                horizontalLineTo(3f)
                arcToRelative(2f, 2f, 0f, false, true, -2f, -2f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, -2f)
                horizontalLineToRelative(0.09f)
                arcTo(1.65f, 1.65f, 0f, false, false, 4.6f, 9f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -0.33f, -1.82f)
                lineToRelative(-0.06f, -0.06f)
                arcToRelative(2f, 2f, 0f, false, true, 0f, -2.83f)
                arcToRelative(2f, 2f, 0f, false, true, 2.83f, 0f)
                lineToRelative(0.06f, 0.06f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 1.82f, 0.33f)
                horizontalLineTo(9f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 1f, -1.51f)
                verticalLineTo(3f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, -2f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, 2f)
                verticalLineToRelative(0.09f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 1f, 1.51f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 1.82f, -0.33f)
                lineToRelative(0.06f, -0.06f)
                arcToRelative(2f, 2f, 0f, false, true, 2.83f, 0f)
                arcToRelative(2f, 2f, 0f, false, true, 0f, 2.83f)
                lineToRelative(-0.06f, 0.06f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -0.33f, 1.82f)
                verticalLineTo(9f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, 1.51f, 1f)
                horizontalLineTo(21f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, 2f)
                arcToRelative(2f, 2f, 0f, false, true, -2f, 2f)
                horizontalLineToRelative(-0.09f)
                arcToRelative(1.65f, 1.65f, 0f, false, false, -1.51f, 1f)
                close()
            }
        }.build()

        return _Settings!!
    }

private var _Settings: ImageVector? = null


val CustomIcons.Outlined.Bookmark: ImageVector
    get() {
        if (_Bookmark != null) return _Bookmark!!

        _Bookmark = ImageVector.Builder(
            name = "bookmark",
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

        return _Bookmark!!
    }

private var _Bookmark: ImageVector? = null



val CustomIcons.Outlined.Delete: ImageVector
    get() {
        if (_MaterialIconsDelete != null) return _MaterialIconsDelete!!

        _MaterialIconsDelete = ImageVector.Builder(
            name = "delete",
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
                moveTo(16f, 9f)
                verticalLineToRelative(10f)
                horizontalLineTo(8f)
                verticalLineTo(9f)
                horizontalLineToRelative(8f)
                moveToRelative(-1.5f, -6f)
                horizontalLineToRelative(-5f)
                lineToRelative(-1f, 1f)
                horizontalLineTo(5f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(14f)
                verticalLineTo(4f)
                horizontalLineToRelative(-3.5f)
                lineToRelative(-1f, -1f)
                close()
                moveTo(18f, 7f)
                horizontalLineTo(6f)
                verticalLineToRelative(12f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(8f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                verticalLineTo(7f)
                close()
            }
        }.build()

        return _MaterialIconsDelete!!
    }

private var _MaterialIconsDelete: ImageVector? = null



val CustomIcons.Outlined.Home: ImageVector
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
                verticalLineTo(0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(12f, 5.69f)
                lineToRelative(5f, 4.5f)
                verticalLineTo(18f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-6f)
                horizontalLineTo(9f)
                verticalLineToRelative(6f)
                horizontalLineTo(7f)
                verticalLineToRelative(-7.81f)
                lineToRelative(5f, -4.5f)
                moveTo(12f, 3f)
                lineTo(2f, 12f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(8f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(-8f)
                horizontalLineToRelative(3f)
                lineTo(12f, 3f)
                close()
            }
        }.build()

        return _Home!!
    }

private var _Home: ImageVector? = null


val CustomIcons.Outlined.Person: ImageVector
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
                moveTo(8f, 8f)
                arcToRelative(3f, 3f, 0f, true, false, 0f, -6f)
                arcToRelative(3f, 3f, 0f, false, false, 0f, 6f)
                moveToRelative(2f, -3f)
                arcToRelative(2f, 2f, 0f, true, true, -4f, 0f)
                arcToRelative(2f, 2f, 0f, false, true, 4f, 0f)
                moveToRelative(4f, 8f)
                curveToRelative(0f, 1f, -1f, 1f, -1f, 1f)
                horizontalLineTo(3f)
                reflectiveCurveToRelative(-1f, 0f, -1f, -1f)
                reflectiveCurveToRelative(1f, -4f, 6f, -4f)
                reflectiveCurveToRelative(6f, 3f, 6f, 4f)
                moveToRelative(-1f, -0.004f)
                curveToRelative(-0.001f, -0.246f, -0.154f, -0.986f, -0.832f, -1.664f)
                curveTo(11.516f, 10.68f, 10.289f, 10f, 8f, 10f)
                reflectiveCurveToRelative(-3.516f, 0.68f, -4.168f, 1.332f)
                curveToRelative(-0.678f, 0.678f, -0.83f, 1.418f, -0.832f, 1.664f)
                close()
            }
        }.build()

        return _BootstrapPerson!!
    }

private var _BootstrapPerson: ImageVector? = null

