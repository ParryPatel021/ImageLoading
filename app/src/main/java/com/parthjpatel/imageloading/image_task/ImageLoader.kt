package com.parthjpatel.imageloading.image_task

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ImageLoader(context: Context) {

    private val cacheDir: File = context.cacheDir

    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    val memoryCacheSize = maxMemory / 8

    private val memoryCache: LruCache<String, Bitmap> =
        object : LruCache<String, Bitmap>(memoryCacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount / 1024
            }
        }

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    fun loadImageFromUrl(url: String, callback: (Bitmap?) -> Unit) {
        val fileName = url.hashCode().toString() + ".jpg"
        val cachedBitmap = getBitmapFromMemoryCache(fileName)
        if (cachedBitmap != null) {
            // Image found in memory cache
            callback(cachedBitmap)
        } else {
            // Load image asynchronously
            CoroutineScope(Dispatchers.IO).launch {
                val fetchedBitmap = fetchBitmapFromUrl(url, fileName)
                if (fetchedBitmap != null) {
                    addBitmapToMemoryCache(fileName, fetchedBitmap)
                    withContext(Dispatchers.Main) {
                        callback(fetchedBitmap)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(null)
                    }
                }
            }
        }
    }

    private fun getBitmapFromMemoryCache(key: String): Bitmap? {
        return memoryCache.get(key)
    }

    private fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap)
        }
    }

    //Disk Cache
    private fun saveBitmapToDiskCache(bitmap: Bitmap, fileName: String) {
        try {
            val file = File(cacheDir, fileName)
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadBitmapFromDiskCache(fileName: String): Bitmap? {
        val file = File(cacheDir, fileName)
        if (file.exists()) {
            val fileInputStream = FileInputStream(file)
            val bitmap = BitmapFactory.decodeStream(fileInputStream)
            fileInputStream.close()
            return bitmap
        }
        return null
    }

    private fun fetchBitmapFromUrl(url: String, fileName: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            // First, try to load bitmap from disk cache
            bitmap = loadBitmapFromDiskCache(fileName)
            if (bitmap == null) {
                // If not found in disk cache, download it from the URL
                val imageUrl = URL(url)
                val connection = imageUrl.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
                // Save bitmap to disk cache
                saveBitmapToDiskCache(bitmap, fileName)
                inputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

}