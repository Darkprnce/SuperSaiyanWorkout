package com.supersaiyanworkout.ui.customComposables

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.supersaiyanworkout.R

/*
@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    imageUrl: Any,
    error: Int = R.drawable.broken_image,
    placeholder: Int = R.drawable.loading_small,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.respectCacheHeaders(false)
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = imageUrl).apply(block = {
                size(Size.ORIGINAL)
            }).error(error).placeholder(placeholder).build(),
            imageLoader = imageLoader
        ),
        contentScale = contentScale,
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
    )
}
*/


@Composable
fun GifImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    tint: Color? = null,
    error: Int = R.drawable.broken_image,
    placeholder: Int = R.drawable.loading_small,
    contentScale: ContentScale = ContentScale.Fit,
) {
    GlideImage(
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = contentScale,
            colorFilter = if (tint != null) {
                ColorFilter.tint(tint)
            } else {
                null
            }
        ),
        previewPlaceholder = placeholder,
        failure = {
            Image(
                painter =
                painterResource(id = error),
                contentDescription = null,
                colorFilter = if (tint != null) {
                    ColorFilter.tint(tint)
                } else {
                    null
                }
            )
        },
        modifier = modifier.fillMaxWidth(),
    )
}