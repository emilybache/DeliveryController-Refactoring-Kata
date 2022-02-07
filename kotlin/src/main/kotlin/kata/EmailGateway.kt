package kata

import java.net.PasswordAuthentication
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


class EmailGateway {

    fun send(address: String, subject: String, msg: String) {

        val prop = mapOf(
            "mail.smtp.host" to "localhost",
            "mail.smtp.port" to "587",
            "mail.smtp.auth" to "true",
            "mail.smtp.ssl.trust" to "localhost",
            "mail.smtp.starttls.enable" to "true"
        ).toProperties()

        val session: Session = Session.getInstance(prop, object : Authenticator() {
            val passwordAuthentication: PasswordAuthentication
                get() = PasswordAuthentication("email", "password".toCharArray())
        })

        val message: Message = MimeMessage(session)
        val mimeBodyPart = MimeBodyPart()
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8")
        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(mimeBodyPart)
        message.setContent(multipart)

        message.setFrom(InternetAddress("noreply@example.com"))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address))
        message.subject = subject
        message.setContent(multipart)
        Transport.send(message)
    }

}