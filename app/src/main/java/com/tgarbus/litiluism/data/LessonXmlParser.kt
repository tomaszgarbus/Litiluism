package com.tgarbus.litiluism.data

import android.content.Context
import android.util.Xml
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.getDrawableResourceId
import org.xmlpull.v1.XmlPullParser

class LessonXmlParser(private val xmlString: String) {
    enum class NodeModifier {
        BOLD,
        CURSIVE,
        IMAGE
    }

    data class NodeMetadata(
        val imgSource: String
    )

    data class Node(
        val modifiers: List<NodeModifier>,
        val text: String,
        val metadata: NodeMetadata?
    )

    data class Lesson(
        val id: String,
        val title: String,
        val body: List<Node>
    )

    class ModifiersStack {
        private fun tagToModifier(tag: String): NodeModifier? {
            return when (tag) {
                "i" -> NodeModifier.CURSIVE
                "b" -> NodeModifier.BOLD
                "img" -> NodeModifier.IMAGE
                else -> null
            }
        }

        private val tagsCount = NodeModifier.entries.map { _ -> 0 }.toIntArray()
        private var metadata: NodeMetadata? = null
        private var text: String = ""

        private fun getActiveModifiers(): List<NodeModifier> {
            return NodeModifier.entries.filter { m -> tagsCount[m.ordinal] > 0 }
        }

        fun emitNode(): Node? {
            if (text.isEmpty()) {
                return null
            }
            return Node(
                modifiers = getActiveModifiers(),
                text = text,
                metadata = metadata
            )
        }

        fun pushTag(pullParser: XmlPullParser): Node? {
            val node = emitNode()
            val tag = pullParser.name
            val modifier = tagToModifier(tag) ?: return null
            tagsCount[modifier.ordinal] += 1
            assert(tagsCount[modifier.ordinal] <= 1)
            if (tag == "img") {
                assert(pullParser.attributeCount == 1 && pullParser.getAttributeName(0) == "src")
                metadata = NodeMetadata(imgSource = pullParser.getAttributeValue(0))
            }
            text = ""
            return node
        }

        fun popTag(pullParser: XmlPullParser): Node? {
            val node = emitNode()
            val tag = pullParser.name
            val modifier = tagToModifier(tag) ?: return null
            tagsCount[modifier.ordinal] -= 1
            if (tag == "img") {
                metadata = null
            }
            text = ""
            return node
        }

        fun pushText(pullParser: XmlPullParser) {
            text += pullParser.text
        }
    }

    private fun parseId(pullParser: XmlPullParser): String {
        var eventType: Int = pullParser.next()
        var text = ""
        while (eventType != XmlPullParser.END_TAG) {
            when (eventType) {
                XmlPullParser.START_TAG -> assert(false) { "Tags in <id> not allowed" }
                XmlPullParser.TEXT -> text += pullParser.text
            }
            eventType = pullParser.next()
        }
        return text
    }

    private fun parseTitle(pullParser: XmlPullParser): String {
        var eventType: Int = pullParser.next()
        var text = ""
        while (eventType != XmlPullParser.END_TAG) {
            when (eventType) {
                XmlPullParser.START_TAG -> assert(false) { "Tags in <title> not allowed" }
                XmlPullParser.TEXT -> text += pullParser.text
            }
            eventType = pullParser.next()
        }
        return text
    }

    private fun parseBody(pullParser: XmlPullParser): ArrayList<Node> {
        var eventType = pullParser.next()
        val modifiersStack = ModifiersStack()
        val result = arrayListOf<Node>()
        while (eventType != XmlPullParser.END_TAG || pullParser.name != "body") {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    val node = modifiersStack.pushTag(pullParser)
                    if (node != null) result.add(node)
                }

                XmlPullParser.END_TAG -> {
                    val node = modifiersStack.popTag(pullParser)
                    if (node != null) result.add(node)
                }

                XmlPullParser.TEXT -> {
                    modifiersStack.pushText(pullParser)
                }
            }
            eventType = pullParser.next()
        }
        val node = modifiersStack.emitNode()
        if (node != null) result.add(node)

        return result
    }

    private fun parseLessonInternal(pullParser: XmlPullParser): Lesson {
        var eventType: Int = pullParser.eventType
        var id = ""
        var title = ""
        var body = ArrayList<Node>()
        while (eventType != XmlPullParser.END_TAG || pullParser.name != "lesson") {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (pullParser.name) {
                        "title" -> title = parseTitle(pullParser)
                        "id" -> id = parseId(pullParser)
                        "body" -> body = parseBody(pullParser)
                    }
                }

                else -> {}
            }
            eventType = pullParser.next()
        }
        return Lesson(id, title, body)
    }

    private fun Node.toLessonTextSpan(): LessonTextSpan {
        return LessonTextSpan(
            text,
            modifiers.mapNotNull { m ->
                when (m) {
                    NodeModifier.BOLD -> LessonTextModifier.BOLD
                    NodeModifier.CURSIVE -> LessonTextModifier.ITALIC
                    else -> null
                }
            }
        )
    }

    private fun Node.toLessonBlock(context: Context): LessonBlock {
        val textSpan = toLessonTextSpan()
        var imageResourceId: Int? = null
        if (modifiers.contains(NodeModifier.IMAGE)) {
            imageResourceId = getDrawableResourceId(metadata!!.imgSource, context)
        }
        return LessonBlock(
            LessonTextBlock(listOf(textSpan)),
            imageResourceId
        )
    }

    private fun mergeLessonBlocks(first: LessonBlock, second: LessonBlock): LessonBlock {
        assert(first.imageResourceId == second.imageResourceId)
        return LessonBlock(
            LessonTextBlock(first.textBlock.spans + second.textBlock.spans),
            first.imageResourceId
        )
    }

    private fun parseLesson(pullParser: XmlPullParser, context: Context): com.tgarbus.litiluism.data.Lesson {
        val lessonInternal = parseLessonInternal(pullParser)
        val body = ArrayList<LessonBlock>()
        for (node in lessonInternal.body) {
            val newBlock = node.toLessonBlock(context)
            if (body.isNotEmpty() && body.last().imageResourceId == newBlock.imageResourceId) {
                body.add(mergeLessonBlocks(body.removeLast(), newBlock))
            } else {
                body.add(newBlock)
            }
        }
        return Lesson(
            lessonInternal.id, lessonInternal.title, body
        )
    }

    fun parse(context: Context): List<com.tgarbus.litiluism.data.Lesson> {
        val pullParser = Xml.newPullParser()
        val lessons = arrayListOf<com.tgarbus.litiluism.data.Lesson>()
        pullParser.setInput(xmlString.byteInputStream(), null)
        var eventType: Int = pullParser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {}
                XmlPullParser.START_TAG -> {
                    when (pullParser.name) {
                        "lesson" -> lessons.add(parseLesson(pullParser, context))
                        else -> assert(false)
                    }
                }
            }
            eventType = pullParser.next()
        }
        return lessons
    }

    companion object {
        fun loadLessons(context: Context): List<com.tgarbus.litiluism.data.Lesson> {
            val rawStringData = context.resources.openRawResource(R.raw.lessons).bufferedReader()
                .use { it.readText() }
            val parser = LessonXmlParser(rawStringData)
            return parser.parse(context)
        }
    }
}
