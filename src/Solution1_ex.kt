/**
 * Created by secr3t on 2020/09/24 at 18:12
 *
 * Email : dev.secr3t@gmail.com
 */

class Pro42893 {
    //현재 사이트 URL
    private val CURRENT_URL = Regex("<meta property=\"og:url\" content=\"https://(.+?)\"/>")

    //외부 링크
    private val EXTERNAL_URL = Regex("<a href=\"https://(.+?)\">")

    //페이지들의 정보를 담고 있다. URL로 페이지 정보를 찾는다.
    private val pageUrl = HashMap<String?, Page?>()

    //페이지 정보
    private inner class Page(var html: String, var word: String, var idx: Int) {
        var url: String? = null
        var defaultGrade = 0

        //해당 url이 이동할 수 있는 링크
        var linkTo = HashSet<String>()

        //해당 url로 들어올 수 있는 링크
        var linkFrome = HashSet<String?>()

        //기본 url정보
        fun init() {
            url = CURRENT_URL.find(html)?.groupValues?.get(1) ?: ""
        }

        //기본 점수 구하기
        fun calculateDefaultGrade() {
            var find = html.indexOf(word)
            while (find != -1) {
                val prev = html[find - 1]
                val next = html[find + word.length]

                //단어의 앞/뒤로 a~z의 문자가 포함되어있는지 검사. 단어가 반복되거나 다른 문자를 포함하면 안되기 때문
                if ((prev < 'a' || prev > 'z') && (next < 'a' || next > 'z')) defaultGrade++

                //단어를 검사했으므로, 다음 단어를 검사하기 위해 현재 인덱스에서 +1부터 단어를 검색한다.
                find = html.indexOf(word, find + 1)
            }
        }

        //링크를 셋팅
        fun setLink() {
            var matcher = EXTERNAL_URL.find(html)
            while (matcher != null) {
                val link: String = matcher.groupValues[1] //링크 정보

                //현재 페이지에 해당 링크 정보를 중복되지 않게 추가
                if (!linkTo.contains(link)) linkTo.add(link)

                //해당 링크의 페이지 정보에 어디로부터 왔는지 추가
                if (pageUrl.containsKey(link)) pageUrl[link]!!.linkFrome.add(url)

                matcher = matcher.next()
            }
        }//해당 페이지의 링크 사이즈를 통해 전체 점수를 구한다.//페이지 정보에 존재하는 외부 링크라면//외부 점수를 구하기 위해 어디로부터 참조되었는지 검사//기본 점수로 셋팅

        //전체 점수
        val totalGrade: Double
            get() {
                var sum = defaultGrade.toDouble() //기본 점수로 셋팅
                for (link in linkFrome) { //외부 점수를 구하기 위해 어디로부터 참조되었는지 검사
                    if (pageUrl.containsKey(link)) { //페이지 정보에 존재하는 외부 링크라면
                        val page = pageUrl[link]
                        if (page!!.linkTo.size > 0) { //해당 페이지의 링크 사이즈를 통해 전체 점수를 구한다.
                            sum += page.defaultGrade.toDouble() / page.linkTo.size
                        }
                    }
                }
                return sum
            }

        init {
            init() //기본 url정보를 셋팅해준다.
            calculateDefaultGrade() //기본 점수를 셋팅해준다.
        }
    }

    fun solution(word: String, pages: Array<String>): Int {
        var idx = 0
        for (html in pages) {

            //기본 정보 셋팅
            val page: Page = Page(html.toLowerCase(), word.toLowerCase(), idx)

            //기본정보 셋팅 후, 페이지 정보를 map에 저장
            pageUrl[page.url] = page
            idx++
        }

        //저장된 페이지 정보들을 통해 외부 링크를 셋팅해준다.
        for (page in pageUrl.values) page!!.setLink()
        var answer = 0
        var max = 0.0

        //전체 점수를 구하고, 최댓값을 구한다.
        for (page in pageUrl.values) {
            val total = page!!.totalGrade
            if (total > max) {
                max = total
                answer = page.idx
            }
        }
        return answer
    }
}