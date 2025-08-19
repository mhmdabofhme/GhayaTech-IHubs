package ghayatech.ihubs.utils// في iosMain/kotlin/com/yourproject/utils/SocialMediaOpenerIos.kt

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringWithUTF8String
import platform.UIKit.UIApplication

//class SocialMediaOpenerIos : SocialMediaOpener {
//    override fun openWhatsAppChat(number: String, text: String?) {
//        val urlString = if (text != null) {
//            "https://wa.me/$number?text=${text.encodeUrl()}"
//        } else {
//            "https://wa.me/$number"
//        }
//        val url = NSURL(string = urlString)
//        UIApplication.sharedApplication.openURL(url)
//    }
//
//    override fun openInstagramProfile(username: String) {
//        val url = NSURL(string = "instagram://user?username=$username")
//        if (UIApplication.sharedApplication.canOpenURL(url)) {
//            UIApplication.sharedApplication.openURL(url)
//        } else {
//            // إذا كان تطبيق إنستجرام غير مثبت، افتح الرابط في المتصفح.
//            val webUrl = NSURL(string = "https://www.instagram.com/$username")
//            UIApplication.sharedApplication.openURL(webUrl)
//        }
//    }
//
//    override fun openFacebookProfile(userId: String) {
//        val url = NSURL(string = "fb://profile/$userId")
//        if (UIApplication.sharedApplication.canOpenURL(url)) {
//            UIApplication.sharedApplication.openURL(url)
//        } else {
//            // إذا كان تطبيق فيسبوك غير مثبت، افتح الرابط في المتصفح.
//            val webUrl = NSURL(string = "https://www.facebook.com/profile.php?id=$userId")
//            UIApplication.sharedApplication.openURL(webUrl)
//        }
//    }
//
//    override fun openLinkedInProfile(userId: String) {
//        val url = NSURL(string = "linkedin://profile/$userId")
//        if (UIApplication.sharedApplication.canOpenURL(url)) {
//            UIApplication.sharedApplication.openURL(url)
//        } else {
//            // إذا كان تطبيق LinkedIn غير مثبت، افتح الرابط في المتصفح.
//            val webUrl = NSURL(string = "https://www.linkedin.com/in/$userId")
//            UIApplication.sharedApplication.openURL(webUrl)
//        }
//    }
//
//    // دالة مساعدة لتشفير الـ URL بشكل صحيح
//    private fun String.encodeUrl(): String {
//        // 1. تحويل String إلى NSString
//        val nsString = this as NSString
//        // 2. استخدام دالة التشفير الصحيحة التي تعمل على NSString مباشرة
//        // استخدام allowedCharacters لتحديد الأحرف المسموح بها في جزء الـ Query
//        val encoded = nsString.stringByAddingPercentEncodingWithAllowedCharacters(
//            NSCharacterSet.URLQueryAllowedCharacterSet
//        )
//        // 3. إرجاع القيمة المشفّرة أو القيمة الأصلية إذا فشل التشفير
//        return encoded ?: this
//    }
//}

class SocialOpenerIOS : SocialOpener {

    override fun openWhatsApp(phone: String, message: String?) {
        val url = "https://wa.me/$phone${message?.let { "?text=$it" } ?: ""}"
        openUrl(url)
    }

    override fun openFacebook(pageId: String) {
        val appUrl = "fb://profile/$pageId"
        val webUrl = "https://www.facebook.com/$pageId"
        openPreferred(appUrl, fallback = webUrl)
    }

    override fun openInstagram(username: String) {
        val appUrl = "instagram://user?username=$username"
        val webUrl = "https://instagram.com/$username"
        openPreferred(appUrl, fallback = webUrl)
    }

    override fun openLinkedIn(profileId: String) {
        val appUrl = "linkedin://in/$profileId"
        val webUrl = "https://www.linkedin.com/in/$profileId"
        openPreferred(appUrl, fallback = webUrl)
    }

    override fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url)!!
        UIApplication.sharedApplication.openURL(nsUrl)
    }

    private fun openPreferred(appUrl: String, fallback: String) {
        val nsUrl = NSURL.URLWithString(appUrl)!!
        if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl)
        } else {
            openUrl(fallback)
        }
    }
}
