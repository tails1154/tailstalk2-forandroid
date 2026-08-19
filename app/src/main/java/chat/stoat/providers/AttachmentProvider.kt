package com.tails1154.tailstalk2.providers

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.tails1154.tailstalk2.BuildConfig
import com.tails1154.tailstalk2.R
import com.tails1154.tailstalk2.api.StoatHttp
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import java.io.File

class AttachmentProvider : FileProvider(R.xml.file_paths)

suspend fun getAttachmentContentUri(
    context: Context,
    resourceUrl: String,
    id: String,
    filename: String
): Uri {
    val attachmentsDir = File(context.cacheDir, "attachments")
    if (!attachmentsDir.exists()) {
        attachmentsDir.mkdir()
    }

    val response = StoatHttp.get(resourceUrl)
    val file = File(attachmentsDir, "$id-$filename")
    file.writeBytes(response.readBytes())

    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        file
    )
}
