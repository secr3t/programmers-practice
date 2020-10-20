/**
 * Created by secr3t on 2020/09/24 at 11:17
 *
 * Email : dev.secr3t@gmail.com
 */

/*
<html lang="ko" xml:lang="ko" xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="utf-8">
  <meta property="og:url" content="https://a.com"/>
</head>
<body>
Blind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit.
<a href="https://b.com"> Link to b </a>
</body>
</html>
 */


fun main() {
    val s = Solution1()
    println(s.solution("Muzi", arrayOf("<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n\n</body>\n</html>",
            "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n\n\t^\n</body>\n</html>")))

    s.solution("blind", arrayOf("<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://a.com\"/>\n</head>  \n<body>\nBlind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n<a href=\"https://b.com\"> Link to b </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://b.com\"/>\n</head>  \n<body>\nSuspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n<a href=\"https://a.com\"> Link to a </a>\nblind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n<a href=\"https://c.com\"> Link to c </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://c.com\"/>\n</head>  \n<body>\nUt condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n<a href=\"https://a.com\"> Link to a </a>\n</body>\n</html>"))

}

class Solution1 {
    fun solution(word: String, pages: Array<String>): Int {
        val linkMap = pages.map { s -> Html(s.toLowerCase(), word.toLowerCase()) }.associateBy { it.link }
        val htmls = linkMap.values.toList()

        htmls.forEach { it.externalLinks.forEach { link ->
            if (linkMap[link] != null) {
                linkMap[link]!!.refScore += it.exScore
            }
        }}

        val scores = htmls.map {
            it.score + it.refScore
        }
        return scores.indexOfFirst {it == scores.max()}
    }


    data class Html(
            var html: String,
            val word: String
    ) {
        var link: String
        var externalLinks: List<String>
        var score = 0.0
        val exScore: Double
        var refScore: Double = 0.0
        init {
            link = linkRegex.find(html)?.groupValues?.get(1) ?: ""
            externalLinks = externalRegex.findAll(html).map { it.groupValues[1] }.toList()

//            html = tag.replace(html, "")

            calcScore()

            exScore = if (externalLinks.isEmpty()) 0.0 else score / externalLinks.size.toDouble()
        }

        private fun calcScore() {
            var find = html.indexOf(word)

            while (find != -1) {
                val prev = html[find - 1]
                val next = html[find + word.length]

                if ((prev < 'a' || prev > 'z') && (next < 'a' || next > 'z')) this.score++

                find = html.indexOf(word, find + 1)
            }
        }

        companion object {
            val linkRegex = Regex("<meta property=\"og:url\" content=\"https://(.+?)\"/>")
            val externalRegex = Regex("<a href=\"https://(.+?)\">.*</a>")
            val tag = Regex("<.+?>")
        }
    }
}