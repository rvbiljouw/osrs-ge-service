package me.rvbiljouw.geservice.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author rvbiljouw
 */
fun ObjectMapper.withDefaults(): ObjectMapper {
    return registerModule(KotlinModule())
}
