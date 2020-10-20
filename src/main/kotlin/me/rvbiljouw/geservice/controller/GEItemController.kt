package me.rvbiljouw.geservice.controller

import me.rvbiljouw.geservice.exception.ItemNotFoundException
import me.rvbiljouw.geservice.model.GEItemSummary
import me.rvbiljouw.geservice.service.GECacheService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author rvbiljouw
 */
@RestController
class GEItemController(private val geCacheService: GECacheService) {

    @RequestMapping("/api/v1/item/{id}")
    fun getItem(@PathVariable("id") id: Short): GEItemSummary {
        return geCacheService.getOrLoadItemSummary(id) ?: throw ItemNotFoundException(id)
    }

}
