package me.rvbiljouw.geservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import me.rvbiljouw.geservice.model.GEItemSummary
import me.rvbiljouw.geservice.model.GEItemSummaryWrapper
import me.rvbiljouw.geservice.util.withDefaults
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * @author rvbiljouw
 */
@Service
class GECacheService {

    companion object {
        const val GEBaseUrl = "https://secure.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item="
    }

    private val mapper = ObjectMapper().withDefaults()
    private val client = OkHttpClient()

    private val summaryCache: LoadingCache<Short, GEItemSummary> = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .build(object : CacheLoader<Short, GEItemSummary>() {
                override fun load(p0: Short): GEItemSummary? {
                    return summarySupplier(p0)
                }
            })

    private val summarySupplier = { id: Short ->
        val request = Request.Builder()
                .url("$GEBaseUrl$id")
                .get()
                .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            mapper.readValue<GEItemSummaryWrapper>(response.body!!.string()).item
        } else {
            null
        }
    }

    fun getOrLoadItemSummary(id: Short): GEItemSummary? {
        try {
            return summaryCache.get(id)
        } catch (icl: CacheLoader.InvalidCacheLoadException) {
            return null
        }
    }

}
