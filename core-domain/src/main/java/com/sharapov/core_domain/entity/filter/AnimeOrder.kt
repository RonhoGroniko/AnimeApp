package com.sharapov.core_domain.entity.filter

enum class AnimeOrder(val value: String) {

    BY_ID("id"), // By ID
    BY_ID_DESC("id_desc"), // id_desc
    BY_RANK("ranked"), // By rank
    BY_TYPE("kind"), // By type
    BY_POPULARITY("popularity"), // By popularity
    IN_ALPHABET_ORDER("name"), // In alphabetical order
    BY_RELEASE_DATE("aired_on"), // By release date
    BY_NUMBER_OF_EPISODES("episodes"), // By number of episodes
    BY_STATUS("status"), // By status
    BY_RANDOM("random"), // By random
    BY_RANKED_RANDOM("ranked_random"), // By random
    BY_SHIKIMORI_RANKING("ranked_shiki"), // By Shikimori ranking
    CREATED_AT("created_at"), // created_at
    CREATED_AT_DESC("created_at_desc"), // created_at_desc
}
