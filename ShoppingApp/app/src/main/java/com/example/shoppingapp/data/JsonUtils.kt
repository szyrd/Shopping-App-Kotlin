import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> loadJSONFromAssets(context: Context, fileName: String): T {
    val json: String = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}

