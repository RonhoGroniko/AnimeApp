package com.sharapov.feature_search_screen.domain.entity.genre

enum class Genre(val id: Int, val value: String) {
    SHOUNEN(27, "Shounen"),
    SHOUJO(25, "Shoujo"),
    SEINEN(42, "Seinen"),
    JOSEI(43, "Josei"),
    KIDS(15, "Kids"),
    VILLAINESS(198, "Villainess"),
    URBAN_FANTASY(197, "Urban Fantasy"),
    COMEDY(4, "Comedy"),
    ECCHI(9, "Ecchi"),
    FANTASY(10, "Fantasy"),
    HORROR(14, "Horror"),
    ACTION(1, "Action"),
    AVANT_GARDE(5, "Avant Garde"),
    SLICE_OF_LIFE(36, "Slice of Life"),
    ROMANCE(22, "Romance"),
    MYSTERY(7, "Mystery"),
    GOURMET(543, "Gourmet"),
    SUSPENSE(117, "Suspense"),
    SCI_FI(24, "Sci-Fi"),
    BOYS_LOVE(133, "Boys Love"),
    SPORTS(30, "Sports"),
    SUPERNATURAL(37, "Supernatural"),
    DRAMA(8, "Drama"),
    ADVENTURE(2, "Adventure"),
    GIRLS_LOVE(129, "Girls Love"),
    EROTICA(539, "Erotica"),
    HENTAI(12, "Hentai"),
    CHILDCARE(134, "Childcare"),
    PARODY(20, "Parody"),
    PERFORMING_ARTS(142, "Performing Arts"),
    PETS(148, "Pets"),
    MAGICAL_SEX_SHIFT(135, "Magical Sex Shift"),
    ANTHROPOMORPHIC(143, "Anthropomorphic"),
    TEAM_SPORTS(102, "Team Sports"),
    LOVE_POLYGON(107, "Love Polygon"),
    MILITARY(38, "Military"),
    VAMPIRE(32, "Vampire"),
    IDOLS_FEMALE(145, "Idols (Female)"),
    PSYCHOLOGICAL(40, "Psychological"),
    SURVIVAL(141, "Survival"),
    REINCARNATION(106, "Reincarnation"),
    CROSSDRESSING(144, "Crossdressing"),
    CGDCT(119, "CGDCT"),
    MEDICAL(147, "Medical"),
    MARTIAL_ARTS(17, "Martial Arts"),
    MECHA(18, "Mecha"),
    SAMURAI(21, "Samurai"),
    SCHOOL(23, "School"),
    SPACE(29, "Space"),
    HAREM(35, "Harem"),
    LOVE_STATUS_QUO(151, "Love Status Quo"),
    SUPER_POWER(31, "Super Power"),
    HISTORICAL(13, "Historical"),
    RACING(3, "Racing"),
    MAHOU_SHOUJO(124, "Mahou Shoujo"),
    IDOLS_MALE(150, "Idols (Male)"),
    VIDEO_GAME(103, "Video Game"),
    EDUCATIONAL(149, "Educational"),
    WORKPLACE(139, "Workplace"),
    SHOWBIZ(136, "Showbiz"),
    AWARD_WINNING(114, "Award Winning"),
    GORE(105, "Gore"),
    IYASHIKEI(140, "Iyashikei"),
    MUSIC(19, "Music"),
    GAG_HUMOR(112, "Gag Humor"),
    HIGH_STAKES_GAME(146, "High Stakes Game"),
    MYTHOLOGY(6, "Mythology"),
    COMBAT_SPORTS(118, "Combat Sports"),
    OTAKU_CULTURE(137, "Otaku Culture"),
    TIME_TRAVEL(111, "Time Travel"),
    ADULT_CAST(104, "Adult Cast"),
    DETECTIVE(39, "Detective"),
    STRATEGY_GAME(11, "Strategy Game"),
    VISUAL_ARTS(108, "Visual Arts"),
    ORGANIZED_CRIME(138, "Organized Crime"),
    DELINQUENTS(131, "Delinquents"),
    REVERSE_HAREM(125, "Reverse Harem"),
    ISEKAI(130, "Isekai"),
    YAOI(33, "Yaoi"),
    YURI(34, "Yuri");

    companion object {

        val sortedAlphabetically: Map<Char, List<String>> =
            entries
                .map { it.value }
                .sorted()
                .groupBy { it[0].uppercaseChar() }

        fun getById(id: Int): Genre {
            for (genre in entries) {
                if (id == genre.id) {
                    return genre
                }
            }
            throw IllegalArgumentException("Unknown genre_id $id")
        }

        fun getIdByName(name: String): Int {
            for (genre in entries) {
                if (name == genre.value) {
                    return genre.id
                }
            }
            throw IllegalArgumentException("Unknown genre_name $name")
        }
    }
}