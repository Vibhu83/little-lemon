package com.vibhu.littlelemon.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.vibhu.littlelemon.ui.entities.MenuItemNetwork
import com.vibhu.littlelemon.ui.entities.MenuItemRoom
import com.vibhu.littlelemon.ui.entities.MenuItemRoom.Companion.isNotTheSameAs
import com.vibhu.littlelemon.ui.entities.MenuNetwork
import com.vibhu.littlelemon.ui.network.LittleLemonHttpClient
import com.vibhu.littlelemon.ui.room.LittleLemonDatabase
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.security.MessageDigest

class MenuViewModel(
    private val database: LittleLemonDatabase,
): ViewModel() {


    var menuList: LiveData<List<MenuItemRoom>> = database.menuItemDao().getAll()

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    fun loadRemoteMenu(context: Context, currentMenuList: List<MenuItemRoom>){
        viewModelScope.launch {
            val response  = fetchMenu()
            val list = response.map { menuItemNetwork ->
                var currentMenuItem = menuItemNetwork.toMenuItemRoom()
                val databaseMenuItem = currentMenuList.firstOrNull { menuItemRoom ->  menuItemRoom.id == currentMenuItem.id }
                val currentMenuItemImageByteArray = getRemoteImageByteArray(currentMenuItem.imageUrl)
                currentMenuItem = if (currentMenuItemImageByteArray==null){
                    currentMenuItem.copy(imageHash = "", imageLocalPath = "")
                }
                else{
                    val imageHash = hashBytes(currentMenuItemImageByteArray)
                    val filePath =
                        if(databaseMenuItem==null || databaseMenuItem.imageHash!=imageHash)
                            createImageFileFromBytes(currentMenuItemImageByteArray, "${currentMenuItem.title} image.jpg", context)
                        else
                            databaseMenuItem.imageLocalPath
                    currentMenuItem.copy(
                        imageHash = imageHash,
                        imageLocalPath = filePath
                    )
                }
                if (databaseMenuItem==null || databaseMenuItem.isNotTheSameAs(currentMenuItem)){
                    withContext(Dispatchers.IO) {
                        database.menuItemDao()
                            .insert(currentMenuItem.copy(dataLastUpdated = System.currentTimeMillis()))
                    }

                }
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return withContext(Dispatchers.IO + coroutineExceptionHandler) {
            val response: MenuNetwork = LittleLemonHttpClient.instance.get(
                "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
            ).body<MenuNetwork>()
            return@withContext response.menu
        }
    }

//    suspend fun downloadImageBitmap(url: String): Bitmap? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val client = HttpClient(Android)
//                val response: HttpResponse = client.get(url)
//                val byteArray = response.readBytes()
//                client.close()
//                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//        }
//    }
    suspend fun downloadAndSaveImage(url: String, context: Context): String? {
        return withContext(Dispatchers.IO) {
            try {
                val bytes = LittleLemonHttpClient.instance.get(url).readBytes()
                val fileName = url.hashCode().toString() + ".jpg"
                val file = File(context.filesDir, fileName)

                file.writeBytes(bytes)
                return@withContext file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun getRemoteImageByteArray(url: String): ByteArray?{
        return withContext(Dispatchers.IO){
            try{
                val bytes = LittleLemonHttpClient.instance.get(url).readBytes()
                return@withContext bytes
            }
            catch (e: Exception){
                Log.i("image-tag", "${e.message}")
                return@withContext null
            }
        }
    }

    private fun createImageFileFromBytes(byteArray: ByteArray, fileName:String, context: Context): String{
        val file = File(context.filesDir, fileName)
        file.writeBytes(byteArray)
        return file.absolutePath
    }

    fun hashBytes(bytes: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(bytes).joinToString("") { "%02x".format(it) }
    }



    companion object{
        fun getInstance(database: LittleLemonDatabase, viewModelStoreOwner: ViewModelStoreOwner): MenuViewModel{
            return ViewModelProvider(viewModelStoreOwner, MenuViewModelFactory(database))[MenuViewModel::class.java]
        }

        class MenuViewModelFactory(
            private val database: LittleLemonDatabase,
        ): ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MenuViewModel::class.java)){
                    return MenuViewModel(database) as T
                }
                else{
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

}