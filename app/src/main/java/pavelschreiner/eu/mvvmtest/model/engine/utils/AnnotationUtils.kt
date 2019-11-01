package pavelschreiner.eu.mvvmtest.model.engine.utils

import pavelschreiner.eu.mvvmtest.model.engine.PusherKey
import pavelschreiner.eu.mvvmtest.model.engine.PusherKeyAnnotation

object AnnotationUtils {

    fun checkAnnotations(pusherKey: PusherKey): String? {
        for (member in pusherKey.javaClass.declaredFields) {
            member.declaredAnnotations.toList().forEach {
                if (it is PusherKeyAnnotation) {
                    member.isAccessible = true
                    return checkPusherAnnotationFieldFormat(member.get(pusherKey) as String)
                }
            }
        }
        return null
    }

    private fun checkPusherAnnotationFieldFormat(pusherKeyString: String): String? {
        return if (pusherKeyString.length == 20 && pusherKeyString.matches("[a-z0-9]*".toRegex()))
            pusherKeyString
        else
            null
    }
}