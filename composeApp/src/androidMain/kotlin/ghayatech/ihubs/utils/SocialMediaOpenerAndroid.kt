package ghayatech.ihubs.utils// في androidMain/kotlin/com/yourproject/utils/SocialMediaOpenerAndroid.kt

import android.content.Context
import android.content.Intent
import android.net.Uri

class SocialMediaOpenerAndroid(private val context: Context) : SocialMediaOpener {
    override fun openWhatsAppChat(number: String, text: String?) {
        val url = if (text != null) {
            "https://wa.me/$number?text=${Uri.encode(text)}"
        } else {
            "https://wa.me/$number"
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun openInstagramProfile(username: String) {
        val uri = Uri.parse("http://instagram.com/_u/$username")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.instagram.android")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            // إذا كان تطبيق إنستجرام غير مثبت، افتح الرابط في المتصفح.
            val webUri = Uri.parse("http://instagram.com/$username")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }

    override fun openFacebookProfile(userId: String) {
        val uri = Uri.parse("fb://profile/$userId")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            // إذا كان تطبيق فيسبوك غير مثبت، افتح الرابط في المتصفح.
            val webUri = Uri.parse("https://www.facebook.com/profile.php?id=$userId")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }

    override fun openLinkedInProfile(userId: String) {
        val uri = Uri.parse("linkedin://profile/$userId")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            // إذا كان تطبيق LinkedIn غير مثبت، افتح الرابط في المتصفح.
            val webUri = Uri.parse("https://www.linkedin.com/in/$userId")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }

    private fun startActivity(intent: Intent) {
        // إضافة هذا العلم ضروري لفتح الأنشطة من خارج الأنشطة الرئيسية
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}