package ghayatech.ihubs.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

actual class WhatsAppHelper(private val context: Context) {
    actual fun openWhatsApp(phone: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/${phone.replace("+", "")}")
            setPackage("com.whatsapp")
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
//            if (intent.resolveActivity(context.packageManager) != null) {
//
//            } else {

            Log.d("TAG", "openWhatsApp: ${e.message}")
            Log.d("TAG", "openWhatsApp: ${e.localizedMessage}")
            Toast.makeText(context, "واتساب غير مثبت على الجهاز", Toast.LENGTH_SHORT).show()
//            }
        }

    }
}

